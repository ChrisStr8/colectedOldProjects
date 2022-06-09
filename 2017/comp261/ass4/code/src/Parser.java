import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;

/**
 * The parser and interpreter. The top level parse function, a main method for
 * testing, and several utility methods are provided. You need to implement
 * parseProgram and all the rest of the parser.
 */
public class Parser {

	/**
	 * Top level parse method, called by the World
	 */
	static RobotProgramNode parseFile(File code) {
		System.out.println(code.getName());
		Scanner scan = null;
		try {
			scan = new Scanner(code);

			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

			RobotProgramNode n = parseProgram(scan); // You need to implement this!!!

			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		}
		return null;
	}

	/** For testing the parser without requiring the world */

	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				File f = new File(arg);
				if (f.exists()) {
					System.out.println("Parsing '" + f + "'");
					RobotProgramNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog != null) {
						System.out.println("================\nProgram:");
						System.out.println(prog);
					}
					System.out.println("=================");
				} else {
					System.out.println("Can't find file '" + f + "'");
				}
			}
		} else {
			while (true) {
				JFileChooser chooser = new JFileChooser(".");// System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if (res != JFileChooser.APPROVE_OPTION) {
					break;
				}
				RobotProgramNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog != null) {
					System.out.println("Program: \n" + prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	// Useful Patterns

	static Pattern NUMPAT = Pattern.compile("-?[1-9][0-9]*|0"); // ("-?(0|[1-9][0-9]*)");
	static Pattern VARPAT = Pattern.compile("\\$[A-Za-z][A-Za-z0-9]*");
	static Pattern OPENPAREN = Pattern.compile("\\(");
	static Pattern CLOSEPAREN = Pattern.compile("\\)");
	static Pattern OPENBRACE = Pattern.compile("\\{");
	static Pattern CLOSEBRACE = Pattern.compile("\\}");
	static Pattern SEMICOLON = Pattern.compile("\\;");
	static Pattern COMMA = Pattern.compile("\\,");
	static Pattern EQUALS = Pattern.compile("\\=");
	static Pattern ELSE = Pattern.compile("else");
	static Pattern ELIF = Pattern.compile("elif");

	private static boolean isAction(String token){
		switch (token){
			case "move": return true;
			case "turnL": return true;
			case "turnR": return true;
			case "turnAround": return true;
			case "shieldOn": return true;
			case "shieldOff": return true;
			case "takeFuel": return true;
			case "wait": return true;
			default: return false;
		}
	}

	private static boolean isSensor(String token){
		switch (token){
			case "fuelLeft": return true;
			case "oppLR": return true;
			case "oppFB": return true;
			case "numBarrels": return true;
			case "barrelLR": return true;
			case "barrelFB": return true;
			case "wallDist": return true;
			default: return false;
		}
	}

	private static boolean isOperation(String token){
		switch (token){
			case "add": return true;
			case "sub": return true;
			case "mul": return true;
			case "div": return true;
			default: return false;
		}
	}

	/**
	 * PROG  ::= STMT*
	 */
	static PROG parseProgram(Scanner s) {
		// THE PARSER GOES HERE
		///*
		if(!s.hasNext()){fail("empty program", s);}
		ArrayList<STMT> statements = new ArrayList<>();
		while (s.hasNext()){
			statements.add(parseStatement(s));
		}
		return new PROG(statements);
	}

	/**
	 * STMT  ::= ACT ; | LOOP | IF | WHILE | ASSGN ;
	 */
	static STMT parseStatement(Scanner s){
		if(!s.hasNext()){fail("end of file", s);}
		String token = s.next();
		if(isAction(token)){
			return  new STMT(parseAction(s, token));
		}
		if(token.equals("loop")){
			return new STMT(parseLoop(s));
		}
		if(token.equals("if")){
			return new STMT(parseIf(s));
		}
		if(token.equals("while")){
			return new STMT(parseWhile(s));
		}
		if(token.charAt(0)=='$'){
			return new STMT(parseAssignment(s, token));
		}
		fail("invalid statement", s);
		return null;
	}

	/**
	 * ACT   ::= move [( EXP )] | turnL | turnR | turnAround |
	 * shieldOn | shieldOff | takeFuel | wait [( EXP )]
	 */
	static ACT parseAction(Scanner s, String token){
		if(!s.hasNext()){fail("end of file", s);}
		if(!isAction(token)){
			fail("invalid action", s);
		}
		EXP exp = null;
		if(s.hasNext(OPENPAREN)){
			s.next();
			exp = parseExpression(s);
			require(CLOSEPAREN, "expected ) in action", s);
		}
		require(SEMICOLON, "expected ; in action", s);
		return new ACT(token, exp);
	}

	/**
	 * LOOP ::= loop BLOCK
	 */
	static LOOP parseLoop(Scanner s){
		if(!s.hasNext()){fail("end of file", s);}
		if (s.hasNext(OPENBRACE)) {
			return new LOOP(parseBlock(s));
		}
		fail("invalid loop", s);
		return null;
	}

	/**
	 * IF    ::= if ( COND ) BLOCK [elif ( COND ) BLOCK]* [else BLOCK]
	 */
	static IF parseIf(Scanner s){
		if(!s.hasNext()){fail("end of file", s);}
		require(OPENPAREN, "expected ( in if", s);
		COND condition = parseCondition(s);
		require(CLOSEPAREN, "expected ) in if", s);
		BLOCK block = parseBlock(s);

		BLOCK elseBlock = null;
		ArrayList<COND> elifConditions = null;
		ArrayList<BLOCK> elifBlocks = null;

		if(s.hasNext(ELIF)) {
			elifConditions = new ArrayList<>();
			elifBlocks = new ArrayList<>();
			while (s.hasNext(ELIF)) {
				s.next();
				require(OPENPAREN, "expected ( in elif", s);
				elifConditions.add(parseCondition(s));
				require(CLOSEPAREN, "expected ) in elif", s);
				elifBlocks.add(parseBlock(s));
			}
		}
		if(s.hasNext(ELSE)){
			s.next();
			elseBlock = parseBlock(s);
		}
		return new IF(condition, elifConditions, block, elifBlocks, elseBlock);
	}

	/**
	 * WHILE ::= while ( COND ) BLOCK
	 */
	static WHILE parseWhile(Scanner s){
		if(!s.hasNext()){fail("end of file", s);}
		require(OPENPAREN, "expected ( in while", s);
		COND condition = parseCondition(s);
		require(CLOSEPAREN, "expected ) in while", s);
		return new WHILE(condition, parseBlock(s));
	}

	/**
	 * BLOCK ::= { STMT+ }
	 */
	static BLOCK parseBlock(Scanner s){
		if(!s.hasNext()){fail("end of file", s);}
		require(OPENBRACE, "expected { in block", s);
		ArrayList<STMT> statements = new ArrayList<>();
		while(!s.hasNext(CLOSEBRACE)){
			statements.add(parseStatement(s));
		}
		if(statements.size()==0){
			fail("block should not be empty", s);
		}
		require(CLOSEBRACE, "expected } in block", s);
		return new BLOCK(statements);
	}

	/**
	 * EXP   ::= NUM | SEN | VAR | OP ( EXP, EXP )
	 */
	static EXP parseExpression(Scanner s){
		if(!s.hasNext()){fail("end of file", s);}
		if(s.hasNext(NUMPAT)){
			return new EXP(parseNumber(s));
		}
		if(s.hasNext(VARPAT)){
			return new EXP(parseVariable(s));
		}
		String token = s.next();
		if(isSensor(token)){
			return new EXP(parseSensor(s, token));
		}
		if(isOperation(token)){
			return new EXP(parseOperation(s, token));
		}
		fail("invalid expression", s);
		return null;
	}

	/**
	 * OP ::= add | sub | mul | div
	 */
	static OP parseOperation(Scanner s, String token){
		if(!s.hasNext()){fail("end of file", s);}
		if(!isOperation(token)){
			fail("invalid operation", s);
		}
		require(OPENPAREN, "expected ( in operation", s);
		EXP exp1 = parseExpression(s);
		require(COMMA, "expected , in operation", s);
		EXP exp2 = parseExpression(s);
		require(CLOSEPAREN, "expected ) in operation", s);
		return new OP(token, exp1, exp2);
	}

	/**
	 * COND  ::= lt ( EXP, EXP )  | gt ( EXP, EXP )  | eq ( EXP, EXP ) |
	 * and ( COND, COND ) | or ( COND, COND ) | not ( COND )
	 */
	static COND parseCondition(Scanner s){
		if(!s.hasNext()){fail("end of file", s);}
		String type = s.next();
		if(type.equals("and") || type.equals("or")){
			require(OPENPAREN, "expected ( in condition", s);
			COND cond1 = parseCondition(s);
			require(COMMA, "expected , in condition", s);
			COND cond2 = parseCondition(s);
			require(CLOSEPAREN, "expected ) in condition", s);
			return new COND(type, null, null, cond1, cond2);

		}
		if(type.equals("not")){
			require(OPENPAREN, "expected ( in condition", s);
			COND cond1 = parseCondition(s);
			require(CLOSEPAREN, "expected ) in condition", s);
			return new COND(type, null, null, cond1, null);
		}
		if(type.equals("lt") || type.equals("gt") || type.equals("eq")){
			require(OPENPAREN, "expected ( in condition", s);
			EXP exp1 = parseExpression(s);
			require(COMMA, "expected , in condition", s);
			EXP exp2 = parseExpression(s);
			require(CLOSEPAREN, "expected ) in condition", s);
			return new COND(type, exp1, exp2, null, null);
		}
		fail("invalid condition", s);
		return null;
	}

	/**
	 * SEN   ::= fuelLeft | oppLR | oppFB | numBarrels |
	 * barrelLR [( EXP )] | barrelFB [ ( EXP ) ] |
	 * wallDist
	 */
	static SEN parseSensor(Scanner s, String token){
		if(!s.hasNext()){fail("end of file", s);}
		if(!isSensor(token)){
			fail("invalid sensor", s);
		}
		EXP expression = null;
		if(s.hasNext(OPENPAREN)){
			require(OPENPAREN, "expected ( in Sensor", s);
			expression = parseExpression(s);
			require(CLOSEPAREN, "expected ) in Sensor", s);
		}
		return new SEN(token, expression);
	}

	/**
	 * NUM   ::= "-?[1-9][0-9]*|0"
	 */
	static NUM parseNumber(Scanner s){
		if(!s.hasNext()){fail("end of file", s);}
		if(s.hasNext(NUMPAT)) {
			return new NUM(Integer.parseInt(s.next()));
		}
		fail("not a number", s);
		return null;
	}

	/**
	 * ASSGN ::= VAR = EXP
	 */
	static ASSGN parseAssignment(Scanner s, String token){
		require(EQUALS, "expected = in assignment", s);
		EXP expression = parseExpression(s);
		require(SEMICOLON, "expected ; in assignment", s);
		return new ASSGN(token, expression);
	}

	/**
	 * VAR   ::= "\\$[A-Za-z][A-Za-z0-9]*"
	 */
	static VAR parseVariable(Scanner s){
		if(s.hasNext(VARPAT)){
			return new VAR(s.next());
		}
		fail("invalid variable", s);
		return null;
	}

	// utility methods for the parser

	/**
	 * Report a failure in the parser.
	 */
	static void fail(String message, Scanner s) {
		String msg = message + "\n   @ ...";
		for (int i = 0; i < 5 && s.hasNext(); i++) {
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg + "...");
	}

	/**
	 * Requires that the next token matches a pattern if it matches, it consumes
	 * and returns the token, if not, it throws an exception with an error
	 * message
	 */
	static String require(String p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	static String require(Pattern p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	/**
	 * Requires that the next token matches a pattern (which should only match a
	 * number) if it matches, it consumes and returns the token as an integer if
	 * not, it throws an exception with an error message
	 */
	static int requireInt(String p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	static int requireInt(Pattern p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	/**
	 * Checks whether the next token in the scanner matches the specified
	 * pattern, if so, consumes the token and return true. Otherwise returns
	 * false without consuming anything.
	 */
	static boolean checkFor(String p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

	static boolean checkFor(Pattern p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

}

// You could add the node classes here, as long as they are not declared public (or private)
class PROG implements RobotProgramNode {
	private final ArrayList<STMT> statements;

	PROG(ArrayList<STMT> statements){
		this.statements = statements;
	}

	@Override
	public void execute(Robot robot) {
		for (STMT n : statements) {
			n.execute(robot);
		}
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		for (STMT n : statements) {
			s.append(n.toString());
		}
		return s.toString();
	}
}

class STMT implements RobotProgramNode {
	private final RobotProgramNode subnode;

	STMT(RobotProgramNode subnode){
		this.subnode = subnode;
	}

	@Override
	public void execute(Robot robot) {
		subnode.execute(robot);
	}

	@Override
	public String toString(){
		String s = "";
		s += subnode.toString()+"\n";
		return s;
	}
}

class LOOP implements RobotProgramNode{
	private final BLOCK block;

	LOOP(BLOCK block){
		this.block = block;
	}

	@Override
	public void execute(Robot robot) {
		while(true) {
			this.block.execute(robot);
		}
	}

	@Override
	public String toString(){
		return "loop"+this.block;
	}
}

class IF implements RobotProgramNode{
	private final COND condition;
	private final ArrayList<COND> elifConditions;
	private final BLOCK block;
	private final ArrayList<BLOCK> elifBlocks;
	private final BLOCK elseBlock;
	IF(COND condition, ArrayList<COND> elifConditions, BLOCK block,
	   ArrayList<BLOCK> elifBlocks, BLOCK elseBlock){
		this.condition = condition;
		this.elifConditions = elifConditions;
		this.block = block;
		this.elifBlocks = elifBlocks;
		this.elseBlock = elseBlock;
	}

	@Override
	public void execute(Robot robot) {
		if(condition.evaluate(robot)) {
			block.execute(robot);
			return;
		}
		if(elifConditions!=null){
			for (int i = 0; i < elifConditions.size(); i++) {
				COND cond = elifConditions.get(i);
				BLOCK block = elifBlocks.get(i);
				if(cond.evaluate(robot)){
					block.execute(robot);
					return;
				}
			}

		}
		if(elseBlock!=null){
			elseBlock.execute(robot);
		}
	}

	@Override
	public String toString(){
		String s = "if(" + condition.toString() + ")" + block.toString();
		if(elifConditions!=null) {
			StringBuilder sBuilder = new StringBuilder(s);
			for (int i = 0; i < elifConditions.size(); i++) {
				sBuilder.append("elif(").append(elifConditions.get(i).toString()).append(")").append(elifBlocks.get(i).toString());
			}
			s = sBuilder.toString();
		}
		if(elseBlock!=null) {
			s += "else"+elseBlock;
		}
		return s;
	}
}

class WHILE implements RobotProgramNode{
	private final COND condition;
	private final BLOCK block;

	WHILE(COND condition, BLOCK block){
		this.condition = condition;
		this.block = block;
	}

	@Override
	public void execute(Robot robot) {
		while(condition.evaluate(robot)) {
			block.execute(robot);
		}
	}

	@Override
	public String toString(){
		return "while("+condition.toString()+")"+block.toString();
	}
}

class ASSGN implements RobotProgramNode{
	private final String variable;
	private final EXP expression;

	ASSGN(String variable, EXP expression){
		this.variable = variable;
		this.expression = expression;
	}

	@Override
	public void execute(Robot robot) {
		robot.variables.put(variable, expression);
	}

	@Override
	public String toString(){
		return variable+" = "+expression.toString()+";";
	}
}

class BLOCK implements RobotProgramNode{
	private final ArrayList<STMT> statements;

	BLOCK(ArrayList<STMT> statements){
		this.statements = statements;
	}

	@Override
	public void execute(Robot robot) {
		for (RobotProgramNode n : statements) {
			n.execute(robot);
		}
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("{\n");
		for (RobotProgramNode n : statements) {
			s.append(n.toString());
		}
		s.append("}");
		return s.toString();
	}

}

class ACT implements RobotProgramNode{
	private final String action;
	private final EXP exp;

	ACT(String action, EXP exp){
		this.action = action;
		this.exp = exp;
	}

	@Override
	public void execute(Robot robot){
		if(action.equals("move")){
			if(exp!=null) {
				int steps = exp.evaluate(robot);
				for (int i = 0; i < steps; i++) {
					robot.move();
				}
			}else{
				robot.move();
			}
		}
		if(action.equals("turnL")){
			robot.turnLeft();
		}
		if(action.equals("turnR")){
			robot.turnRight();
		}
		if(action.equals("turnAround")){
			robot.turnAround();
		}
		if(action.equals("shieldOn")){
			robot.setShield(true);
		}
		if(action.equals("ShieldOff")){
			robot.setShield(false);
		}
		if(action.equals("takeFuel")){
			robot.takeFuel();
		}
		if(action.equals("wait")){
			if(exp!=null) {
				int steps = exp.evaluate(robot);
				for (int i = 0; i < steps; i++) {
					robot.idleWait();
				}
			}else {
				robot.idleWait();
			}
		}
	}

	@Override
	public String toString(){
		return action+";";
	}
}

class EXP implements IntegerProgramNode{
	private final IntegerProgramNode subnode;

	EXP(IntegerProgramNode subnode){
		this.subnode = subnode;
	}

	@Override
	public int evaluate(Robot robot) {
		return subnode.evaluate(robot);
	}

	@Override
	public String toString(){
		return subnode.toString();
	}
}

class SEN implements IntegerProgramNode{
	private final String type;
	private final EXP expression;

	SEN(String type, EXP expression){
		this.type = type;
		this.expression = expression;
	}

	@Override
	public int evaluate(Robot robot) {
		if(type.equals("fuelLeft")){
			return robot.getFuel();
		}
		if(type.equals("oppLR")){
			return robot.getOpponentLR();
		}
		if(type.equals("oppFB")){
			return robot.getOpponentFB();
		}
		if(type.equals("numBarrels")){
			return robot.numBarrels();
		}
		if(type.equals("barrelLR")){
			if(expression !=null){
				return robot.getBarrelLR(expression.evaluate(robot));
			}
			return robot.getClosestBarrelLR();
		}
		if(type.equals("barrelFB")){
			if(expression !=null){
				return robot.getBarrelFB(expression.evaluate(robot));
			}
			return robot.getClosestBarrelFB();
		}
		if(type.equals("wallDist")){
			return robot.getDistanceToWall();
		}
		return 0;
	}

	@Override
	public String toString(){
		return type;
	}
}

class OP implements IntegerProgramNode{
	private final String type;
	private final EXP exp1;
	private final EXP exp2;

	OP(String type, EXP exp1, EXP exp2){
		this.type = type;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}

	@Override
	public int evaluate(Robot robot) {
		if(type.equals("add")){
			return exp1.evaluate(robot) + exp2.evaluate(robot);
		}
		if(type.equals("sub")){
			return exp1.evaluate(robot) - exp2.evaluate(robot);
		}
		if(type.equals("mul")){
			return exp1.evaluate(robot) * exp2.evaluate(robot);
		}
		if(type.equals("div")){
			return exp1.evaluate(robot) / exp2.evaluate(robot);
		}
		return 0;
	}

	@Override
	public String toString(){
		return "("+type+"("+exp1.toString()+", "+exp2.toString()+")"+")";
	}
}

class COND implements BooleanProgramNode{
	private final String comparision;
	private final EXP exp1;
	private final EXP exp2;
	private final COND cond1;
	private final COND cond2;

	COND(String comparision, EXP exp1, EXP exp2, COND cond1, COND cond2){
		this.comparision = comparision;
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.cond1 = cond1;
		this.cond2 = cond2;
	}

	@Override
	public boolean evaluate(Robot robot) {
		if(comparision.equals("lt")){//less than
			return exp1.evaluate(robot) < exp2.evaluate(robot);
		}
		if(comparision.equals("gt")){//greater than
			return exp1.evaluate(robot) > exp2.evaluate(robot);
		}
		if(comparision.equals("eq")){//equal to
			return exp1.evaluate(robot) == exp2.evaluate(robot);
		}
		if(comparision.equals("and")){
			return cond1.evaluate(robot) && cond2.evaluate(robot);
		}
		if(comparision.equals("or")){
			return cond1.evaluate(robot) || cond2.evaluate(robot);
		}
		if(comparision.equals("not")){
			return !cond1.evaluate(robot);
		}
		return false;
	}

	@Override
	public String toString(){
		if(comparision.equals("and") || comparision.equals("or")){
			return comparision+"("+cond1.toString()+", "+cond2.toString()+")";
		}
		if(comparision.equals("not")){
			return comparision+"("+cond1.toString()+")";
		}
		return comparision+"("+exp1.toString()+", "+exp2.toString()+")";
	}
}

class NUM implements IntegerProgramNode{
	private final int number;

	NUM(int number){
		this.number = number;
	}

	@Override
	public int evaluate(Robot robot) {
		return number;
	}

	@Override
	public String toString(){
		return ""+number;
	}
}

class VAR implements IntegerProgramNode{
	private final String name;

	VAR(String name){
		this.name = name;
	}

	@Override
	public int evaluate(Robot robot) {
		IntegerProgramNode expresion = robot.variables.get(name);
		if(expresion!=null) {
			return expresion.evaluate(robot);
		}
		return 0;
	}

	public String toString(){
		return name;
	}
}
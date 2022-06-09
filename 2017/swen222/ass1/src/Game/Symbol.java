package Game;

/**
 * Created by chris on 3/08/17.
 * sword = -
 * shield = #
 * nothing = .
 */
public class Symbol {
    private String symbol;

    /**
     * creates a Symbol object that stores one of the three symbols used in the Sword and shield Game
     * @param symbol the symbol must be either -, . or #
     */
    public Symbol(String symbol){
        if(!symbol.equals("-")&&!symbol.equals(".")&&!symbol.equals("#")){
            throw new Error("invalid symbol");
        }
        this.symbol = symbol;
    }

    /**
     * returns the character stored by the Symbol
     * @return single character string
     */
    public String getSymbol() {
        return symbol;
    }
}

/**
 * Created by chris on 3/08/17.
 */
public class Symbol {
    private String symbol;

    public Symbol(String symbol){
        if(!symbol.equals("Sword")||!symbol.equals("Shield")||!symbol.equals("Nothing"))
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

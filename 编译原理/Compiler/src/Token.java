/*
* Token 类 
*/
public class Token
{
    private TokenType type;     // 类型
    private String lexeme;      // 语义
    private double value;       // 数值  本例中唯有标识符PI、E有值
    private Function function;  // 功能 

    public Token(TokenType type, String lexeme, double value, Function function)
    {
        this.type = type;
        this.lexeme = lexeme;
        this.value = value;
        this.function = function;
    }

    public void setType(TokenType type) { this.type = type; }
    public void setLexeme(String lexeme)
    {
        this.lexeme = lexeme;
    }
    public void setValue(double value)
    {
        this.value = value;
    }
    public void setFunction(Function function)
    {
        this.function = function;
    }


    public TokenType getType() { return type; }
    public String getLexeme()
    {
        return lexeme;
    }
    public double getValue()
    {
        return value;
    }
    public Function getFunction() { return function; }
}

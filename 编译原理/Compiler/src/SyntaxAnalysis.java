/*句法分析
* 主要工作：
1. 设计函数绘图语言的文法，使其适合递归下降分析；
2. 设计语法树的结点，用于存放表达式的语法树；
3. 设计递归下降子程序，分析句子并构造表达式的语法树；
4. 设计测试程序和测试用例，检验分析器是否正确

* public SyntaxAnalysis(String file);   // 构造函数
* public LexicalAnalysis getLexicalAnalysis();     // getLexicalAnalysis()
*
* private Token getToken(); // 过滤源程序中的换行符 &&  过滤源程序中的注释内容，返回非注释的token && 
*
*  //注意return值 使用EBNF文法
*  private ExprNode atom();     // Atom → CONST_ID | T | FUNC L_BRACKET ExpressionR_BRACKET| L_BRACKET ExpressionR_BRACKET 
*  private ExprNode component();    // Atom [ POWER Component ]
*  private ExprNode factor();   // ( PLUS | MINUS ) Factor | Component
*  private ExprNode term();     // Factor { ( MUL | DIV ) Factor }
*  private ExprNode expression();   // Term { ( PLUS | MINUS) Term }
*
*  public void program();   // { Statement SEMICO }
*  private void statement();  //  OriginStatment | ScaleStatment |  RotStatment    | ForStatment
*  protected void originStatement();    // ORIGIN IS L_BRACKET Expression COMMA Expression R_BRACKET
*  protected void scaleStatement();      // SCALE IS L_BRACKET Expression COMMA Expression R_BRACKET
*  protected void rotStatement() ;  // ROT IS Expression
*  protected void forStatement();   //  FOR T FROM Expression TO  Expression STEP Expression DRAW L_BRACKET Expression COMMA Expression R_BRACKET
*
*   private void fetchToken();  // getToken(); + 错误处理
*   private void matchToken(TokenType tokenType)  // 错误处理 + 参数匹配 + printMatchState();    fetchToken();

*   private void syntaxError(Token token);  /
*   private void syntaxError(String message);   
*
*   private String getMethodName();  
*
*   protected void printEnterFunction(String methodName);    
*   protected void printExitFunction(String methodName);   
*   protected void printMatchState();    // println("matchtoken " + token.getLexeme());
*
*   protected void printSyntaxTree(ExprNode root);   
*
*  public static void main(String[] args);
* */

import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;


public class SyntaxAnalysis
{
    private LexicalAnalysis lexicalAnalysis;    // 文件字符流
    private Token token;    // 记号 ||标记 || 令牌
    private int line;       // 行号
    private int indent;     // 缩进值
    protected Draw draw;    
    protected ExprNode xOrigin, yOrigin, xScale, yScale, start, end, x, y, rot, step, color;

    public SyntaxAnalysis(String file)   // 构造函数
    {
        draw = new Draw();
        indent = -1;    
        line = 1;
        lexicalAnalysis = new LexicalAnalysis(file);        
        token = getToken(); // 注意定义时的逻辑顺序

    }

    
    public Draw getDraw()   
    {
        return draw;
    }

    public LexicalAnalysis getLexicalAnalysis()     // getLexicalAnalysis() 
    {
        return lexicalAnalysis;
    }

    private Token getToken()
    {
        /*过滤源程序中的换行符*/
        while ((token = lexicalAnalysis.getToken()).getType() == TokenType.NEXT_LINE)   // "\n"
            line++;     // 用于报错标识

        /*过滤源程序中的注释内容，返回非注释的token*/
        while (token.getType() == TokenType.COMMENT)    // 注释
        {
            while ((token = lexicalAnalysis.getToken()).getType() != TokenType.NEXT_LINE && token.getType() != TokenType.NONTOKEN)
                ; //  注释内容不做任何处理
            while (token.getType() == TokenType.NEXT_LINE)   // "\n"
            {
                line++;
                token = lexicalAnalysis.getToken();
            }
        }

        /*将“过滤程序”的返回值token作为getToken()返回值*/
        return token;
    }

    private ExprNode atom() // Atom → CONST_ID | T | FUNC L_BRACKET ExpressionR_BRACKET| L_BRACKET ExpressionR_BRACKET  (优先级最高)
    {
        ExprNode exprNode = new ExprNode();     // 无参构造 默认其属性值均为null
        switch (token.getType())    // SyntaxAnalysis(String file)中初始化token
        {
            case CONST_ID:    // 常量 标识符PI、E
                exprNode.setToken(token);  // 例: | CONST_ID | 5 |
                token = getToken();
                break;
            case T:     // 参数
                exprNode = draw.getT();     // return t; 
                token = getToken();
                break;
            case FUNC:      // 函数调用 FUNC L_BRACKET  Expression  R_BRACKET
                Token tmp = token;      // 例如 cos(t) → | Fun | cos | child | &&  child → | T | ptr | &&  ptr → parameter
                token = getToken();
                matchToken(TokenType.L_BRACKET);
                exprNode.setToken(tmp);  //  cos Expression ;
                exprNode.setLeft(expression());
                matchToken(TokenType.R_BRACKET);
                break;
            case L_BRACKET:     // (Expresson)
                token = getToken();     // 取Expression
                exprNode = expression();  
                matchToken(TokenType.R_BRACKET);
                break;
            case RED:    // 颜色
                exprNode.setToken(token);
                token = getToken();
                break;
            case BLUE:    // 颜色
                exprNode.setToken(token);
                token = getToken();
                break;
            case GREEN:    // 颜色
                exprNode.setToken(token);
                token = getToken();
                break;
            case DARKGREY:    // 颜色
                exprNode.setToken(token);
                token = getToken();
                break;
            default:
                break;
        }
        return exprNode;
    }

    private ExprNode component()    // Atom [ POWER Component ]
    {
        ExprNode exprNode = atom(); // 执行atom(),进行匹配

        if (token.getType() == TokenType.POWER)     //  Atom POWER Component // if语句 --- [ Optional]
        {
            Token tmp = token;  
            token = getToken();
            exprNode = new ExprNode(tmp, exprNode, component());
        }

        return exprNode;
    }

    private ExprNode factor()   // ( PLUS | MINUS ) Factor | Component
    {
        if (token.getType() == TokenType.PLUS)      // PLUS Factor
            return factor();
        if (token.getType() == TokenType.MINUS)     // MINUS Factor
        {
            Token tmp = token;  
            token = getToken(); 
            return new ExprNode(tmp,
                    new ExprNode(new Token(TokenType.CONST_ID, "0", 0.0, null), null, null),
                    factor());  //ExprNode(Token token, ExprNode left, ExprNode right);
        }
        return component();   // 未检测到( PLUS | MINUS ) Factor ，执行component()方法
    }

    private ExprNode term()     // Factor { ( MUL | DIV ) Factor }
    {
        ExprNode exprNode = factor();
        while (token.getType() == TokenType.MUL || token.getType() == TokenType.DIV)  //Factor MUL Factor | Factor DIV Factor
        {
            Token tmp = token;
            token = getToken();
            exprNode = new ExprNode(tmp, exprNode, factor());
        }
        return exprNode;
    }

    private ExprNode expression()   // Term { ( PLUS | MINUS) Term }  (优先级最低)
    {
        indent++;  // 呼应  printEnterFunction
        printEnterFunction(getMethodName());    // getMethodName?
        ExprNode exprNode = term();
        while (token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS)
        {
            Token tmp = token;
            token = getToken();
            exprNode = new ExprNode(tmp, exprNode, term()); //
        }
        printExitFunction(getMethodName());
        indent--;   // printExitFunction呼应
        return exprNode;
    }


    private void statement()  //  OriginStatment | ScaleStatment |  RotStatment | ForStatment
    {
        indent++;
        printEnterFunction(getMethodName());        //	enter in statement
        switch (token.getType())
        {
            case ORIGIN:
                originStatement();
                break;
            case SCALE:
                scaleStatement();
                break;
            case ROT:
                rotStatement();
                break;
            case FOR:
                forStatement();
                break;
            case COLOR:
                ColorStatement();
                break;
            default:
                syntaxError(token);
                break;
        }
        printExitFunction(getMethodName());
        indent--;
    }

    protected void ColorStatement()    // Color is blue;
    {
        indent++;
        printEnterFunction(getMethodName());    // enter in ColorStatment
        matchToken(TokenType.COLOR);
        matchToken(TokenType.IS);
        switch (token.getType())
        {
            case RED:
                color = expression();
                break;
            case BLUE:
                color = atom();
                break;
            case GREEN:
                color = atom();
                break;
            case DARKGREY:
                color = expression();
                break;
        }
        printSyntaxTree(color);
        printExitFunction(getMethodName());
        indent--;
    }

    protected void originStatement()    // ORIGIN IS L_BRACKET Expression COMMA Expression R_BRACKET
    {
        indent++;
        printEnterFunction(getMethodName());    // enter in originStatement
        matchToken(TokenType.ORIGIN);
        matchToken(TokenType.IS);
        matchToken(TokenType.L_BRACKET);
        xOrigin = expression();
        printSyntaxTree(xOrigin);
        matchToken(TokenType.COMMA);
        yOrigin = expression();
        printSyntaxTree(yOrigin);
        matchToken(TokenType.R_BRACKET);
        printExitFunction(getMethodName());
        indent--;
    }

    protected void scaleStatement()      // SCALE IS L_BRACKET Expression COMMA Expression R_BRACKET
    {
        indent++;
        printEnterFunction(getMethodName());    // enter in scaleStatement
        matchToken(TokenType.SCALE);
        matchToken(TokenType.IS);
        matchToken(TokenType.L_BRACKET);
        xScale = expression();
        printSyntaxTree(xScale);
        matchToken(TokenType.COMMA);
        yScale = expression();
        printSyntaxTree(yScale);
        matchToken(TokenType.R_BRACKET);
        printExitFunction(getMethodName());
        indent--;
    }

    protected void rotStatement()   // ROT IS Expression
    {
        indent++;
        printEnterFunction(getMethodName());    // enter in rotStatement
        matchToken(TokenType.ROT);
        matchToken(TokenType.IS);
        rot = expression();
        printSyntaxTree(rot);
        printExitFunction(getMethodName());      // enter from rotStatement
        indent--;
    }


    protected void forStatement()   //  FOR T FROM Expression TO  Expression STEP Expression DRAW L_BRACKET Expression COMMA Expression R_BRACKET
    {
        indent++;
        printEnterFunction(getMethodName());    // enter in forStatement
        matchToken(TokenType.FOR);
        matchToken(TokenType.T);   //
        matchToken(TokenType.FROM);
        start = expression();
        printSyntaxTree(start);
        matchToken(TokenType.TO);
        end = expression();
        printSyntaxTree(end);
        matchToken(TokenType.STEP);
        step = expression();
        printSyntaxTree(step);
        matchToken(TokenType.DRAW);
        matchToken(TokenType.L_BRACKET);
        x = expression();
        printSyntaxTree(x);
        matchToken(TokenType.COMMA);
        y = expression();
        printSyntaxTree(y);
        matchToken(TokenType.R_BRACKET);
        printExitFunction(getMethodName());
        indent--;
    }

    public void program()   // { Statement SEMICO }
    {
        indent++;
        printEnterFunction(getMethodName());    // enter in program
        while (token.getType() != TokenType.NONTOKEN)
        {
            statement();
            matchToken(TokenType.SEMICO);
        }
        printExitFunction(getMethodName());
        indent--;
    }

    private void fetchToken()
    {
        token = getToken();     // //调用词法分析器接口

        // 错误处理
        if (token.getType() == TokenType.ERRTOKEN)
            syntaxError("Error Token!");
    }


    private void matchToken(TokenType tokenType)  //
    {
        indent++;

        // 错误处理
        if (token.getType() != tokenType)
            syntaxError(token);

        // 参数匹配处理
        if (token.getType() == TokenType.T) // TokenType.T 参数
            draw.setT(new ExprNode(token, null, null));

        printMatchState();
        fetchToken();
        indent--;
    }


    private void syntaxError(Token token)
    {
        System.out.println("Line " + line + ": " + token.getLexeme() + " 不是预期符号");
        System.exit(-1);
    }

    private void syntaxError(String message)
    {
        System.out.println(message);
        System.exit(-1);
    }

    private String getMethodName()
    {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    protected void printEnterFunction(String methodName)
    {
        for (int i = 0; i < indent; i++)
            System.out.print("\t");
        System.out.println("enter in " + methodName);
    }

    protected void printExitFunction(String methodName)
    {
        for (int i = 0; i < indent; i++)
            System.out.print("\t");
        System.out.println("exit from " + methodName);
    }

    protected void printMatchState()    // println("matchtoken " + token.getLexeme());
    {
        for (int i = 0; i < indent; i++)
            System.out.print("\t");
        System.out.println("matchtoken " + token.getLexeme());
    }

    protected void printSyntaxTree(ExprNode root)
    {
        indent++;
        if (root != null)
        {
            for (int i = 0; i < indent; i++)
                System.out.print("\t");
            System.out.println(root.getToken().getLexeme());
            printSyntaxTree(root.getLeft());
            printSyntaxTree(root.getRight());
        }
        indent--;
    }


    public static void main(String[] args)
    {
        if (args.length != 1)
            System.out.println("Usage: The name of source file");
        else
        {
            try
            {
                System.setOut(new PrintStream("SyntaxOut.txt"));
                SyntaxAnalysis syntaxAnalysis = new SyntaxAnalysis(args[0]);

                syntaxAnalysis.program();
                syntaxAnalysis.getLexicalAnalysis().close();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

/*
* LexicalAnalysis 
* 词法分析器： 识别输入序列，并为语法分析器提供记号。
* 
* private PushbackInputStream f;
* 
* public LexicalAnalysis(String file) // 含参构造  生成文件字符流？
* 
* 
* // Token数组
* private static Token tokenString[] = {};      // 标识符PI、E  参数  函数调用  保留字
* private static Token tokenSymbol[] = {};      // 分隔符 (运算符)
* 
* public LexicalAnalysis(String file);    // file 转为 字符流？
* public void close();      // f.close();
*
* private boolean isWhiteSpace(int c);
*
* public Token getToken();      // LexicalAnalysis.getToken() 识别记号token，确定其 type && lexeme && value && function
*
* public static void main(String[] args);
*
*/

import java.io.*;

public class LexicalAnalysis
{
    private PushbackInputStream f;

    
    private static Token tokenString[] = {
            // 标识符PI、E
            new Token(TokenType.CONST_ID, "PI", Math.PI, null),
            new Token(TokenType.CONST_ID, "E", Math.E, null),

            // 参数
            new Token(TokenType.T, "T", 0.0, null),

            // 函数(调用)
            new Token(TokenType.FUNC, "SIN", 0.0, new Function()
            {
                @Override
                public double fun(double param)
                {
                    return Math.sin(param);
                }

                @Override
                public String toString()
                {
                    return "sin";
                }
            }),
            new Token(TokenType.FUNC, "COS", 0.0, new Function()
            {
                @Override
                public double fun(double param)
                {
                    return Math.cos(param);
                }

                @Override
                public String toString()
                {
                    return "cos";
                }
            }),
            new Token(TokenType.FUNC, "TAN", 0.0, new Function()
            {
                @Override
                public double fun(double param)
                {
                    return Math.tan(param);
                }

                @Override
                public String toString()
                {
                    return "tan";
                }
            }),
            new Token(TokenType.FUNC, "LN", 0.0, new Function()
            {
                @Override
                public double fun(double param)
                {
                    return Math.log(param);
                }

                @Override
                public String toString()
                {
                    return "log";
                }
            }),
            new Token(TokenType.FUNC, "EXP", 0.0, new Function()
            {
                @Override
                public double fun(double param)
                {
                    return Math.exp(param);
                }

                @Override
                public String toString()
                {
                    return "exp";
                }
            }),
            new Token(TokenType.FUNC, "SQRT", 0.0, new Function()
            {
                @Override
                public double fun(double param)
                {
                    return Math.sqrt(param);
                }

                @Override
                public String toString()
                {
                    return "sqrt";
                }
            }),

            // 保留字
            new Token(TokenType.ORIGIN, "ORIGIN", 0.0, null),
            new Token(TokenType.SCALE, "SCALE", 0.0, null),
            new Token(TokenType.ROT, "ROT", 0.0, null),
            new Token(TokenType.IS, "IS", 0.0, null),
            new Token(TokenType.FOR, "FOR", 0.0, null),
            new Token(TokenType.FROM, "FROM", 0.0, null),
            new Token(TokenType.TO, "TO", 0.0, null),
            new Token(TokenType.STEP, "STEP", 0.0, null),
            new Token(TokenType.DRAW, "DRAW", 0.0, null),

            new Token(TokenType.COLOR, "COLOR", 0.0, null),
            new Token(TokenType.RED, "RED", 0.0, null),
            new Token(TokenType.BLUE, "BLUE", 0.0, null),
            new Token(TokenType.GREEN, "GREEN", 0.0, null),
            new Token(TokenType.DARKGREY, "DARKGREY", 0.0, null),

    };

    private static Token tokenSymbol[] = {
            // 分隔符
            new Token(TokenType.SEMICO, ";", 0.0, null),
            new Token(TokenType.L_BRACKET, "(", 0.0, null),
            new Token(TokenType.R_BRACKET, ")", 0.0, null),
            new Token(TokenType.COMMA, ",", 0.0, null),
            new Token(TokenType.PLUS, "+", 0.0, null),
            new Token(TokenType.NEXT_LINE, "\n", 0.0, null),

            // 有成为注释标识||乘方的可能性
            /*new Token(TokenType.MINUS, "-", 0.0, null),
            new Token(TokenType.MUL, "*", 0.0, null),
            new Token(TokenType.DIV, "/", 0.0, null),
            new Token(TokenType.POWER, "**", 0.0, null), 
            */
    };

    public LexicalAnalysis(String file) // 含参构造  生成文件字符流？
    {
        try    // 异常处理
        {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));    // file 转为 字符流？
            f = new PushbackInputStream(in);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void close()  // 析构函数
    {
        try
        {
            f.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private boolean isWhiteSpace(int c)
    {
        return c == ' ' || c == '\t' || c == '\r';
    }

    public Token getToken()      // LexicalAnalysis.getToken() 识别记号token，确定其 type && lexeme && value && function
    {
        Token token = new Token(TokenType.NONTOKEN, "", 0.0, null);     // 初始化 token : 空记号（源程序结束）
        int c;
        try
        {
            while ((c = f.read()) != -1)
            {
                if (isWhiteSpace(c))
                    continue;

                StringBuilder string = new StringBuilder("");

                if (Character.isLetter(c))      // 状态1 字符串
                {
                    // string.append
                    string.append((char) c);

                    while ((c = f.read()) != -1 && Character.isLetterOrDigit(c))
                        string.append((char) c);

                    if (c != -1)   f.unread(c);     // 

                
                    for (Token t : tokenString)     // 标识符PI、E  参数  函数调用  保留字
                    {
                        if ((string.toString().toUpperCase()).equals(t.getLexeme()))
                            return t;
                    }


                    token.setType(TokenType.ID);  // token.type; NONTOKEN → ID
                    token.setLexeme(string.toString()); // token.lexeme: "" → "ID047"
                    return token;
                }


                else if (Character.isDigit(c))      // 数字
                {
                    // string.append
                    string.append((char) c);

                    while ((c = f.read()) != -1 && Character.isDigit(c))  // 状态2 整数
                        string.append((char) c);

                    if (c == '.')   // 状态3 小数
                    {
                        string.append((char) c);
                        while ((c = f.read()) != -1 && Character.isDigit(c))
                            string.append((char) c);
                    }

                    if (c != -1)  f.unread(c);

                    token.setType(TokenType.CONST_ID);      // 常数(常量)
                    token.setLexeme(string.toString());
                    token.setValue(Double.parseDouble(string.toString()));

                    return token;
                }

                else // 非字母 && 非数字,则匹配分隔符  且得到的是单字符
                {
                    for (Token t : tokenSymbol)   // 状态5 分隔符  (逐个匹配)  + | ，| ；| ( | ) 
                    {
                        if (t.getLexeme().equals(String.valueOf((char) c)))
                            return t;
                    }

                    // 排查是否为注释
                    if (c == '*')
                    {
                        if ((c = f.read()) == '*')   // 状态5 乘方运算
                        {
                            token.setType(TokenType.POWER);
                            token.setLexeme("**");
                            return token;       // 直接取出第二个"*"
                        }
                        if (c != -1)     f.unread(c);

                        token.setType(TokenType.MUL);  // 状态4 乘运算
                        token.setLexeme("*");

                        return token;
                    }
                    else if (c == '/')   
                    {
                        if ((c = f.read()) == '/')  // 状态5 注释
                        {
                            token.setType(TokenType.COMMENT);
                            token.setLexeme("//");
                            return token;
                        }

                        if (c != -1)
                            f.unread(c);
                        token.setType(TokenType.DIV);  // 状态6 除运算
                        token.setLexeme("/");
                        return token;
                    }
                    else if (c == '-')
                    {
                        if ((c = f.read()) == '-')      // 状态5 注释
                        {
                            token.setType(TokenType.COMMENT);
                            token.setLexeme("--");
                            return token;
                        }
                        if (c != -1)
                            f.unread(c);
                        token.setType(TokenType.MINUS);     // 状态7 减运算
                        token.setLexeme("-");
                        return token;
                    }
              
                }
            }
        }
        catch (IOException e)   // 输入输出异常
        {
            e.printStackTrace();
        }
        return token;
    }

    public static void main(String[] args)
    {
        if (args.length != 1)
            System.out.println("Usage: The name of source file");
        else
        {
            try
            {
                System.setOut(new PrintStream("LexicalOut.txt"));

                LexicalAnalysis lexicalAnalysis = new LexicalAnalysis(args[0]);

                Token token;  

                while ((token = lexicalAnalysis.getToken()).getType() != TokenType.NONTOKEN)
                {
                    System.out.println( "token.type:" + token.getType() + "     token.lexeme:" + token.getLexeme() + "      token.value:" + token.getValue() + "        token.function:" + token.getFunction());
                }
                lexicalAnalysis.close();  // f.close()
            }
            catch (IOException e)
            {
                e.printStackTrace();  
            }
        }
    }
}

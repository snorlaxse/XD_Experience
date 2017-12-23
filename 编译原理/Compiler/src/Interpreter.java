/**语义分析器：根据语言结构，处理函数绘图语言程序的语义
 * public Interpreter(String file);
 * public Draw getDraw();
 * 
 * protected void originStatement();    // 赋值
 * protected void rotStatement();
 * protected void scaleStatement();
 * protected void forStatement();
 * 
 * protected void printEnterFunction(String methodName);     // 空操作
 * protected void printExitFunction(String methodName);      // 空操作
 * protected void printMatchState();         // 空操作
 * protected void printSyntaxTree(ExprNode root);        // 空操作
 * 
 * public static double getExprValue(ExprNode root);    // 迭代计算结点表达式值
 * 
 * public static void main(String[] args);
 */

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Interpreter extends SyntaxAnalysis
{
    public Interpreter(String file)     // 构造函数
    {
        super(file);    // SyntaxAnalysis(String file);
    }

    protected void ColorStatement()
    {
        super.ColorStatement();
        draw.setColor(color);
    }

    protected void originStatement()
    {
        super.originStatement();
        draw.setOrigin(getExprValue(xOrigin), getExprValue(yOrigin));
    }

    protected void rotStatement()
    {
        super.rotStatement();       //  SyntaxAnalysis.rotStatement();
        draw.setRot(getExprValue(rot));
    }

    protected void scaleStatement()
    {
        super.scaleStatement();
        draw.setScale(getExprValue(xScale), getExprValue(yScale));
    }

    protected void forStatement()
    {
        super.forStatement();       // example: for T from 0 to 200 step 1 draw (t, 0);
        draw.setStart(getExprValue(start));
        draw.setEnd(getExprValue(end));
        draw.setStep(getExprValue(step));
        draw.setStepX(x);
        draw.setStepY(y);
        Graphics g = draw.getGraphics();    // !!!
        draw.paint(g);
    }

    protected void printEnterFunction(String methodName)   
    {
    }

    protected void printExitFunction(String methodName)
    {
    }

    protected void printMatchState()
    {
    }

    protected void printSyntaxTree(ExprNode root)
    {
    }

    public static double getExprValue(ExprNode root)
    {
        if (root == null)
            return 0.0;
        switch (root.getToken().getType())
        {
            case PLUS:
                return getExprValue(root.getLeft()) + getExprValue(root.getRight());
            case MINUS:
                return getExprValue(root.getLeft()) - getExprValue(root.getRight());
            case MUL:
                return getExprValue(root.getLeft()) * getExprValue(root.getRight());
            case DIV:
                return getExprValue(root.getLeft()) / getExprValue(root.getRight());
            case POWER:
                return Math.pow(getExprValue(root.getLeft()), getExprValue(root.getRight()));
            case FUNC:
                return root.getToken().getFunction().fun(getExprValue(root.getLeft()));
            case T:
            case CONST_ID:
                return root.getToken().getValue();
            default:
                return 0.0;
        }
    }

    public static void main(String[] args)
    {
        if (args.length != 1)
            System.out.println("Usage: The name of source file");
        else
        {
            Interpreter interpreter = new Interpreter(args[0]);
            Frame frame = new Frame("Captain_Interpreter");
            frame.add(interpreter.getDraw(), BorderLayout.CENTER);
            frame.setSize(700, 700);
            frame.setLocation(300 ,50);
            frame.setBackground(Color.pink);
            
            frame.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    System.exit(0);
                }
            });
            frame.setVisible(true);
            
            interpreter.program();
            interpreter.getLexicalAnalysis().close();
        }
    }
}

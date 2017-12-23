/**
 * public void paint(Graphics g);
 * public ExprNode getT();
 * 
 * private int calcX();
 * private int calcY();
 * 
 * public void setStepX(ExprNode stepX);
 * public void setStepY(ExprNode stepY);
 * public void setOrigin(double x, double y);
 * public void setT(ExprNode t);
 * public void setStep(double step);
 * public void setScale(double x, double y);
 * public void setStart(double start);
 * public void setEnd(double end);
 * public void setRot(double rot);
 * 
 * public static void main(String[] args);
 */
import java.awt.*;

public class Draw extends Panel
{
    private double x = 0, y = 0;    // setOrigin();
    private double xScale = 1, yScale = 1;
    private double step;
    private double start, end;
    private double rot = 0;     // 旋转弧度
    private ExprNode t;     // 绘制の点
    private ExprNode stepX, stepY, color;

    public void paint(Graphics g)
    {
        super.paint(g); 
        Graphics2D g2d = (Graphics2D) g;
        for (double a = start; a <= end; a += step)     // a 参数取值
        {
            long time = System.currentTimeMillis();
            t.getToken().setValue(a);
            int cx = calcX();   // x方向从左向右增长
            int cy = calcY();   // y方向从上到下增长
            g2d.drawLine(cx, cy, cx, cy);  // 画点  左上角为原点
            switch(color.getToken().getType())
            {
                case RED:
                    g2d.setColor(Color.red);
                    break;
                case BLUE:
                    g2d.setColor(Color.blue);
                    break;
                case GREEN:
                    g2d.setColor(Color.green);
                    break;
                case DARKGREY:
                    g2d.setColor(Color.DARK_GRAY);
                    break;
                default:
                    g2d.setColor(Color.black);
                    break;
            }
            while (System.currentTimeMillis() - time < 5);     // 点间隔:5ms
        }
    }

    public ExprNode getT()
    {
        return t;
    }

    private int calcX() // 旋转后X=旋转前X*COS(弧度)+旋转前Y*SIN(弧度) 
    {
        return (int) (Interpreter.getExprValue(stepX) * xScale * Math.cos(rot) + Interpreter.getExprValue(stepY) * yScale * Math.sin(rot) + x);
    }

    private int calcY()     // 旋转后Y=旋转前Y*COS(弧度)-旋转前X*SIN(弧度)
    {
        return (int) (Interpreter.getExprValue(stepY) * yScale * Math.cos(rot) - Interpreter.getExprValue(stepX) * xScale * Math.sin(rot) + y);
    }

    public void setStepX(ExprNode stepX)    // Fun(stepX,stepY)
    {
        this.stepX = stepX;
    }

    public void setStepY(ExprNode stepY)
    {
        this.stepY = stepY;
    }

    public void setColor(ExprNode color)
    {
        this.color = color;
    }

    public void setOrigin(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public void setT(ExprNode t)
    {
        this.t = t;
    }

    public void setStep(double step)
    {
        this.step = step;
    }

    public void setScale(double x, double y)
    {
        xScale = x;
        yScale = y;
    }

    public void setStart(double start)
    {
        this.start = start;
    }

    public void setEnd(double end)
    {
        this.end = end;
    }

    public void setRot(double rot)
    {
        this.rot = rot;
    }

    public static void main(String[] args)
    {

    }
}

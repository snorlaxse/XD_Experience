/*结点类的数据结构  设计语法树的结点，用于存放表达式的语法树

 public ExprNode()；// 无参构造
 public ExprNode(Token token, ExprNode left, ExprNode right);     //含参构造

 public void setLeft(ExprNode left);
 public void setRight(ExprNode right);
 public void setToken(Token token);

 public ExprNode getLeft();
 public ExprNode getRight();
 public Token getToken();
*/

public class ExprNode
{
    private Token token;
    private ExprNode left;
    private ExprNode right;

    public ExprNode()   // 无参构造
    {
        token = null;
        left = null;
        right = null;
    }

    public ExprNode(Token token, ExprNode left, ExprNode right)     //含参构造
    {
        this.token = token;
        this.left = left;
        this.right = right;
    }


    public void setLeft(ExprNode left)
    {
        this.left = left;
    }

    public void setRight(ExprNode right)
    {
        this.right = right;
    }

    public void setToken(Token token)
    {
        this.token = token;
    }


    public ExprNode getLeft()
    {
        return left;
    }

    public ExprNode getRight()
    {
        return right;
    }

    public Token getToken()
    {
        return token;
    }
}

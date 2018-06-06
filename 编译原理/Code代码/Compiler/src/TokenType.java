public enum TokenType
{
    ORIGIN, SCALE, ROT, IS,      // 保留字（一字一码）
    TO, STEP, DRAW, FOR, FROM,
    COLOR, RED, BLUE, GREEN, DARKGREY,  //颜色
    T, ID,  //参数
    SEMICO, L_BRACKET, R_BRACKET, COMMA,    // 分隔符
    PLUS, MINUS, MUL, DIV, POWER,   // 运算符
    FUNC,   // 函数(调用)
    CONST_ID,   // 常数
    NONTOKEN,    // 空记号（源程序结束）
    ERRTOKEN,   // 出错记号（非法输入)
    COMMENT,    // 注释
    NEXT_LINE,  // 换行符"\n"
}

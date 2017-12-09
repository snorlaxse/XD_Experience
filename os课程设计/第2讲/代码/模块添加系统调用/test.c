#include<stdio.h>
#include<stdlib.h>
int main()
{   
        printf("Hello, %04ld\n", syscall(223,110047)); //测试223号系统调用
        return 0;
}



/*
* 安装设备后从设备中读出字符串为你的学号
*/
#include<fcntl.h>
#include<stdio.h>

void main()
{
    char number[1024];
    int o=open("/dev/zhengjian",O_RDONLY);
    int r=read(o,number,sizeof(number));
    close(o);
    printf("read from zhengjian:");
    puts(number);
}

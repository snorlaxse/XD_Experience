/*
题目1:
编写一个C语言程序，使用系统调用open、 read读取/proc/cpuinfo，
并打印出其中的CPU 的主频信息。
*/
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <assert.h>
#include <string.h>
#include <fcntl.h>
int main()
{
         int fd=open("/proc/cpuinfo",O_RDONLY);
         assert(fd!=-1);
         printf("CPU信息:\n");
         char buff[128]={0};
         read(fd,buff,127);
         printf("read:%s\n",buff);
         close(fd);
         return 0;
}

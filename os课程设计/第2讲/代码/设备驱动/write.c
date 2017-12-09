/*
* 将字符写入到设备中
*/
#include<stdio.h>
#include<fcntl.h>
void main()
{
    char stu[1024];
     printf("write string:\n");
     scanf("%s",stu);
     int a=open("/dev/zhengjian",O_WRONLY);
     int b=write(a,stu,sizeof(stu)+1);
//     printf("%d",sizeof(stu));
     close(a);
}

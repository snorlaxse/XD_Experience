/*
* 用户可以读出最近写入到设备中的字符；
* 设备关闭前不能被多次打开；
*/

#include<stdio.h>
#include<fcntl.h>
void main()
{
   int o=open("/dev/zhengjian",O_RDONLY);
   int p=open("/dev/zhengjian",O_RDONLY);
   printf("open status:\n");
   if(o!=-1)
   {
	printf("open success!\n");
   }
   printf("open again status:\n");
   if(p==-1)
   {
	printf("open fail\n");
   }
   close(o);

}

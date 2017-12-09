/*
* 设备支持系统调用ioctl(int d, int req,…)

* 设备支持每次写入字符不超过1024个，超过部分被丢弃
*/

#include<stdio.h>
#include<fcntl.h>
void main()
{       
        int i,o,r,w; // 辅助变量

        char yourmsg[1024];
        char a[1060];
        char b[1060];

        printf("write 1060 strings\n");

	   for(i=0;i<1060;i++)
         {
	      if(i==1023){a[i]='a';}
              else{a[i]='h';}
              
 	   }

         o=open("/dev/zhengjian",O_WRONLY);
         w=write(o,a,sizeof(a)+1);
         close(o);
         printf("read from zhengjian:\n");
         o=open("/dev/zhengjian",O_RDONLY);
         r=read(o,b,sizeof(b));
         close(o);
         puts(b);
         printf("\n");
}

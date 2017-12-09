/*
* 设备支持系统调用ioctl(int d, int req,…)

* req = 0x909090, 清除设备中写入的字符串;
*/

#include<stdio.h>
#include<fcntl.h>
void main()
{
	int o,r;
    char c[1024];
    o=open("/dev/zhengjian",O_WRONLY); 
    // clear buf by set its len to zero
    r=ioctl(o,0x909090,0);
 	close(o);
	
	// 验证已清除设备中的字符串
	o=open("/dev/zhengjian",O_RDONLY);
    r=read(o,c,sizeof(c));
    close(o);     
    printf("clear zhengjian!\n");
    puts(c);

}

#include<reg51.h>
sbit LED_1=P3^6;
sbit LED_2=P3^7;   
unsigned char buffer;
void receive() interrupt 4
{
    ES=0;
	RI=0;    //将接收中断标志清零
	buffer=SBUF;
    SBUF=buffer+1;
    while(!TI); //将数据buffer发送出去	
	TI=0;
    ES=1;
}
void main(){
             TMOD=0x20;//设置定时器1为工作方式2
	TH1=0xd9;   //设置波特率为2400
	TR1=1;   //启用定时器1
	REN=1;  //开启接收
	SM0=0; //工作方式1
	SM1=1;
	EA=1;  //CPU中断允许位
	ES=1;  //串行口中断允许位
}

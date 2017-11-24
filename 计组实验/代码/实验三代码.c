#include<reg51.h>
sbit LED_1=P3^6;
sbit LED_2=P3^7;   
unsigned  char  count;  
void LED_LM()  //LED灯闪烁
{     
	LED_1=~LED_1;
	LED_2=~LED_2;
   }
   void T0_timer(void) interrupt 1 
{
    //TF0标志位能自动清0，所以不用管
    TH0=0X00;
    TL0=0X00;
    count++;
}
void main( )
{
   TMOD=0X01;  //T0 工作方式1
   EA=1;       //开总中断
   ET0=1;      //打开定时器0的中断
   TH0=0X00;
   TL0=0X00;   //对定时器0设置初始值，即设置定时时间 
   TR0=1;      //定时器0控制位，置1时定时器开始工作
   while(1){ 
     if(count==10)
     {
       count=0;
       LED_LM();
     }
   }
}


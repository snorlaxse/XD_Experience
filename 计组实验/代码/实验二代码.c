#include<reg51.h>

sbit Row1=P0^0;
sbit Row2=P0^1;
sbit Row3=P0^2;
sbit Row4=P0^3;
sbit Col1=P3^2;
sbit Col2=P3^3;
sbit Col3=P3^4;
sbit Col4=P3^5;
sbit LED1=P3^6;
sbit LED2=P3^7;

void delay(int del)
{
	int  i,j;
        for(i=0;i<del;i++)
            for(j=0;j<1000;j++);

}

unsigned char readRow()
{
	P0=P0 | 0x0F;
	return P0 & 0x0F;
}

void setCol(unsigned char col)
{
	LED1 =~LED1;
	switch(col)
	{
		case 1:
			Col1=0;
			Col2=1;
			Col3=1;
			Col4=1;
			break;
		case 2:
			Col1=1;
			Col2=0;
			Col3=1;
			Col4=1;
			break;
		case 4:
			Col1=1;
			Col2=1;
			Col3=0;
			Col4=1;
			break;
		case 8:
			Col1=1;
			Col2=1;
			Col3=1;
			Col4=0;
			break;
		default:
			break;
	}
}

unsigned char scanKey()
{
	unsigned char i=0;
	unsigned char Scankey=0x00;
	for(i=0;i<4;i++)
	{
		setCol(0x01 << i);
		delay(1);
		Scankey = readRow();
		switch((~Scankey)&0x0F)
		{
			case 0x01:
				return i*4+0;
				break;
			case 0x02:
				return i*4+1;
				break;
			case 0x04:
				return i*4+2;
				break;
			case 0x08:
				return i*4+3;
				break;
			default:
			break;
		}
	}
	
				return 0xFF;
}

void output(unsigned char i)
{
	LED1=i^0;
	LED2=i^1;
	P1=i;
}

void main()
{
	unsigned char test=0;
	for(test=0;test<16;test++)
	{
		P1 = test;
		delay(100);
	}
	while(1)
	{
		output(scanKey());

	}
}


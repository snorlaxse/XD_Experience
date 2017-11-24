#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define true 1
#define false 0

int flag[2];
int turn;

void p0()
{
	while(true)
	{
		flag[0]=true;
		turn=1;
		while(flag[1] && turn==1)
		{
			//busy wait
			printf("Process p0 is busy-waiting.\n");
		}
		//critical Section 临界区
		printf("Process p0 is at critical Section.\n");

		flag[0]=false;
	}
}

void p1()
{
	while(true)
	{
		flag[1]=true;
		turn=0;
		while(flag[0] && turn==0)
		{
			//busy wait
			printf("Process p1 is busy-waiting.\n");

		}
		//critical Section 临界区
		printf("Process p1 is at critical Section.\n");

		flag[1]=false;
	}
}

int main(int argc, char const *argv[])
{
	flag[0]=flag[1]=false;
	turn=0;
	pthread_t t1,t2;

	if (pthread_create(&t1,NULL,(void *)p0,NULL)!=0)
	{
		printf("pthread t1 cannot create!\n");
		exit(-1);
	}
	if (pthread_create(&t2,NULL,(void *)p1,NULL)!=0)
	{
		printf("pthread t2 cannot create!\n");
		exit(-1);
	}

	pthread_join(t1,NULL);	//以阻塞的方式等待t1指定的线程结束,不捕获线程的返回状态
	pthread_join(t2,NULL);	//以阻塞的方式等待t2指定的线程结束不捕获线程的返回状态
	return 0;
}
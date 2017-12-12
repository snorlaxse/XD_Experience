#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

#define NUM 5

int queue[NUM];

//blank代表空位，product代表产品
sem_t blank_number ,product_number;


void *producer(void* arg)
{
       int p = 0;
       while(1)
       	{
       		//将空位信号量-1
       		sem_wait(&blank_number);
       		queue[p] = rand() % 1000 +1;
       		printf("product %d\n", queue[p]);
       		//将产品信号量+1
       		sem_post(&product_number);

       		p = (p+1) % NUM;
       		sleep(rand() % 5);
       	}
       	return (void*)0;
 }

void *consumer(void* arg)
{
       int c = 0;
       while(1)
       	{
       		//等待有产品后消费，将产品信号量-1
       		sem_wait(&product_number);
       		printf("consumer %d\n", queue[c]);
       		queue[c] = 0;
       		//消费完毕后将空位信号量+1
       		sem_post(&blank_number);

       		c = (c+1) % NUM;
       		sleep(rand() % 5);
       	}
       	return (void*)0;
 }

 int main()
 {
 	pthread_t pid,cid;
 	sem_init(&blank_number,0,NUM);
 	sem_init(&product_number,0,0);

 	pthread_create(&pid,NULL,producer,NULL);
 	pthread_create(&cid,NULL,consumer,NULL);

 	pthread_join(pid,NULL);
 	pthread_join(cid,NULL);

 	sem_destroy(&blank_number);
 	sem_destroy(&product_number);

 	return 0;   
 }

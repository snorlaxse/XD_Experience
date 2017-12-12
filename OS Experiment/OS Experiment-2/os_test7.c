/*
1.现在看一个结合应用这些函数的示例。程序首先创建互斥变量并将其初始化为快速互斥。
2.在线程中，是按次数增大protVariable计数器的值，为防止这个变量被同时访问把增大这个变量的语句放入关键区，即在此句代码前加锁，执行完后解锁。
3.最后调用pthread_mutex_destroy销毁互斥变量。
*/
#include <assert.h>
#include <pthread.h>
#include <stdio.h>
pthread_mutex_t cntr_mutex = PTHREAD_MUTEX_INITIALIZER; //创建互斥变量并将其初始化为快速互斥
long protVariable = 0L;
void *myThread(void *arg){
	int i, ret;
	for(i=0; i<10000; i++){
		ret = pthread_mutex_lock( &cntr_mutex);
		assert( ret == 0 );
		/* Critical Section */
		/* Increment protected counter */
		protVariable++;
		/* Critical Section */
		ret = pthread_mutex_unlock( &cntr_mutex );
		assert( ret == 0 );
	}
	pthread_exit(NULL);
}
#define MAX_THREADS 10
int main(){
	int ret, i;
	pthread_t threadIds[MAX_THREADS];

	for(i=0; i<MAX_THREADS; i++){
		ret = pthread_create( &threadIds[i], NULL, myThread, NULL);
		if(ret != 0){
			printf("Error creating thread %d\n", (int)threadIds[i]);
		}
	}

	for(i = 0; i<MAX_THREADS; i++){
		ret = pthread_join( threadIds[i], NULL);
		if(ret != 0){
			printf("Error joining thread %d\n", (int)threadIds[i]);
		}
	}

	printf("The protected variable value is %ld\n", protVariable);
	ret  = pthread_mutex_destroy( & cntr_mutex );	//销毁互斥变量
	if(ret != 0 ){
		/*Mutex is locked , can not destry*/
		printf("Could not destroy the mutex\n");
	}
	return 0;
}

/*Result:
The protected variable value is 100000
*/
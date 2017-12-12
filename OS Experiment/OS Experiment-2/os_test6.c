/*
* 线程同步
	* 同步线程是多线程开发很重要的一面。最基本的方法是让线程的创建者等待这个线程结束（也称“加入”线程），由pthread_join提供。
	在调用该函数时会挂起调用者直到加入的线程完成。在加入的线程完成后，调用者会从pthread_join的返回值中获得加入线程的终止状态。
	在某种程度上与进程的wait函数相同。
	* `int pthread_join(pthread_t th, void**thread_return );`
		* 参数th是想要加入的线程
		* 参数thread_return存储线程完成后的返回值，可以为NULL,表明不捕获线程的返回状态。
		* 返回值，0代表成功，非0代表失败的编号。

*/
#include <pthread.h>
#include <stdio.h>

void *myThread(void *arg){
	printf("Thread %d started\n", (int)arg);
	pthread_exit(arg);
}
#define MAX_THREADS 5
int main(){
	int ret, i, status;
	pthread_t threadIds[MAX_THREADS];
	for(i = 0; i<MAX_THREADS; i++){
		ret = pthread_create(&threadIds[i], NULL, myThread, (void*)i);
		if(ret != 0){
			printf("Error creating thread %d\n", (void*)i);
		}
	}
	for(i = 0; i<MAX_THREADS; i++){
		ret = pthread_join(threadIds[i], (void **)&status);
		if(ret != 0){
			printf("Error joining thread %d\n", (void *)i);
		}
		else{
			printf("Status = %d\n", status);
		}
	}
	return 0;
}

/*Result:
Thread 0 started
Thread 1 started
Thread 2 started
Thread 3 started
Thread 4 started
Status = 0
Status = 1
Error joining thread 1
Status = 2
Error joining thread 1
Error joining thread 2
Status = 3
Error joining thread 1
Error joining thread 2
Error joining thread 3
Status = 4
Error joining thread 1
Error joining thread 2
Error joining thread 3
Error joining thread 4*/

/*
* 线程（thread）是“进程”中某个单一顺序的控制流。也被称为轻量进程

* 线程函数
	* 头文件<pthread.h>
	* 创建线程原型: `int pthread_create(pthread_t*thread,pthread_attr_t *attr, void *(*start_routine)(void*), void *arg);`
		* 第一个参数为指向线程标示符的指针。
		* 第二个参数用来设置线程属性。
		* 第三个参数是线程运行函数的起始地址。
		* 最后一个参数是运行函数的参数。
		* 若成功则返回0，若失败则返回出错编号。
	* 终止线程原型:`int pthread_exit(void *retval);`
*/
#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>

void *myThread(void *arg){
	printf("Thread ran\n");
	pthread_exit(arg);
}


int main(){
	int ret;
	pthread_t mythread;
	ret = pthread_create( &mythread, NULL, myThread, NULL);
	if(ret != 0){
		printf("Can not create pthread (%s)\n", strerror(errno));
		exit(-1);        
	}else if(ret==0)
		printf("create pthread successful~\n");
	return 0;
}


//gcc -pthread 源代码 -o 目标代码 -lpthread
*  常用进程函数

|API函数|用途|
|---|---||fork|创建一个新的子进程|
wait|将进程挂起直到子进程退出
signal|注册一个新的信号句柄
pause|将进程挂起直到捕获到信号
kill|向某个指定的进程发出信号
exit|正常中止当前进程

* wait函数のstatus参数

| 宏|说明
|---|---|
WIFEXITED|如果子进程正常退出，则不为零
WEXITSTATUS|返回子进程的exit状态
WIFSIGNALED|如果子进程因为信号而结束，则此宏值为true
WTERMSIG|返回引起子进程退出的信号，仅在WIFSIGNALED为true才有意义

* signal函数的信号句柄有三种类型:	* 忽略（SIG_IGN）	* 默认的针对特定信号的句柄类型（SIG_DFL）	* 用户定义句柄

信号|说明
---|---
SIGHUP|挂起
SIGINT|键盘中断
SIGKILL|Kill信号
SIGUSR1|用户自定义信号
SIGUSR2|用户自定义信号
SIGPIPE|终止管道
SIGTERM|终止信号

* kill函数	* kill函数向一个进程或一系列进程发送信号。如果信号成功发送则返回0，否则返回-1.	* kill原型：		* 头文件<sys/types.h><signal.h>		* int kill( pid_t pid, int sig_num);		* 参数sig_num表示将要发送的信号，参数pid可以是各种值

pid|说明
---|---
>0|信号发送到由pid指定的进程
0|信号发送到与调用进程同组的所有进程
-1|信号发送到所有进程（init进程除外）
<0|信号发送到由pid的绝对值指定的进程组中的所有进程

* exit函数	* 终止调用进程。传入exit的参数会返回给父进程，为wait或waitpid调用提供所需要的状态信息。	* exit原型：		` void exit( int status);`	* 进程调用exit时还会向父进程发出SIGCHLD信号，释放当前进程占用的资源。	* 这个函数调用十分重要，因为他会向Shell坏境表明状态时成功还是失败。

* 线程（thread）是“进程”中某个单一顺序的控制流,也被称为轻量进程.
-线程由线程描述符确定，每个线程描述符都是唯一的。每个线程都有私有的栈和一个独有的上下文环境（程序计数器和存储寄存器等）

* 线程与进程区别
	* 地址空间和其它资源（如打开文件）：进程间相互独立，同一进程的各线程间共享。某进程内的线程在其它进程不可见。	* 通信：进程间通信IPC，线程间可以直接读写进程数据段（如全局变量）来进行通信——需要进程同步和互斥手段的辅助，以保证数据的一致性。	* 调度和切换：线程上下文切换比进程上下文切换要快得多。* 线程函数	* 头文件<pthread.h>	* 创建线程原型: `int pthread_create(pthread_t*thread,pthread_attr_t *attr, void *(*start_routine)(void*), void *arg);`		* 第一个参数为指向线程标示符的指针。		* 第二个参数用来设置线程属性。		* 第三个参数是线程运行函数的起始地址。		* 最后一个参数是运行函数的参数。		* 若成功则返回0，若失败则返回出错编号。	* 终止线程原型:`int pthread_exit(void *retval);`* 线程管理	* pthread_t  pthread_self(void);//获得自己的线程描述符

* 线程同步	* 同步线程是多线程开发很重要的一面。最基本的方法是让线程的创建者等待这个线程结束（也称“加入”线程），由pthread_join提供。在调用该函数时会挂起调用者直到加入的线程完成。在加入的线程完成后，调用者会从pthread_join的返回值中获得加入线程的终止状态。在某种程度上与进程的wait函数相同。	* `int pthread_join(pthread_t th, void**thread_return );`		* 参数th是想要加入的线程		* 参数thread_return存储线程完成后的返回值，可以为NULL,表明不捕获线程的返回状态。		* 返回值，0代表成功，非0代表失败的编号。* 线程互斥	* 互斥是保证线程在关键去正常执行的机制。这些关键去只能被线程独占，不能同时访问。	* 创建一个互斥变量		`pthread_mutex_t myMutex = PTHREAD_MUTEX_INITIALIZER`

类型|说明
---|---
PTHREAD_MUTEX_INITIALIZER|快速互斥
PTHREAD_RECURSIVE_MUTEX_INITIALIZER_NP|递归互斥
PTHREAD_ERROCHECK_MUTIX_INITIALIZER_NP|检查错误的互斥
	
* 现在有了互斥变量，可以通过锁定或解锁它，从而创建关键区，函数原型如下	* int pthread_mutex_lock(pthread_mutex_t *mutex);	* int pthread_mutex_trylock(pthread_mutex_t *mutex);	* int pthread_mutex_unlock(pthread_mutex_t *mutex);	* int pthread_mutex_destroy(pthread_mutex_t *mutex);	* pthread_mutex_lock加锁，可能会阻塞。	* pthread_mutex_trylock加锁，如果互斥变量已锁定，则不会阻塞，而是立即返回一个值来描述互斥锁状态。	
	* pthread_mutex_unlock解锁。	* pthread_mutex_destroy销毁互斥变量，如果互斥正被锁定，函数会失败并返回错误码：EBUSY.	* 所有这些函数在成功时返回零，失败时返回非零的错误码。

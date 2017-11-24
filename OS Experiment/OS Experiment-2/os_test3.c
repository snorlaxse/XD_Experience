/*
* signal函数
-signal函数允许你为进程注册信号句柄（一种回调函数，是通过函数指针调用函数）。
-传入的信号句柄应具有如下格式
void signal_handler( int signal_number);
-在信号句柄注册之后，就可以在需要时调用signal函数。
-signal函数原型如下：
sighandler_t  signal( int signum, sighandler_t handler);
-sighandler_t类型定义如下：
typedef void (*sighandler_t)(int);
signal 返回前面注册的信号句柄，可以把新的句柄和旧的句柄串连起来（如果必要）

*进程的信号句柄有三种类型。
-忽略（SIG_IGN）
-默认的针对特定信号的句柄类型（SIG_DFL）
-用户定义句柄
信号|说明
SIGHUP|挂起
SIGINT|键盘中断
SIGKILL|Kill信号
SIGUSR1|用户自定义信号
SIGUSR2|用户自定义信号
SIGPIPE|终止管道
SIGTERM|终止信号
*/
#include <stdio.h>
#include <sys/types.h>
#include <signal.h>
#include <unistd.h>

void catch_ctlc( int sig_num){
	printf("Caught Control-C\n");
	fflush(stdout);//清除标准输出的缓存区
}

int main(){
	signal( SIGINT, catch_ctlc);
	printf("Go ahead,  make my day.\n");
	pause();	//将进程挂起直到捕获到信号
	return 0;
}


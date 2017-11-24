/*
* pause函数
pause函数会把进程挂起，直到接收到信号。在接收到以后，调用进程从pause中返回，继续进行。
如果进程捕获的信号已经注册了信号句柄，那么pause函数会在信号句柄被调用并返回之后才返回。
* pause原型：
-头文件<unistd.h>
-int pause( void );

* kill函数
kill函数向一个进程或一系列进程发送信号。如果信号成功发送则返回0，否则返回-1.
* kill原型：
-头文件<sys/types.h><signal.h>
-int kill( pid_t pid, int sig_num);
-参数sig_num表示将要发送的信号，参数pid可以是各种值
pid|说明
>0|信号发送到由pid指定的进程
0|信号发送到与调用进程同组的所有进程
-1|信号发送到所有进程（init进程除外）
<0|信号发送到由pid的绝对值指定的进程组中的所有进程
*/
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h> //pause头文件
#include <signal.h>
#include <errno.h>
void usr1_handler( int sig_num){
	printf("Parent (%d) got the SIGUSR1\n", getpid());
}
int main(){
	pid_t ret;
	int status;
	int role = -1;
	ret = fork();
	if( ret > 0){
		printf("Parent: This is the parent process (pid %d)\n",getpid());
		signal(SIGUSR1, usr1_handler); //触发事件:SIGUSR1 触发后的操作:usr1_handler
		role = 0;
		pause();	//把进程挂起，直到接收到信号
		printf("Parent: Awaiting child exit\n");
		ret = wait(&status);	//防止僵尸进程的产生
	}         
	else  
		if(ret == 0){
			printf("Child: This is the child process (pid %d)\n",  getpid());
			role = 1;
			sleep(1);
			printf("Child: Sending SIGUSR1 to pid %d\n", getppid());
			kill(getppid(), SIGUSR1);	//杀死父进程 //kill函数向一个进程或一系列进程发送信号
			sleep(2);
		}else{
			printf("Parent: Error trying to fork() (%d)\n", errno);
		}
			printf("%s: Exiting…\n", ((role == 0)? "Parent" : "Child"));
	return 0;
}

/*Result:
Parent: This is the parent process (pid 71390)
Child: This is the child process (pid 71391)
Child: Sending SIGUSR1 to pid 71390
Parent (71390) got the SIGUSR1
Parent: Awaiting child exit
Child: Exiting…
Parent: Exiting…
*/


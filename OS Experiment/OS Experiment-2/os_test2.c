/*
* fork函数没有入口参数，返回进程描述符。
如果返回值是0，那么当前进程就是新创建的子进程。
如果返回值大于零，那么当前进程就是父进程，返回值表示的是子进程的进程ID。
如果fork的返回值小于零，就说明发生了错误。

*wait函数
用于将调用进程挂起，直到子进程（由调用进程创建）退出，或直到某个信号发出。
如果父进程没有在等待子进程退出，而子进程退出了，这个子进程就会成为僵尸进程。

*/
#include <sys/types.h>
#include <unistd.h>
#include <errno.h>

int main(){
	pid_t ret;
	int status , i;
	int role = -1;
	ret = fork();
	if(ret > 0){	//当前进程就是父进程
		printf("Parent: This the parent process (pid %d)\n", getpid());
		for(i=0;i<6;i++){
			printf("Parent: At count %d\n", i);
			sleep(1);      
			}
		ret = wait(&status);	//防止僵尸进程的产生
		role=0;      
	}else if(ret ==0){	//当前进程就是新创建的子进程
			printf("Child: This the child process (pid %d)\n", getpid());
			for(i=0;i<6;i++){
				printf("Chile: At count %d\n",i);
				sleep(1);       
			}
			role = 1;        
		}
		else{	//发生了错误
			printf("Parent: Error trying to fork() (%d)\n", errno);
		}
		printf("%s: Exiting...\n", ((role ==0)?"Parent":"Child"));
		return 0;
}
	

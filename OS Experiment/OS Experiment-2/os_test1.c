/*Note:
* 进程是一个“执行中的程序”
* 进程状态:就绪态/运行态/阻塞态
*/
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

int main(){
	pid_t myPid;
	pid_t myParentPid;
	gid_t myGid;
	uid_t myUid;
            
	myPid = getpid(); //获得当前进程ID
	myParentPid = getppid();//获得当前进程的父进程的ID
	myGid = getgid(); //获得组ID
	myUid = getuid(); //获得用户ID

	printf("my process id is  %d\n", myPid);
	printf("my parent is process id is %d\n", myParentPid);
	printf("my group id is %d\n", myGid);
	printf("my user id is %d\n", myUid);
	return 0;
}

#include <stdio.h>	
#include <sys/types.h>	
#include <fcntl.h>	
#include <unistd.h>
#include <stdlib.h>	
//#include <wait.h>

int main()
{
	int fd;		
	pid_t pid;
	struct flock rdlock,wrlock;
	rdlock.l_type = F_RDLCK;		rdlock.l_start = 0; //读锁
	rdlock.l_whence = SEEK_SET;	rdlock.l_len = 0;
	wrlock.l_type = F_WRLCK;	wrlock.l_start = 0; //写锁
	wrlock.l_whence = SEEK_SET;	wrlock.l_len = 0;	
	if((fd = open("/Users/Captain/Desktop/myfile.txt",O_RDWR)) < 0){
		printf("cannot open the file\n");		
		exit(EXIT_FAILURE);
	}
	if(write(fd,"abcdefgh",8) != 8){
		printf("cannot write to the file\n");		
		exit(EXIT_FAILURE);
	}
	pid = fork();  //创建子进程
	if(pid < 0){
		printf("cannot fork\n");			
		exit(EXIT_FAILURE);
	}

	if(pid == 0){
		if(fcntl(fd,F_GETLK,&rdlock) == -1){
			printf("child cannot lock \n");		
			exit(EXIT_FAILURE);
		}
		//执行成功
		if(rdlock.l_type == F_UNLCK)
			printf("lock off\n");
		else
			printf("lock on\n");
			printf("子进程id:%d\n", getpid());
	}
	else{
		if(fcntl(fd,F_SETLK,&wrlock) == -1){
			printf("parent cannot lock\n");	
			exit(EXIT_FAILURE);
		}
		else
			if(write(fd,"aaaa",4) != 4){
				printf("cannot write to the file\n");
				exit(EXIT_FAILURE);
			}
			printf("父进程id:%d\n", getpid());
			wait(NULL);
		}
	close(fd);
	printf("id:%d\n", getpid());
	return 0;
}

#include <sys/types.h>  
#include <sys/stat.h>  
#include <unistd.h>  
#include <stdlib.h>  
#include <stdio.h>

int main(int argc,char *argv[])
{
	int i;
	char *ptr;
	struct stat filestat;
	for(i=1;i<argc;i++) //i=0 默认当前文件名
	{
		printf("%s: ",argv[i]);
		if(lstat(argv[i],&filestat) < 0)
		{
			printf("lstat error\n");
			continue;
		}
	if(S_ISREG(filestat.st_mode))	ptr = "regular";
	else if(S_ISDIR(filestat.st_mode))	ptr = "directory";
	else if(S_ISCHR(filestat.st_mode))	ptr = "character special";
	else if(S_ISBLK(filestat.st_mode))	ptr = "block special";
	else if(S_ISFIFO(filestat.st_mode))	ptr = "fifo"; //先入先出命名管道	
	else  if(S_ISLNK(filestat.st_mode))	ptr = "symbolic link";
	else  if(S_ISSOCK(filestat.st_mode))	ptr = "socket";
	else  ptr = "unknown mode";
		printf("%s\n",ptr);
	}
	exit(0);
}

/* Result:
./exp_1 hello hello.txt helloword.txt
hello: directory
hello.txt: regular
helloword.txt: regular
*/
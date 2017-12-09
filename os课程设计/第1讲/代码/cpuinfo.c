#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<fcntl.h>
int main()
{
	int fd;
	const char *pathname;
	pathname="/proc/cpuinfo";
	fd=open(pathname,O_RDONLY,0666);
	char buf[1024*10];
	char buf_line[1024];
	int returnnum=read(fd,buf,1024);
	int i=1;	
	int j=0;
	int k=0;
	while(i!=8){
		if(buf[j]=='\n'){ i++; buf_line[k]='\0';j++;;k=0;}
		else{buf_line[k]=buf[j]; j++; k++;}	
	}	
	printf("%s\n",buf_line);

	close(fd);
	return 0;
	
}	

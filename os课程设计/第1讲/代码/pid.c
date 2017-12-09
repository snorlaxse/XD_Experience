#include<linux/module.h>
#include<linux/kernel.h>
#include<linux/proc_fs.h>
#include<linux/sched.h>
#include<linux/uaccess.h>
#define STRINGLEN 1024

char global_buffer[STRINGLEN];
struct proc_dir_entry *example_dir,* current_file,*symlink;

//用户读取current文件时，内核调用这个函数
int proc_read_current(char *page, char **start, off_t off,int count, int *eof, void *data)
{
	int len;
	try_module_get(THIS_MODULE);	// 模块引用计数增加1，保证模块不会被卸载
	//告诉用户，读出的文件内容是一行字符串，说明了当前进程的名字和pid
	len=sprintf(page,"cunt process usages:\nname: %s \npid : %d\n",current->comm,current->pid);
	module_put(THIS_MODULE);	// 模块引用计数减1
	return len;
}

//在加载模块的时候被调用，在加载模块的时候创建proc文件
static int init(void)
{
	//~/proc文件下，创建文件"15130110047"，其父目录为NULL
	example_dir=proc_mkdir("15130110047",NULL);  

	//创建只读文件
	//该函数将创建一个只读的proc文件，只是简单地调用create_proc_entry，
	//文件名为"047",文件类型和访问权限为0666，父目录为example_dir，
	//并将返回结构的read_proc域的值置为proc_read_current，data域的值置为NULL
	current_file=create_proc_read_entry("047",0666,example_dir,proc_read_current,NULL);

	// 创建符号链接proc_symlink()
	//该函数在example_dir目录下创建一个名字为"044_too"的符号链接文件，链接的目标是"047"
	symlink=proc_symlink("047_too",example_dir,"047"); 

	return 0;
}

//卸载模块，把创建的目录和文件都删除
static void cleanup(void)	
{
	//删除节点（文件或者目录）remove_proc_entry()     
	//void remove_proc_entry ( const char *name, struct proc_dir_entry *parent)
	//该函数将删除一个proc节点（按文件名删除）
	remove_proc_entry("047_too",example_dir);
	remove_proc_entry("047",example_dir);
	remove_proc_entry("15130110047",NULL);
}

	module_init(init);
	module_exit(cleanup);


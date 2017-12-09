/*
完善例子中的字符设备程序，使之满足以下功能：
* 安装设备后从设备中读出字符串为你的学号； --- read.c 
* 设备支持每次写入字符不超过1024个，超过部分被丢弃；----1024.c
* 用户可以读出最近写入到设备中的字符； ---- write.c read.c
* 设备关闭前不能被多次打开； ---- open.c
* 设备支持系统调用ioctl(int d, int req,…)
* req = 0x909090, 清除设备中写入的字符串; ---- clear.c
*/

/* rwbuf.c,  driver for virtual char-device*/

#include<linux/kernel.h>
#include<linux/module.h>
#include<linux/fs.h>
#include<linux/uaccess.h>

#define DEVICE_NAME "ZHENGJIAN"
#define RWBUF_CLEAR 0x909090
#define rwbuf_size 1024   // MAX size of buffer

static char rwbuf[rwbuf_size]="15130110047~";  // the buffer keeping string
static int rwlen=0;  // length of string
static char buffer[1024];
int inuse=0;
int i;

static int rwbuf_open ( struct inode *inode,  struct file * filep )  
{
        if (inuse == 1)return -1;
        inuse = 1;
        try_module_get(THIS_MODULE);	// increase the use count in struct module
        return 0;
}

static int rwbuf_close( struct inode *inode,  struct file * filep ) 
{
	inuse  =  0;
	module_put(THIS_MODULE);
	return 0;
}

static ssize_t rwbuf_write ( struct file * filep, const char *buf, size_t count, loff_t * ppos )
{       
	// 判断写入的长度是否有效
    // if(count>1024)      count=1024;

    // 从用户空间复制到内核空间
	copy_from_user(rwbuf, buf, count);  
	rwlen = count;

	//  print some message by printk();
	return count;
}

static ssize_t rwbuf_read( struct file* filep, char *buf, size_t count, loff_t* ppos)
{
	// 判断读取的长度是否有效
 	// if(count>1024)      count=1024;

 	 // 从用户空间复制到内核空间
	copy_to_user( buf, rwbuf, count);
	
	// print some message by printk()

	return count;
}

static int  rwbuf_ioctl  (struct inode *inode, struct file * filep,  unsigned int cmd,unsigned long arg )
{
	if ( cmd == RWBUF_CLEAR )  
        {
		rwlen = 0;	// clear buf by set its len to zero
                for(i=0;i<1024;i++){
                rwbuf[i]=buffer[i];}
		printk("rwbuf in kernel zero-ed\n");
	};

	return 0;
}

static struct file_operations rwbuf_fops =
{
	open:		rwbuf_open,
	release:	rwbuf_close,
	read:		rwbuf_read,
	write:		rwbuf_write,
	ioctl:		rwbuf_ioctl,

};



int init_module()
{
        printk("Hello %s\n",DEVICE_NAME);
        if ( register_chrdev(60,DEVICE_NAME,&rwbuf_fops))
        { 
		  printk("register error\n");
		  return -1;
        }
        printk("register ok\n");
        return 0;
}

void cleanup_module()

{
	unregister_chrdev(60,DEVICE_NAME);
	printk("unreg ok\n");
        printk("Bye!\n");
}

MODULE_LICENSE("GPL");

 //	   make
 //    mknod /dev/zhengjian c 60 0  创建设备文件
 //    insmod test1.ko 
 //    dmesg
 //    ./read
 //   ./write
 //    ./read
 //    ./1024
 //    ./open
 //   ./clear
 //    dmesg 
 //    history


// 安装与卸载： mknod  /dev/rwbuf  c  60  0	创建设备文件
// 	2.4: insmod   rwbuf.o  		安装设备驱动
// 	2.6  insmod rwbuf.ko
// 	/sbin/rmmod    rwbuf 		卸载设备驱动


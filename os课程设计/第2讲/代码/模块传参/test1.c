/*
* 编写一个内核模块；
* 编译该模块
* 加载、卸载该模块
*/
#include <linux/init.h>	 // for module_init()
#include <linux/module.h>  // must be include
#include <linux/moduleparam.h>
#include <linux/kernel.h>  // for printk()
 
/*内核允许对模块指定参数
* module_param(name, type, perm);
- perm是一个权限值，控制谁可以存取模块参数在sysfs中的表示。
- perm如0644是所有者可读写，组内可读，其他人可读；0444是所有者也是可读，
  宏是S_IRUGO，用的较广，也有为0的，表示禁止sys中的所有项。
* 这个宏定义应当放在任何函数之外, 典型地是出现在源文件的前面
* 应该总是为变量赋初值
*/
// 将获取参数值的变量声明为全局变量
static char *who = "Captain";
static int times = 1;
// 使用宏module_param来声明
module_param(times, int, S_IRUSR);
module_param(who, charp, S_IRUSR);

static int hello_init(void) {  // 模块入口函数
   int i;
   for (i = 0; i < times; i++)
     printk("(%d) hello, %s!\n", i, who);
   return 0;
}

static void hello_exit(void) {  // 模块退出函数
	
   printk("Goodbye, %s!\n", who);
}

module_init(hello_init);
module_exit(hello_exit);

// 2.4内核后，引入识别代码是否在GPL许可下发布的机制. 在使用非公开的源代码产品时会得到警告。
MODULE_LICENSE("GPL");	//设置模块遵守GPL证书，取消警告信息
MODULE_AUTHOR("Captain");	// 用来声明模块的作者
MODULE_DESCRIPTION("hello");	// 用来描述模块的用途 
MODULE_SUPPORTED_DEVICE()  // 声明模块支持的设备
// 这些宏都在头文件linux/module.h定义，使用这些宏只是用来提供识别信息


/*
内核允许对模块指定参数，这些参数可在装载模块时改变。
在运行insmod或者modprobe命令时给出参数的值。
* insmod test1.ko who="Captain" times=5
*/

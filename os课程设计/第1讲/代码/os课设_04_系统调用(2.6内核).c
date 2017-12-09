/*为Linux内核增加一个系统调用，并编写用户进程的程序来测试。
要求该系统调用能够完成以下功能：
(1). 该系统调用有1个整型参数，接收输入自己的学号；
(2). 若参数为奇数，则返回自己学号的最后5位。如您的学号为13051007，则返回51007；
(3). 若参数为偶数，则返回自己的学号的最后4位。如您的学号为13051004，则返回1004
*/
#include<stdio.h>
int main()
{   
    long num;
    printf("输入学号:");
    scanf("%ld",&num);
    printf("The return value is:%04d\n",syscall(337,num));
    return 0;
}


// 配置过程使用的代码
// asmlinkage long sys_mycall(long num);


// asmlinkage long sys_mycall(long  num)
// 	{
//
// 	    printk("This is my syscall from kernel.\n");
// 		printk("current pid is:%d.\n",current->pid);
//
// 	    long a;
//         if (num%2!=0)
//         {   
//             a=num%100000;
// 			return a;
//         }else
//            {
//                a=num%10000;
// 			   return a;    
//             }	
//     }
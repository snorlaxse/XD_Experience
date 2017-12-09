/*
Makefile说明：（2.6版本内核）
首先需要有配置并构建好的2.6内核源代码树，而且最好运行和模块对应的内核。2.6内核的模块要和内核源代码树中的目标文件连接。
2.6内核的构建系统Kbuild，使得内核源码外的内核模块编译跟内核编译统一起来，无须手动给定这些参数。
改变目录到用 -C 选项提供的内核源码目录，在那里找到内核的顶层makefile。M= 选项使 makefile 在试图建立模块目标前, 回到模块源码目录
*/

obj-m :=test1.o
KERNELDIR :=/lib/modules/$(shell uname -r)/build
PWD :=$(shell pwd)

modules:
	$(MAKE) -C $(KERNELDIR) M=$(PWD) modules

modules_install:
	$(MAKE) -C $(KERNELDIR) M=$(PWD) modules_install

clean:
	rm -rf *.o *~ core .depend .*.cmd *.ko *.mod.c .tmp_versions

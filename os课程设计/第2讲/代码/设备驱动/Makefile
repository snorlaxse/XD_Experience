obj-m :=test1.o
KERNELDIR :=/lib/modules/$(shell uname -r)/build
PWD :=$(shell pwd)
EXTRA_CFLAGS= -O0

modules:
	$(MAKE) -C $(KERNELDIR) M=$(PWD) modules

modules_install:
	$(MAKE) -C $(KERNELDIR) M=$(PWD) modules_install

clean:
	rm -rf *.o *~ core .depend .*.cmd *.ko *.mod.c .tmp_versions

obj-m :=pid.o
KERNELBUILD :=/lib/modules/`uname -r`/build

all:
	make -C $(KERNELBUILD) M=$(shell pwd) modules
clean:
	rm -rf *.o *.ko *.mod.c .*.cmd .tmp_versions

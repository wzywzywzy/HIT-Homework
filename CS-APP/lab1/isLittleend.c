#include <stdio.h>

int isLittleEndian() {
	unsigned int x = 0;
	x = 0;
	unsigned *p = &x;
	*(char*)p = 0x22;
	return x == 0x22 ? 1 : 0;
}

int main() {
	puts(isLittleEndian() ? "Little Endian" : "Big Endian");
	return 0;
}
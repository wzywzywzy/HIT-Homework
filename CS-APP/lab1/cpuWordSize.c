#include<stdio.h>

int cpuwordsize()
{
	char x;
	return sizeof(&x) << 3;
}
int main()
{
	printf("%d", cpuwordsize());
	return 0;
}
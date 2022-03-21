#include <stdio.h>
#include <stdlib.h>

int fib_loop1(int n)
{
	int x=1, y=1, z=0;
	for (int i = 2; i < n; i++)
	{
		z = x + y;
		x = y;
		y = z;
	}
	return z;
}

long fib_loop2(int n)
{
	long x=1, y=1, z=0;
	for (int i = 2; i < n; i++)
	{
		z = x + y;
		x = y;
		y = z;
	}
	return z;
}

unsigned int fib_loop3(int n)
{
	unsigned int x=1, y=1, z=0;
	for (int i = 2; i < n; i++)
	{
		z = x + y;
		x = y;
		y = z;
	}
	return z;
}

unsigned long fib_loop4(int n)
{
	unsigned long x=1, y=1, z=0;
	for (int i = 2; i < n; i++)
	{
		z = x + y;
		x = y;
		y = z;
	}
	return z;
}

int main()
{
	int a, b, c, d;
	printf("int的情况：\n");
	for (a = 3; fib_loop1(a) > 0; a++);
    printf("%d\n",a);

	printf("long的情况：\n");
	for (b = 3; fib_loop2(b) > 0; b++);
    printf("%d\n",b);

	printf("unsigned int的情况：\n");
	for (c = 3; fib_loop3(c) > fib_loop3(c-1); c++);
    printf("%d\n", c);

	printf("unsigned long的情况：\n");
	for (d = 3; fib_loop4(d) > fib_loop4(d - 1); d++);
	printf("%d\n", d);
	return 0;
}

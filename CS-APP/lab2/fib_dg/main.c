#include <stdio.h>
#include <stdlib.h>

int fib_dg1(int n)
{
	if (n <= 2)return 1;
	return fib_dg1(n - 2) + fib_dg1(n - 1);
}

long fib_dg2(int n)
{
	if (n <= 2)return 1;
    return fib_dg2(n - 2) + fib_dg2(n - 1);
}

unsigned int fib_dg3(int n)
{
	if (n <= 2)return 1;
	return fib_dg3(n - 2) + fib_dg3(n - 1);
}

unsigned long fib_dg4(int n)
{
	if (n <= 2)return 1;
	return fib_dg4(n - 2) + fib_dg4(n - 1);
}

int main()
{
	int a, b, c, d;
	for (a = 3; fib_dg1(a) > 0; a++);
	printf("int-> %d\n", a);

	for (b = 3; fib_dg2(b) > 0; b++);
	printf("long -> %d\n", b);

	for (c = 3; fib_dg3(c) > fib_dg3(c - 1); c++);
	printf("unsigned int-> %d\n", c);

	for (d = 3; fib_dg4(d) > fib_dg4(d - 1); d++);
	printf("unsigned long-> %d\n", d);
	return 0;
}

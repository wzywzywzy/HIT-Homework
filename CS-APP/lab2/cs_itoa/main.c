#include <stdio.h>
#include <stdlib.h>

void cs_itoa(int num, char *s)
{
	int i = 0, j, sign;
	if(num<0)sign = -1,num = -num;
	char *t = s;
	do {
		*t++ = num % 10 + '0';//取下一个数字
	} while (num /= 10);//删除该数字
	if (sign < 0)
		{*t++ = '-';i++;}

	do{//生成的数字是逆序的，所以要逆序输出
		t--;
		printf("%c", *t);
    } while(t!=s);
    return ;
}

int main()
{
    char str[128];
	int number;
	printf("请输入一个整数\n");
	scanf("%d", &number);
    cs_itoa(number, str);
	return 0;
}

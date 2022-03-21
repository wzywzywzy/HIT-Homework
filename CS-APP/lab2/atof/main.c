#include <stdio.h>
#include <stdlib.h>
# include <stdio.h>
# include <string.h>
# include <ctype.h>

float cs_atof(char *s){
	float x=0,f=1,y=0.1;
	while (*s && !isdigit(*s)){if ((*s) == '-') f = -1; s++;}
	while (*s && isdigit(*s)) x = x*10+(*s)-'0', s++;
    if (*s == '.')
    {
        s++;
        while (isdigit(*s)) x += ((*s)-'0')*y, s++, y*=0.1;
    }
	return x*f;
}

float my_atof(char* str)
{
	float num = 0, integer = 1, flag = 1;
	if (*str && *str == '-')
	{
		flag = -1;
		str++;
	}
	while (*str && *str >= '0' && *str <= '9')
	{
		num = num * 10 + *str - '0';
		str++;
	}
	if (*str && *str == '.')
	{
		str++;
	}
	while (*str && *str >= '0' && *str <= '9')
	{
		integer *= 0.1;
		num = num + (*str - '0') *integer;
		str++;
	}
	return num * flag;
}

int main()
{
	char str[128];
	printf("请输入一个字符串\n");
	scanf("%s", str);
	float result = 0;
	result = cs_atof(str);
	printf("字符串%s    转换为浮点数为%f", str, result);
	return 0;
}

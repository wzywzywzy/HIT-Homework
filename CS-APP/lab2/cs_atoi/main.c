#include <stdio.h>
#include <stdlib.h>

int cs_atoi(char *str)
{
    int num = 0;
    int signedflag = 1;   //符号位的设置
    if (*str && *str == '-')
    {
        signedflag = -1;
        str++;
    }
    while (*str && *str >= '0' && *str <= '9')
    {
        num = num * 10 + *str - '0';
        str++;
        if (num < 0)
        {
            num = 2147483647;//chuliyichu
            break;
        }
    }
    return num * signedflag;
}

int main()
{
    char str[20];
    int result;
    printf("请输入一个字符串\n");
    scanf("%s", str);
    result = cs_atoi(str);
    printf("string：%s -->int：%d\n", str, result);
    return 0;
}

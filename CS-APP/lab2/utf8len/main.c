#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int utf8len(char *a)
{
    int i = 0, word = 0, len = 0;
    for(;a[i]!='\0';i+=len,word++)
    {
        unsigned char byte = a[i];
        if(byte > 0xFC)
            len = 6;
        else if(byte > 0xF8)
            len = 5;
        else if(byte > 0xF0)
            len = 4;
        else if(byte > 0xE0)
            len = 3;
        else if(byte > 0xC0)
            len = 2;
        else
            len = 1;
    }
    return word;
}

int main()
{
    int num;
    char str[1000], *p;
    printf("Please input the string\n");
    scanf("%s",str);
    p = str;
    num = utf8len(p);
    printf("%d", num);
    return 0;
}



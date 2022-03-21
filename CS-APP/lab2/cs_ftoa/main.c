#include <stdlib.h>
#include <stdio.h>

int cs_itoa(int x,char *str){
    char rev[128];
    char *t = str;
    if (x < 0) x = -x;

    char *p = rev;
    do
    {
        *p++ = x%10+'0';
    }while(x/=10);
    while (p != rev) *t++ = *--p;
    *t = '\0';
    return t-str;
}

void cs_ftoa(float x, int pre, char *s) {
    char *t = s;
    int y=(int)x;
    x-=y;
    if (x < 0) *t++ = '-',x = -x;
    t = t + cs_itoa(y , t);
    *t++ = '.';
    x += 5 * pow(10.0, -pre-1);
    for (int i=0; i<pre; ++i) {
        x = x*10;
        *t++ = ((int)x) + '0';
        x -= (int)x;
    }
    *t = '\0';
    printf("%s",s);
    return;
}

int main(){
    char s[128];
    float x;
    scanf("%f",&x);
    cs_ftoa(x,5,s);
    return 0;
}

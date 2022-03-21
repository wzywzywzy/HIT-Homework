#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <float.h>

#define _GNU_SOURCE 1
typedef unsigned char *b_pointer;

void show_bytes(b_pointer str, size_t len)
{
    size_t i;
    for(i=0; i<len; i++)
    {
        printf("%.2x", str[i]);
    }
    printf("\n");
    return;
}

void show(float x)
{
    show_bytes((b_pointer)&x, sizeof(float));
    return;
}

int main()
{
    float A = +0.0f,B = -0.0f;
    float C = FLT_TRUE_MIN;
    float D = FLT_MAX,E = FLT_MIN;
    float F = INFINITY;
    float G = NAN;

    printf("+0: %E\n", A);
    printf("-0: %E\n", B);
    printf("最小浮点正数: %E\n", C);
    printf("最大浮点正数: %E\n", D);
    printf("最小正的规格化浮点数: %E\n", E);
    printf("正无穷大: %fE\n", F);
    printf("Nan: %f.64\n", G);

    printf("16进制：\n");
    printf("+0:");
    show(A);
    printf("-0:");
    show(B);
    printf("最小浮点正数:");
    show(C);
    printf("最大浮点正数:");
    show(D);
    printf("最小的规格化浮点正数:");
    show(E);
    printf("正无穷大:");
    show(F);
    printf("Nan:");
    show(G);

    return 0;
}

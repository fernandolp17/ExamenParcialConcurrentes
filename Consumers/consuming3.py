#!/usr/bin/env python

import socket

HOST = "localhost"
PORT = 23232

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

toServer = "Consuming:0"
toServerBytes = (toServer+"\n").encode('utf-8')
sock.sendall(toServerBytes)

data = sock.recv(1024).decode("utf-8")

print("=>", data)

while(True):
    # sock.sendall(b"Adios\n")
    data = sock.recv(1024).decode("utf-8")
    print("=> SMS no leidos: "+data)
    print(len(data))
    toServer = input("Desea leer sms: ")
    toServerBytes = (toServer+"\n").encode('utf-8')
    sock.sendall(toServerBytes)

    for cont in range(len(data)-1):
        data = sock.recv(1024).decode("utf-8")
        print("=>", data)

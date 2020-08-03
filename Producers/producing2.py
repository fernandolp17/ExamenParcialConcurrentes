#!/usr/bin/env python

import socket

#HOST = "54.173.179.167"
HOST = "localhost"
PORT = 23232

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

toServer="Producing"
toServerBytes=(toServer+"\n").encode('utf-8')
sock.sendall(toServerBytes)
data = sock.recv(1024).decode("utf-8")

print("=>", data)

# sock.sendall(b"Adios\n")
while data !='exit':
  toServer = input("Ingrese un sms: ")
  toServerBytes = (toServer+"\n").encode('utf-8')
  sock.sendall(toServerBytes)

print("=>"+"salio producing")
sock.close()
print("Socket cerrado")
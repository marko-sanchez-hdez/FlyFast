#!/usr/bin/env python3

import socket
import mysql.connector


PORT = 6686

#Connect to database
db = mysql.connector.connect(
    host="localhost",
    user="marko",
    password="AdminUser25062002#$",
    database="FlyFastDB"
)

cursor = db.cursor()

sql = "INSERT INTO Users (name, username, password) VALUES (%s, %s, %s)"

#Function to insert a user in the MySQL DB
def registUser(data):
    val = [(data[0], data[1], data[2])]
    cursor.executemany(sql,val)
    db.commit()
    return None

def server():
    s = socket.socket()
    s.bind(('0.0.0.0',PORT))
    s.listen(5)

    while True:
        c, addr = s.accept()
        msg = str(c.recvfrom(1024)[0])
        c.close()
        msg = msg[10:len(msg)-1]
        data = msg.split(",")
        registUser(data)

def main():
    server()

if __name__=="__main__":
    main()

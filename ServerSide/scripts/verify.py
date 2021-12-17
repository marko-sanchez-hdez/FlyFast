#!/usr/bin/env python3

import socket
import mysql.connector


PORT = 6898

#Connect to database
db = mysql.connector.connect(
    host="localhost",
    user="marko",
    password="AdminUser25062002#$",
    database="FlyFastDB"
)

cursor = db.cursor()

def verifyUser(data):
    username = data[0]
    password = data[1]
    sql = "SELECT username, password FROM Users WHERE username = '"+username+"'"
    cursor.execute(sql)
    result = cursor.fetchall()
    if len(result) == 0:
        tmp = "false"
    else:
        result = list(result[0])
        if username == result[0] and password == result[1]:
            tmp = "true"
        else:
            tmp = "false"
    return tmp

def server():
    s = socket.socket()
    s.bind(('',PORT))
    s.listen(5)

    while True:
        c, addr = s.accept()
        tmp = str(c.recvfrom(1024)[0])
        tmp = tmp[10:len(tmp)-1]
        data = tmp.split(",")
        ans = verifyUser(data)
        ans = ans.encode("UTF8")
        c.send(ans)
        c.close()

def main():
    server()

if __name__=="__main__":
    main()

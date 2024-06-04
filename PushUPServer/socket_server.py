import socket
from database import *
import re;
import selectors
import types
import datetime
import time
import threading
#import cv2
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(("10.114.7.4", 3030))
s.listen()
s.setblocking(False)
sel = selectors.DefaultSelector()
sel.register(s, selectors.EVENT_READ, data=None)

#conn, addr = s.accept()

def getId(query):
    email, password = query.split('#')
    idUser = getID(email, password)
    answer = ''
    if (idUser == '-1'):
        answer = 'error: incorrect password'
    elif idUser == '-2':
        answer = 'error: user is not found'
    else:
        answer = idUser
    return str(answer)

def getNewID(query):
    user = query.split('#')
    answer = InsertUser(user)
    return answer

def getInformation(idUser):
    inf = setInformation(idUser)
    answer = ''
    for i in inf:
        answer += str(i) + '#'
    answer = answer[:len(answer)-1] 
    return answer

def setDataUser(query):
    user = query.split('#')
    answer = updateData(user[0], user[1:])
    return answer

def setPhoto(query):
    idUser, photo = query.split('#')
    answer = InsertPhoto(idUser, photo)
    return answer

def setQuantityDay(query):
    idUser, x = query.split('#')
    answer = updateQuantityDay(idUser, x)
    return answer

def getColibration(idUser):
    colibr = setColibration(idUser)
    answer = ''
    for i in colibr:
        answer += str(i[0]) + '#' + str(i[1]) + '#'
    answer = answer[:len(answer)-1]
    return answer

def update_Calibration(query):
    idUser, idTraining, x = query.split('#')
    answer = updateCalibration(idUser, idTraining, x)
    return answer

def getQuantity(query):
    idUser, idTraining = query.split('#')
    quantity = extractQuantity(idUser, idTraining)
    answer = ''
    for i in quantity:
        answer += str(i) + '#'
    answer = answer[:len(answer)-1]
    return answer

def setQuantity(query):
    idUser, idTraining, x = query.split('#')
    answer = updateQuantity(idUser, idTraining, x)
    return answer

def getStatistic(query):
    answer = ''
    idTraning, nameTraining, x, y = query.split('#')
    statistic = setStatistic(idTraning, nameTraining, x, y)
    if statistic == -110:
        return 'error getting ststistics'
    for i in statistic:
        answer += str(i[0]) + '#' + str(i[1]) + '#'
    answer = answer[:len(answer)-1]
    return answer

def addNewNtaining(query):
    idUser, idTraining = query.split('#')
    return addTraining(idUser, idTraining) 

def accept_wrapper(sock):
    conn, addr = sock.accept()
    print(f"Accepted connection from {addr}")
    conn.setblocking(False)
    data = types.SimpleNamespace(addr=addr, inb=b"", outb=b"")
    events = selectors.EVENT_READ | selectors.EVENT_WRITE
    sel.register(conn, events, data=data)

def service_connection(key, mask):
    sock = key.fileobj
    data = key.data
    if mask & selectors.EVENT_READ:
        try:
            recvData = sock.recv(1024)
        except:
            recvData = None
         
        if recvData:
            request = recvData.decode('utf-8')
            code = int(request[0:3])    
            request = re.sub(r'.','',request, count = 4)
            print(request)
            answer = processingRequest(request, code)
            print(answer)
            data.outb += answer.encode('ascii')
        else:
            print(f"Closing connection to {data.addr}")
            sel.unregister(sock)
            sock.close()
    if mask & selectors.EVENT_WRITE:
        if data.outb:
            sent = sock.send(data.outb)  # Should be ready to write
            data.outb = data.outb[sent:]

def processingRequest (request, code):
    answer = ''
    match code:
        case 100:
            answer = getId(request)
        case 101:
            answer = getNewID(request)
        case 102:
            answer = getInformation(request)
        case 103:
            answer = setDataUser(request)
        case 104:
            answer = setPhoto(request)
        case 105:
            answer = setQuantityDay(request)
        case 106:
            answer = getColibration(request)
        case 107:
            answer = update_Calibration(request)
        case 108:
            answer = getQuantity(request)
        case 109:
            answer = setQuantity(request)
        case 110:
            answer = getStatistic(request)
        case 111:
            answer = addNewNtaining(request)
    answer += '\n'
    return answer

def checkTime():
    currentDay = datetime.date.today()
    currentMonth = datetime.datetime.now().month
    currentYear = datetime.datetime.now().year
    while True:
        if datetime.date.today() != currentDay:
            currentDay = datetime.date.today()
            newDay()

        if datetime.datetime.now().month != currentMonth:
            currentMonth = datetime.date.today()
            newMonth()

        if datetime.datetime.now().year != currentYear:
            currentYear = datetime.datetime.now().year
            newYear()
        
        time.sleep(600)
try:
    while True:
        thread = threading.Thread(target=checkTime)
        thread.start()
        data = sel.select(timeout=None)
        for key, mask in data:
            if key.data is None:
                accept_wrapper(key.fileobj)
            else:
                service_connection(key, mask)
except KeyboardInterrupt:
    print("пользователь отключился")
finally:
    conn.close()


import socket
from database import *
import re;
#import cv2
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(("10.114.7.4", 3030))
s.listen(1)
conn, addr = s.accept()

def getId(query):
    email, password = query.split('#')
    idUser = getID(email, password)
    answer = ''
    if (idUser == -1):
        answer = 'error: incorrect password'
    elif idUser == -2:
        answer = 'error: user is not found'
    else:
        answer = str(idUser)
    return answer

def getNewID(query):
    user = query.split('#')
    answer = str(InsertUser(user))
    return answer

def getInformation(idUser):
    inf = setInformation(idUser)
    answer = ''
    for i in inf:
        answer += str(i) + '#'
    answer = answer[:len(answer-1)] 
    return answer

def setDataUser(query):
    idUser, user = query.split('#')
    answer = updateData(idUser, user)
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
        answer += i[0] + '#'
    answer = answer[:len(answer-1)]
    return answer

def updateCalibration(query):
    idUser, idTraining, x = query.splite('#')
    answer = updateCalibration(idUser, idTraining, x)
    return answer

def getQuantity(query):
    idUser, idTraining = query.splite('#')
    quantity = extractQuantity(idUser, idTraining)
    answer = ''
    for i in quantity:
        answer += str(i) + '#'
    answer = answer[:len(answer-1)]
    return answer

def setQuantity(query):
    idUser, idTraining, x = query.splite('#')
    answer = updateQuantity(idUser, idTraining, x)
    return answer

def getStatistic(query):
    answer = ''
    idTraning, nameTraining, x, y = query.splite('#')
    statistic = setStatistic(idTraning, nameTraining, x, y)
    for i in statistic:
        answer += str(i[0]) + '#' + str(i[1]) + '#'
    answer = answer[:len(answer-1)]
    return answer

while True:
    data = conn.recv(1024) #прием
    request = data.decode('utf-8')
    code = int(request[0:3])    
    request = re.sub(r'.','',request, count = 4)
    print(request)
    answer = ''
    match code:
        case 100:
            answer = getID(request)
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
            answer = updateCalibration(request)
        case 108:
            answer = getQuantity(request)
        case 109:
            answer = setQuantity(request)
        case 110:
            answer = getStatistic(request)
    answer += '\n'
    conn.send(answer.encode('ascii')) #отправка
    print("Отправил ответ")
conn.close()


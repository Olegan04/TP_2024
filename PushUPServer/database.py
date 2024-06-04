import psycopg2
from datetime import date
from pandas.io.sql import DatabaseError
#import cv2
conn = psycopg2.connect(
    dbname="postgres",
    user="postgres", 
    password="123")
cur = conn.cursor()

def InsertUser(user):
    try:
        posgres = "INSERT INTO users (binding, password, name_user, date_of_birth, gender, country, location, quantity_of_day) VALUES (%s, %s, %s, %s, %s, %s, %s, 0)"
        cur.execute(posgres, user)
        conn.commit() 
    except:
        return '-101'
    
    posgres = "select id_user from users where binding = %s"
    cur.execute(posgres, (user[0],))
    p = cur.fetchall()
    print(str(p[0][0]))
    posgres = "INSERT INTO push_ups (id_user, id_training, calibration, quantity_per_day, quantity_per_month, quantity_per_year, quantity_per_all_time) VALUES (%s, 1, 0, 0, 0, 0, 0)"
    cur.execute(posgres, (str(p[0][0]), ))
    conn.commit()  
    return str(p[0][0])

def getID(email, password):
    try:
        posgres = "select id_user, password from users where binding = %s"
        cur.execute(posgres, (email,))
        p = cur.fetchall()
    except:
        return -100
    if (len(p) == 0):
        return '-2'
    elif(p[0][1] == password):
        return p[0][0]
    else:
        return '-1'
    
def InsertPhoto(idUser, photo):
    try:
        posgres = "UPDATE user set photo = %s where id_user = %s"
        cur.execute(posgres, (photo, idUser))
        conn.commit()
    except:
        return -104

def setInformation(idUser):
    try:
        posgres = "select name_user, date_of_birth, gender, country, location from users where id_user = %s"
        cur.execute(posgres, (idUser,))
        p = cur.fetchall()
    except:
        return -102
    return p[0]

def setColibration(idUser):
    try:
        posgres = "select id_training, calibration from push_ups where id_user = %s"
        cur.execute(posgres, (idUser,))
        p = cur.fetchall()
    except:
        return 'error obtaining calibration data'
    return p

def updateCalibration(idUser, idTraining, x):
    try:
        posgres = "UPDATE push_ups set calibration = %s where id_user = %s and id_training = %s"
        cur.execute(posgres, (x, idUser, idTraining))
        conn.commit()
    except:
        return 'error adding calibration data'
    return 'data change'
 
def updateQuantity(idUser, idTraining, x):
    try:
        posgres = "select quantity_per_day, quantity_per_month, quantity_per_year, quantity_per_all_time from push_ups where id_user = %s and id_training = %s"
        cur.execute(posgres, (idUser, idTraining, ))
        p = cur.fetchall()
    except:
        return 'error adding in the number of pusp-ups'
    pp = []
    for i in p[0]:
        pp.append(str(i + int(x)))
    posgres = "UPDATE push_ups set quantity_per_day = %s, quantity_per_month = %s, quantity_per_year = %s, quantity_per_all_time = %s where id_user = %s and id_training = %s"
    cur.execute(posgres, tuple(pp) + (idUser, idTraining,))
    conn.commit()
    return 'quantity of pusp-ups change'

def  updateQuantityDay(idUser, x):
    try:
        posgres = "UPDATE users set quantity_of_day = %s where id_user = %s"
        cur.execute(posgres, (x, idUser, ))
        conn.commit()
    except DatabaseError as e:
        return 'error' + str(e) 
    return 'quantity of day change'

def updateData(idUser, user):
    try:
        posgres = "UPDATE users set name_user = %s, date_of_birth = %s, gender = %s, country = %s, location = %s where id_user = %s"
        cur.execute(posgres, tuple(user) + (idUser, ))
        conn.commit()
    except DatabaseError as e:
        return 'error addind data user' + str(e)
    return 'yra'
    
def extractQuantity(idUser, idTraining):
    try:
        posgres = "SELECT quantity_per_day, quantity_per_month, quantity_per_year, quantity_per_all_time FROM push_ups where id_user = %s and id_training = %s"
        cur.execute(posgres, (idUser, idTraining, ))
        p = cur.fetchall()
    except:
        return 'error extract quantity'
    return p[0]

def setStatistic(idTraining, timeInterval, x, y):
    try:
        timeInterval = 'p.quantity_per_' + timeInterval
        posgres = ('SELECT u.name_user, ' + timeInterval +'''
                FROM users u JOIN push_ups p
                ON u.id_user = p.id_user
                WHERE p.id_training = %s
                ORDER BY ''' + timeInterval + ''' DESC
                OFFSET (%s)
                LIMIT %s''')
        
        cur.execute(posgres, (idTraining, x, y, ))
        p = cur.fetchall()
    except DatabaseError as e:
        print (str(e))
        return -110
    return p

def addTraining(idUser, IdTraining):
    try:
        posgres = '''INSERT INTO push_ups 
        (id_user, id_training, calibration, quantity_per_day, quantity_per_month, quantity_per_year, quantity_per_all_time)
        VALUES (%s, %s, 0, 0, 0, 0, 0)'''
        cur.execute(posgres, (idUser, IdTraining))
        conn.commit()  
    except DatabaseError as e:
        return str(e)
    return 'Ura'
    
def newDay():
    posgres = 'UPDATE push_ups SET quantity_per_day = 0'
    cur.execute(posgres)
    conn.commit()

def newMonth():
    posgres = 'UPDATE push_ups SET quantity_per_month = 0'
    cur.execute(posgres)
    conn.commit()

def newYear():
    posgres = 'UPDATE push_ups SET quantity_per_year = 0'
    cur.execute(posgres)
    conn.commit()

# cur.close()
# conn.close()
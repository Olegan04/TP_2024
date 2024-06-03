import psycopg2
from users import Users
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
        return "error adding a new user"
    
    posgres = "select id_user from users where binding = %s"
    cur.execute(posgres, (user[0],))
    p = cur.fetchall()
    posgres = "INSERT INTO push_ups (id_user, id_training, colibration, quantity_per_day, quantity_per_mounth, quantity_per_year, quantity_per_all_time) VALUES (%s, 1, 0, 0, 0, 0, 0)"
    cur.execute(posgres, p[0][0])
    conn.commit()  
    return p[0][0]

def getID(email, password):
    try:
        posgres = "select id_user, password from users where binding = %s"
        cur.execute(posgres, (email,))
        p = cur.fetchall()
    except:
        return "error user verification"
    if (len(p) == 0):
        return -2
    elif(p[0][1] == password):
        return p[0][0]
    else:
        return -1
    
def InsertPhoto(idUser, photo):
    try:
        posgres = "UPDATE user set photo = %s where id_user = %s"
        cur.execute(posgres, (photo, idUser))
        conn.commit()
    except:
        return 'error adding photo'

def setInformation(idUser):
    try:
        posgres = "select name_user, date_of_birth, gender, country, location from users where id_user = %s"
        cur.execute(posgres, (idUser,))
        p = cur.fetchall()
    except:
        return "error obtaining user information"
    return p[0]

def setColibration(idUser):
    try:
        posgres = "select calibration from push_ups where id_user = %s"
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
        posgres = "select quantity_per_day, quantity_per_mounth, quantity_per_year, quantity_per_all_time from push_ups where id_user = %s and id_training = %s"
        cur.execute(posgres, (idUser, idTraining, ))
        p = cur.fetchall()
    except:
        return 'error adding in the number of pusp-ups'
    for i in p[0]:
        i += x
    posgres = "UPDATE push_ups set quantity_per_day = %s, quantity_per_mounth = %s, quantity_per_year = %s, quantity_per_all_time = %s where id_user = %s and id_training = %s"
    cur.execute(posgres, (p[0], idUser, idTraining))
    conn.commit()
    return 'quantity of pusp-ups change'

def  updateQuantityDay(idUser, x):
    try:
        posgres = "UPDATE users set quantity_of_day = %s where is_user = %s"
        cur.execute(posgres, (x, idUser))
        conn.commit()
    except:
        return 'error adding quantity of day'
    return 'quantity of day change'

def updateData(idUser, user):
    try:
        posgres = "UPDATE users set name_user = %s, date_of_birth = %s, gender = %s, country = %s, location = %s where id_user = %s"
        cur.execute(posgres, (user, idUser))
        conn.commit()
    except:
        return 'error addind data user'
    
def extractQuantity(idUser, idTraining):
    try:
        posgres = "SELECT quantity_per_day, quantity_per_mounth, quantity_per_year, quantity_per_all_time FROM push_ups where id_user = %s and id_training = %s"
        cur.execute(posgres, (idUser, idTraining, ))
        p = cur.fetchall()
    except:
        return 'error extract quantity'
    return p[0]

def setStatistic(idTraining, nameTraining, x, y):
    try:
        posgres = ('''SELECT u.user_name, %s
                FROM user u LEFT JOIN push_ups p
                ON u.id_user = p.id_user
                WHERE p.id_training = %s
                ORDER BY %s
                OFFSET (SELECT COUNT(*) - %s FROM push_ups)
                LIMIT %s''')
        nameTraining = 'p.' + nameTraining
        cur.execute(posgres, (nameTraining, idTraining, nameTraining, x, y, ))
        p = cur.fetchall()
    except:
        return 'error getting ststistics'
    return p

# cur.close()
# conn.close()
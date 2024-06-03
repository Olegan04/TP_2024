import psycopg2
from datetime import date
class Users:
    def __init__(self, binding, password, name, age, gender, country, location):
        self.binding = binding
        self.password = password
        self.name = name        
        self.age = age 
        self.gender = gender
        self.country = country
        self.location = location
        self.days = 0; 

    def get(self):
        print(self.days)
    def ChangeName(self, new_name):
        self.name = new_name
    def SetTG(self, link):
        self.TG = link
    
    def list1(self):
        user = (self.binding, self.password, self.name, self.age, self.gender, self.country, self.location)
        return user
    

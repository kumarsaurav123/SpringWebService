#how to run.
clone the project
go to root folder
create database using schema under filr.
/src/main/resources/db.sql
modify your database properties in database.properties
run jar using command java -jar target/*.jar 





# SpringWebService API's End points
API


register
http://localhost:8080/account/register?name=accountuser&cellnumber=8008552320

{"id":100013,"name":"accountuser","cellnumber":"8008552320","email":null,"message":"Register Success!Use id  and password to login","password":"ODAwODU1MjMyMA==","balance":0.0}

Login
http://localhost:8080/account/100013/login?pass=ODAwODU1MjMyMA==
{"id":100013,"name":"accountuser","cellnumber":"8008552320","email":null,"message":"login Success!","password":null,"balance":0.0}

deposit into account

http://localhost:8080/account/100013/deposit?amount=100000
{"id":100013,"name":"accountuser","cellnumber":"8008552320","email":null,"message":null,"password":null,"balance":100000.0}

add a payee

http://localhost:8080/account/100013/payee/add?name=saurav&cellnumber=9010090100&email=abc@gmail.com
{"id":1,"name":"saurav","cellnumber":"9010090100","email":"abc@gmail.com","userid":"100013","message":"Payee Added!Use id to transact"}

get a payee

http://localhost:8080/account/100013/payee/1
{"id":1,"name":"saurav","cellnumber":"9010090100","email":"abc@gmail.com","userid":"100013","message":"Payee Found!Use id to transact"}

pay 

http://localhost:8080/account/100013/payee/1/pay?amount=5000
{"id":100013,"name":"accountuser","cellnumber":"8008552320","email":null,"message":"Payment successfull","password":null,"balance":95000.0}




logout
http://localhost:8080/account/100002/logout?pass=ODAwODU1MjMyMA==
{"id":100002,"name":"accountuser","cellnumber":"8008552320","email":null,"message":"login Success!","password":null,"balance":0.0}









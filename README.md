How to run the app.

    1. build using command "mvn clean install" or use maven wrapper if your system does not have maven, "mvnw clean install"
    2. run command "java -jar target/homecredit-0.0.1-SNAPSHOT.jar"

How to access the API

    1. http://localhost:8080/weather/listInfo - to get api for three cities (London, Prague, San Francisco)
    2. http://localhost:8080/weather/store - to save last 5 unique results from ^ above api
    3. http://localhost:8080/weather/all - to retrieve all weather from db 

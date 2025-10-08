Drone Application

git clone https://github.com/JMMOrosca/Drone-Application.git
cd Drone-Application
mvn clean install
mvn spring-boot:run

Here is the curl for Postman Collection

#POST
curl --location 'http://localhost:8080/api/drones' \
--header 'Content-Type: application/json' \
--data '{
    "id": null,
    "serialNo.": "",
    "model": "",
    "weight": ,
    "battery": ,
    "state": "",
    "medications": []
}'
#GET
curl --location 'http://localhost:8080/api/drones/available'
#GET
curl --location 'http://localhost:8080/api/drones/droneId/medication'
#GET
curl --location 'http://localhost:8080/api/drones/droneId/battery'
#POST
curl --location 'http://localhost:8080/api/drones/2/load' \
--header 'Content-Type: application/json' \
--data '[{
    "name": "Aspirin",
    "weight": 325,
    "code": "ASP-325",
    "image": "Aspirin",
    "drone_id": 1
}]'

H2 database is in file 

Created as well a component for sample data

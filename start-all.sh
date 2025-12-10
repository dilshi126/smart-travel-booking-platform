#!/bin/bash

echo "Starting Smart Travel Platform Microservices..."
echo "================================================"

# Start User Service
echo "Starting User Service on port 8081..."
cd user-service && mvn spring-boot:run &
sleep 5

# Start Flight Service
echo "Starting Flight Service on port 8082..."
cd ../flight-service && mvn spring-boot:run &
sleep 5

# Start Hotel Service
echo "Starting Hotel Service on port 8083..."
cd ../hotel-service && mvn spring-boot:run &
sleep 5

# Start Payment Service
echo "Starting Payment Service on port 8084..."
cd ../payment-service && mvn spring-boot:run &
sleep 5

# Start Notification Service
echo "Starting Notification Service on port 8085..."
cd ../notification-service && mvn spring-boot:run &
sleep 5

# Start Booking Service
echo "Starting Booking Service on port 8086..."
cd ../booking-service && mvn spring-boot:run &

echo ""
echo "All services starting..."
echo "Wait a few seconds for all services to be ready."
echo ""
echo "Service URLs:"
echo "  User Service:         http://localhost:8081/swagger-ui.html"
echo "  Flight Service:       http://localhost:8082/swagger-ui.html"
echo "  Hotel Service:        http://localhost:8083/swagger-ui.html"
echo "  Payment Service:      http://localhost:8084/swagger-ui.html"
echo "  Notification Service: http://localhost:8085/swagger-ui.html"
echo "  Booking Service:      http://localhost:8086/swagger-ui.html"

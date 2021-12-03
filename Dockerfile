FROM openjdk:8-jdk-apline
COPY target/address-1.0.0.jar .
ENTRYPOINT ["java", "-DapiKey=AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw", "-jar", "address-1.0.0.jar"]

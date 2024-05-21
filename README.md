# BSPQ23-E4

# LuxWheels

## Overview

This project includes LuxWheels application, a SpringBoot application built with Java and Maven. It uses JUnit for testing and JUnitPerf for performance testing.

## Prerequisites
Prerequisites
Java Development Kit (JDK)
Version: 17 or higher

Apache Maven
Version: 3.6.0 or higher

MySQL
Version: 8.0 or higher


## Project compilation and running

### Server Side

To compile and start the server-side application, navigate to the server directory and run:

`
cd server 
`\
`
mvn clean install
`\
`
mvn spring-boot:run`

### Client Side

To compile and start the client-side application, navigate to the client directory and run:

Server must be running before running the client.


`
cd client
`\
`
mvn clean install
`\
`
mvn spring-boot:run`


## Project testing

## Server side

For testing the tests of the server side, navigate to the server directory and run:

`
cd server 
`\
`
mvn test
`

## Client side

For testing the tests of the client sive, first of all navigate to the server directory and run the server:

`
cd server 
`\
`
mvn spring-boot:run`

Then, go to the client directory and run the client tests:

`
cd client
`\
`
mvn test
`



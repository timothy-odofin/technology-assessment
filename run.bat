
@echo off
SET DEVELOPMENT_HOME=C:\Users\JIDE\Documents\fordsoft\task\retail

cd %DEVELOPMENT_HOME%\retailstore\

REM Building artifacts
REM Running Unit and integration testing
call mvn clean install

REM shutdown or stop any previous docker-compose that is running
call docker-compose down

REM Build the docker image using the Dockerfile 
call docker-compose build

REM Start  docker container in an interactive mode
call docker-compose up

REM press ctl+c to stop  docker container in an interactive mode

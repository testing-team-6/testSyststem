@echo off

SET PROFILE=dist
REM Created war package
mvn clean package -DskipTests -P %PROFILE%

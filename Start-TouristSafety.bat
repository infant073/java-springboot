@echo off
title Smart Tourist Safety Server Launcher
echo ========================================================
echo   Starting Smart Tourist Safety Monitoring Application
echo ========================================================
echo.
cd /d "%~dp0"
echo Launching Spring Boot Backend...
start http://localhost:8080/
mvn spring-boot:run
pause

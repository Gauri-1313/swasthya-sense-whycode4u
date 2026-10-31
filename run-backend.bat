@echo off
title Swasthya Sense Backend (Spring Boot)
cd /d "%~dp0backend"
echo ====================================================================
echo Starting Swasthya Sense Spring Boot Backend on http://localhost:8080
echo ====================================================================
call mvnw.cmd spring-boot:run
pause


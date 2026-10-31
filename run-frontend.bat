@echo off
title Swasthya Sense Frontend (React + Vite)
cd /d "%~dp0frontend"
echo ====================================================================
echo Starting Swasthya Sense React Frontend on http://localhost:5173
echo ====================================================================
if not exist "node_modules\" (
    echo Installing npm dependencies...
    call npm install
)
call npm run dev
pause


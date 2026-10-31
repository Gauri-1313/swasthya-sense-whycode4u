# Run Swasthya Sense Backend in PowerShell
Set-Location "$PSScriptRoot\backend"
Write-Host "====================================================================" -ForegroundColor Cyan
Write-Host " Starting Swasthya Sense Spring Boot Backend on http://localhost:8080" -ForegroundColor Green
Write-Host "====================================================================" -ForegroundColor Cyan
& ".\mvnw.ps1" spring-boot:run


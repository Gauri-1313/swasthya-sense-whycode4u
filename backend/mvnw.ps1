param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$Arguments
)

$ErrorActionPreference = 'Stop'

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$m2Dir = Join-Path $HOME ".m2"
$wrapperBase = Join-Path $m2Dir "wrapper\dists\apache-maven-3.9.6-bin"
$mavenHome = Join-Path $wrapperBase "apache-maven-3.9.6"
$mvnCmd = Join-Path $mavenHome "bin\mvn.cmd"

# Check if Maven is already extracted
if (-not (Test-Path $mvnCmd)) {
    Write-Host "[Swasthya Sense] Downloading Apache Maven 3.9.6..." -ForegroundColor Cyan
    $zipUrl = "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip"
    $zipPath = Join-Path $env:TEMP "apache-maven-3.9.6-bin.zip"
    
    New-Item -ItemType Directory -Force -Path $wrapperBase | Out-Null
    
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
    Write-Host "Downloading from $zipUrl ..." -ForegroundColor Gray
    Invoke-WebRequest -Uri $zipUrl -OutFile $zipPath
    
    Write-Host "Extracting Maven to $wrapperBase ..." -ForegroundColor Gray
    Expand-Archive -Path $zipPath -DestinationPath $wrapperBase -Force
    Remove-Item -Force $zipPath -ErrorAction SilentlyContinue
    Write-Host "[Swasthya Sense] Maven setup complete!" -ForegroundColor Green
}

# Run Maven with arguments inside backend directory
Push-Location $scriptDir
try {
    & $mvnCmd @Arguments
} finally {
    Pop-Location
}


param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$Arguments
)

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$backendMvnw = Join-Path $scriptDir "backend\mvnw.ps1"

if (Test-Path $backendMvnw) {
    & $backendMvnw @Arguments
} else {
    Write-Error "Cannot find backend\mvnw.ps1"
}


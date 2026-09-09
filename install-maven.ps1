# Script d'installation automatique de Maven pour Windows
# Exécutez ce script en tant qu'Administrateur

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Installation de Maven pour Windows" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Vérifier si Maven est déjà installé
Write-Host "Vérification de Maven..." -ForegroundColor Yellow
$mavenInstalled = Get-Command mvn -ErrorAction SilentlyContinue

if ($mavenInstalled) {
    Write-Host "Maven est déjà installé !" -ForegroundColor Green
    mvn -version
    exit 0
}

Write-Host "Maven n'est pas installé. Installation en cours..." -ForegroundColor Yellow
Write-Host ""

# Vérifier si Chocolatey est installé
Write-Host "Vérification de Chocolatey..." -ForegroundColor Yellow
$chocoInstalled = Get-Command choco -ErrorAction SilentlyContinue

if (-not $chocoInstalled) {
    Write-Host "Chocolatey n'est pas installé. Installation de Chocolatey..." -ForegroundColor Yellow
    
    # Installer Chocolatey
    Set-ExecutionPolicy Bypass -Scope Process -Force
    [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
    
    try {
        Invoke-Expression ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
        Write-Host "Chocolatey installé avec succès !" -ForegroundColor Green
    } catch {
        Write-Host "Erreur lors de l'installation de Chocolatey : $_" -ForegroundColor Red
        Write-Host ""
        Write-Host "Installation manuelle requise. Consultez INSTALLATION_WINDOWS.md" -ForegroundColor Yellow
        exit 1
    }
    
    # Rafraîchir l'environnement
    $env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")
}

Write-Host ""
Write-Host "Installation de Maven avec Chocolatey..." -ForegroundColor Yellow

try {
    choco install maven -y
    Write-Host "Maven installé avec succès !" -ForegroundColor Green
} catch {
    Write-Host "Erreur lors de l'installation de Maven : $_" -ForegroundColor Red
    Write-Host ""
    Write-Host "Installation manuelle requise. Consultez INSTALLATION_WINDOWS.md" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Installation terminée !" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "IMPORTANT : Fermez et rouvrez PowerShell pour que les changements prennent effet." -ForegroundColor Yellow
Write-Host ""
Write-Host "Ensuite, vérifiez l'installation avec : mvn -version" -ForegroundColor Cyan
Write-Host ""

@echo off
echo ========================================
echo Application de Dessin - MASI Mini Projet
echo ========================================
echo.

REM Aller dans le dossier du projet
cd /d "%~dp0"

echo Verification de Maven...
where mvn >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERREUR] Maven n'est pas installe ou pas dans le PATH
    echo.
    echo Solutions :
    echo 1. Redemarrez votre ordinateur
    echo 2. Consultez SOLUTION_MAVEN.md
    echo 3. Utilisez PowerShell Administrateur
    echo.
    pause
    exit /b 1
)

echo Maven trouve !
echo.

echo Verification de Java...
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERREUR] Java n'est pas installe
    echo.
    pause
    exit /b 1
)

echo Java trouve !
echo.

echo Compilation du projet...
echo.
call mvn clean compile

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERREUR] La compilation a echoue
    echo.
    pause
    exit /b 1
)

echo.
echo ========================================
echo Lancement de l'application...
echo ========================================
echo.

call mvn javafx:run

echo.
echo L'application s'est terminee.
pause

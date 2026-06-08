@echo off

:: ============================================================
:: Запуск сервера lab7
:: ============================================================

set DB_HOST=pg
set DB_NAME=studs
set DB_USER=s504751
set DB_PASSWORD=u0dHC5qLmSGuiQxU

set JAR=%~dp0server\build\libs\server-1.0-SNAPSHOT.jar

if not exist "%JAR%" (
    echo JAR не найден: %JAR%
    echo Собери проект: gradlew.bat :server:jar
    pause
    exit /b 1
)

echo Запуск сервера...
echo DB: %DB_USER%@%DB_HOST%/%DB_NAME%
echo JAR: %JAR%
echo ---

java -jar "%JAR%"
pause

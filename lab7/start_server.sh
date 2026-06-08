#!/bin/bash

# ============================================================
# Запуск сервера lab7
# ============================================================

export DB_HOST=pg
export DB_NAME=studs
export DB_USER=s504751
export DB_PASSWORD=u0dHC5qLmSGuiQxU

# Путь к jar — ищем рядом со скриптом
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
JAR="$SCRIPT_DIR/server/build/libs/server-1.0-SNAPSHOT.jar"

if [ ! -f "$JAR" ]; then
    echo "JAR не найден: $JAR"
    echo "Собери проект: ./gradlew :server:jar"
    exit 1
fi

echo "Запуск сервера..."
echo "DB: $DB_USER@$DB_HOST/$DB_NAME"
echo "JAR: $JAR"
echo "---"

java -jar "$JAR"

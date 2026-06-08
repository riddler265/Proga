#!/bin/bash
echo "=== Rebuilding lab7 ==="
./gradlew :server:jar :Client:jar
echo "=== Done ==="
echo "Server jar: server/build/libs/server-1.0-SNAPSHOT.jar"
echo "Client jar: Client/build/libs/Client-1.0-SNAPSHOT.jar"

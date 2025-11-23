#!/bin/bash
set -e

# Compile
echo "Compiling..."
$JAVA_HOME/bin/javac -d out src/com/example/rest/*.java
# Start server in background
echo "Starting server..."
$JAVA_HOME/bin/java -cp out com.example.rest.App &
SERVER_PID=$!

# Wait for server to start
sleep 2

# Test GET
echo "Testing GET /api/users..."
curl -v http://localhost:8000/api/users
echo ""

# Test POST
echo "Testing POST /api/users..."
curl -v -X POST -d '{"name": "Charlie"}' http://localhost:8000/api/users
echo ""

# Cleanup
echo "Stopping server..."
kill $SERVER_PID

#!/bin/bash

# Electronics E-Commerce Store - Run Script
# INFO 6205 Data Structures Project

echo "=================================="
echo "Electronics E-Commerce Store"
echo "INFO 6205 - Data Structures Project"
echo "=================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven: https://maven.apache.org/install.html"
    exit 1
fi

# Check Java version
echo "Checking Java version..."
java -version

echo ""
echo "Building and running application..."
echo ""

# Clean and run
mvn clean javafx:run

echo ""
echo "Application closed."

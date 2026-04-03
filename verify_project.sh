#!/bin/bash

# Project Verification Script
# Checks that all required files are present

echo "======================================"
echo "Project Structure Verification"
echo "======================================"
echo ""

MISSING=0

# Function to check file
check_file() {
    if [ -f "$1" ]; then
        echo "✅ $1"
    else
        echo "❌ MISSING: $1"
        MISSING=$((MISSING + 1))
    fi
}

echo "📁 Data Structures (6 files):"
check_file "src/main/java/com/ecommerce/datastructures/BinarySearchTree.java"
check_file "src/main/java/com/ecommerce/datastructures/HashTable.java"
check_file "src/main/java/com/ecommerce/datastructures/Stack.java"
check_file "src/main/java/com/ecommerce/datastructures/PriorityQueue.java"
check_file "src/main/java/com/ecommerce/datastructures/LinkedList.java"
check_file "src/main/java/com/ecommerce/datastructures/CategoryTree.java"
echo ""

echo "📁 Models (3 files):"
check_file "src/main/java/com/ecommerce/models/Product.java"
check_file "src/main/java/com/ecommerce/models/Category.java"
check_file "src/main/java/com/ecommerce/models/CartItem.java"
echo ""

echo "📁 Services (5 files):"
check_file "src/main/java/com/ecommerce/services/ProductService.java"
check_file "src/main/java/com/ecommerce/services/CartService.java"
check_file "src/main/java/com/ecommerce/services/SearchService.java"
check_file "src/main/java/com/ecommerce/services/RecommendationService.java"
check_file "src/main/java/com/ecommerce/services/BrowsingHistoryService.java"
echo ""

echo "📁 UI Components (5 files):"
check_file "src/main/java/com/ecommerce/Main.java"
check_file "src/main/java/com/ecommerce/ui/MainWindow.java"
check_file "src/main/java/com/ecommerce/ui/ProductGridView.java"
check_file "src/main/java/com/ecommerce/ui/ProductDetailView.java"
check_file "src/main/java/com/ecommerce/ui/CartView.java"
echo ""

echo "📁 Configuration & Documentation:"
check_file "pom.xml"
check_file "README.md"
check_file "REQUIREMENTS_VERIFICATION.md"
check_file ".gitignore"
check_file "run.sh"
echo ""

echo "======================================"
if [ $MISSING -eq 0 ]; then
    echo "✅ All files present! Project complete."
    echo ""
    echo "📊 Project Statistics:"
    echo "   - Data Structures: 6 custom implementations"
    echo "   - Models: 3 domain classes"
    echo "   - Services: 5 business services"
    echo "   - UI Components: 5 JavaFX classes"
    echo "   - Total Files: 24"
    echo ""
    echo "🚀 Ready to run!"
    echo "   Execute: ./run.sh"
else
    echo "❌ $MISSING file(s) missing!"
    exit 1
fi
echo "======================================"

# Quick Start Guide

## ⚡ Get Started in 3 Steps

### Step 1: Verify Setup
```bash
./verify_project.sh
```
This checks that all 24 files are present.

### Step 2: Run the Application
```bash
./run.sh
```
Or manually:
```bash
mvn clean javafx:run
```

### Step 3: Test the Features

#### Test Data Structures:

1. **Binary Search Tree** (Product Catalog)
   - Launch app → Products load into BST
   - Browse products → BST inorder traversal

2. **Hash Table** (Fast Lookup)
   - Click any product → O(1) hash lookup
   - Add to cart → Hash table storage

3. **Stack** (Navigation History)
   - Click multiple products
   - Click "← Back" button (stack pop)
   - Click "Forward →" button (stack pop)

4. **Priority Queue** (Recommendations)
   - Click any product
   - Scroll to "Recommended Products"
   - See priority queue ranking

5. **Linked List** (Shopping Cart)
   - Add items to cart → LinkedList add
   - Remove items → LinkedList remove
   - Update quantity → LinkedList operations

6. **Recursion** (Category Tree)
   - Select a category from dropdown
   - Products from subcategories appear (recursive traversal)

#### Test Sorting Algorithms:

1. Click "Sort by" dropdown
2. Try different options:
   - Price (Low to High) → QuickSort
   - Rating (High to Low) → QuickSort
   - Name (A-Z) → MergeSort

---

## 📋 What to Submit

1. **Source Code**: Entire `FinalProject/` directory
2. **Documentation**:
   - README.md
   - REQUIREMENTS_VERIFICATION.md
   - PROJECT_SUMMARY.md
3. **Build File**: pom.xml

---

## 🎯 Demonstration Checklist

When presenting/demoing:

- [ ] Show custom data structure implementations (not Java Collections)
- [ ] Demonstrate stack navigation (back/forward buttons)
- [ ] Show recursive category traversal
- [ ] Display priority queue recommendations
- [ ] Show different sorting algorithms in action
- [ ] Explain Big-O complexities in code comments
- [ ] Show hash table O(1) lookups vs BST O(log n)

---

## 📚 Documentation Files

- **README.md** - Complete project documentation
- **REQUIREMENTS_VERIFICATION.md** - Detailed requirements checklist
- **PROJECT_SUMMARY.md** - Project overview and statistics
- **QUICK_START.md** - This file

---

## ⚠️ Troubleshooting

### If compilation fails:
```bash
mvn clean compile
```
Check for Java 11+ and Maven installation.

### If JavaFX is missing:
Maven will automatically download JavaFX dependencies.
No manual installation needed.

### To see verbose output:
```bash
mvn clean compile -X
```

---

## 🎓 Key Points for Grading

1. **All data structures are custom** - No Java Collections used for core functionality
2. **All requirements exceeded** - 3 foundational + 5 advanced topics
3. **Full working application** - Functional GUI with real features
4. **Comprehensive documentation** - Big-O analysis throughout
5. **Professional code quality** - Clean organization, proper naming

---

## 💡 Tips

- The application includes **informative console output** showing data structure operations
- Check the **status bar at bottom** for operation feedback
- All **Big-O complexities are documented** in code comments
- **README.md** has detailed algorithm complexity tables

---

## ✅ Success Indicators

You know it's working when:

1. ✅ Application launches without errors
2. ✅ 20 products display in grid
3. ✅ Clicking products shows details
4. ✅ Back/Forward buttons enable after clicking products
5. ✅ Cart updates when adding items
6. ✅ Sorting changes product order
7. ✅ Recommended products appear in detail view

---

**Ready to run!** Execute `./run.sh` to start.

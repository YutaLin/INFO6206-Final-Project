# E-Commerce Electronics Store - Implementation Plan

## Project Overview
Building an Electronics E-Commerce application using JavaFX that demonstrates all required data structures and algorithms from INFO 6205.

**Approach**: Minimal Viable Product - Core features first, all requirements met, clean architecture

## Requirements Coverage

### Foundational Topics (Need 1, Using 2)
- **Stacks**: Browsing history (back/forward navigation through products)
- **Recursion**: Category tree traversal (Electronics → Laptops → Gaming Laptops)
- **Algorithm Efficiency**: Performance analysis of search and sort operations

### Advanced Topics (Need 3, Using 5)
- **Binary Search Tree**: Product catalog indexed by product ID
- **Hash Table**: Fast product lookup by ID, shopping cart storage
- **Priority Queue**: Featured/recommended products ranking
- **Sorting**: Multiple sort options (price, rating, name)
- **Lists**: Shopping cart implementation, product list management

## Architecture Design

### Package Structure
```
src/
├── main/
│   └── java/
│       └── com/ecommerce/
│           ├── Main.java
│           ├── models/
│           │   ├── Product.java
│           │   ├── Category.java
│           │   └── CartItem.java
│           ├── datastructures/
│           │   ├── BinarySearchTree.java
│           │   ├── HashTable.java
│           │   ├── Stack.java
│           │   ├── PriorityQueue.java
│           │   ├── LinkedList.java
│           │   └── CategoryTree.java (uses recursion)
│           ├── services/
│           │   ├── ProductService.java
│           │   ├── CartService.java
│           │   ├── SearchService.java
│           │   └── RecommendationService.java
│           └── ui/
│               ├── MainWindow.java
│               ├── ProductGridView.java
│               ├── ProductDetailView.java
│               ├── CartView.java
│               └── BrowsingHistoryView.java
└── resources/
    └── sample-products.txt
```

## Implementation Phases

### Phase 1: Data Structure Implementation
Create custom ADT implementations (as required by project):

1. **BinarySearchTree<T>**
   - Interface: insert, search, delete, inorder traversal
   - Used for: Product catalog indexed by ID

2. **HashTable<K, V>**
   - Interface: put, get, remove, containsKey
   - Implementation: Array-based with chaining for collisions
   - Used for: Fast product lookup, cart storage

3. **Stack<T>**
   - Interface: push, pop, peek, isEmpty
   - Used for: Browsing history

4. **PriorityQueue<T>**
   - Interface: enqueue, dequeue, peek
   - Implementation: Heap-based
   - Used for: Featured products ranking

5. **LinkedList<T>**
   - Interface: add, remove, get, size
   - Used for: Shopping cart items

6. **CategoryTree**
   - Recursive methods: traverseCategories, findCategory, getAllProducts
   - Tree structure for product categories

### Phase 2: Domain Models
Create simple model classes:

1. **Product**
   - Fields: id, name, category, price, rating, description, specs
   - Implements Comparable for sorting

2. **Category**
   - Fields: name, parent, subcategories (List)
   - Tree structure: Electronics → Laptops, Phones, Accessories

3. **CartItem**
   - Fields: product, quantity

### Phase 3: Business Services
Implement service layer using custom data structures:

1. **ProductService**
   - Uses BST for product storage
   - Uses HashTable for ID-based lookup
   - Sample data: 20-30 electronics products
   - Methods: getAllProducts, getProductById, getProductsByCategory

2. **SearchService**
   - Linear search by name/specs
   - Binary search on sorted collections
   - Category filtering using recursive tree traversal
   - Sorting: by price, rating, name (implement multiple sort algorithms)

3. **CartService**
   - Uses LinkedList for cart items
   - Uses HashTable to track product quantities
   - Methods: addItem, removeItem, updateQuantity, getTotal

4. **RecommendationService**
   - Uses PriorityQueue to rank featured products
   - Simple algorithm: products in same category, sorted by rating

5. **BrowsingHistoryService**
   - Uses Stack for history
   - Methods: push (when viewing product), pop (back button), peek (current)

### Phase 4: JavaFX GUI
Build minimal but functional interface:

1. **Main Window Layout**
   - Top: Search bar, category dropdown, sort options
   - Left: Category tree navigation (recursive display)
   - Center: Product grid (thumbnails, name, price, rating)
   - Right: Shopping cart panel
   - Bottom: Back/Forward navigation buttons (stack-based)

2. **Product Grid View**
   - Display products in grid format
   - Click to view details
   - Quick "Add to Cart" button

3. **Product Detail View**
   - Full product information
   - Specifications
   - Add to cart with quantity
   - Recommended products section

4. **Cart View**
   - List of cart items
   - Quantity adjustment
   - Total price
   - Clear cart button

5. **Browsing History**
   - Back/Forward buttons using Stack
   - Shows last viewed product

### Phase 5: Sample Data & Testing
1. Create sample electronics data (laptops, phones, headphones, etc.)
2. Test all data structure operations
3. Test GUI interactions
4. Performance analysis of search/sort algorithms

## Critical Files to Create

1. **Data Structures** (8 files)
   - `BinarySearchTree.java`
   - `HashTable.java`
   - `Stack.java`
   - `PriorityQueue.java`
   - `LinkedList.java`
   - `CategoryTree.java`
   - Interface files as needed

2. **Models** (3 files)
   - `Product.java`
   - `Category.java`
   - `CartItem.java`

3. **Services** (5 files)
   - `ProductService.java`
   - `CartService.java`
   - `SearchService.java`
   - `RecommendationService.java`
   - `BrowsingHistoryService.java`

4. **UI** (5 files)
   - `Main.java`
   - `MainWindow.java`
   - `ProductGridView.java`
   - `ProductDetailView.java`
   - `CartView.java`

## Efficiency Analysis Component
Document algorithm performance in comments and code:
- Search: Linear O(n) vs Binary Search O(log n)
- BST operations: O(log n) average case
- Hash table: O(1) average lookup
- Sorting: Compare different algorithms (Quick Sort, Merge Sort)
- Include timing measurements in the application

## MVP Feature Set
1. ✓ Browse products by category (recursive tree traversal)
2. ✓ Search products by name
3. ✓ Sort products (price, rating, name)
4. ✓ View product details
5. ✓ Add/remove items from cart
6. ✓ Back/forward navigation (stack-based history)
7. ✓ See recommended products (priority queue)
8. ✓ All custom data structures implemented and used

## Verification & Testing
After implementation:
1. Run the application
2. Test category navigation (verify recursion)
3. Test browsing history (verify stack operations)
4. Add/remove cart items (verify list operations)
5. Search and sort products (verify BST, sorting)
6. Check recommended products (verify priority queue)
7. Verify all data structures are custom implementations (not Java Collections)

## Future Enhancements (Post-MVP)
- User accounts and login
- Order history
- Advanced recommendation algorithm using graphs
- Persistent storage (file I/O)
- Undo/redo for cart operations
- Product comparison feature

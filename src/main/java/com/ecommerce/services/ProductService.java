package com.ecommerce.services;

import com.ecommerce.datastructures.BinarySearchTree;
import com.ecommerce.datastructures.CategoryTree;
import com.ecommerce.models.Product;
import com.ecommerce.models.Category;

import java.util.*;

/**
 * ProductService manages products using custom data structures
 * Uses: BST for ordered storage, CategoryTree for organization
 */
public class ProductService {
    private final BinarySearchTree<Product> productBST;
    private final CategoryTree categoryTree;
    private final Category rootCategory;

    public ProductService() {
        this.productBST = new BinarySearchTree<>();
        this.rootCategory = initializeCategories();
        this.categoryTree = new CategoryTree(rootCategory);
        initializeSampleProducts();
    }

    /**
     * Initialize category tree structure
     */
    private Category initializeCategories() {
        Category electronics = new Category("Electronics");

        // Laptops category
        Category laptops = new Category("Laptops", electronics);
        Category gamingLaptops = new Category("Gaming Laptops", laptops);
        Category businessLaptops = new Category("Business Laptops", laptops);
        Category ultrabooks = new Category("Ultrabooks", laptops);
        laptops.addSubcategory(gamingLaptops);
        laptops.addSubcategory(businessLaptops);
        laptops.addSubcategory(ultrabooks);

        // Phones category
        Category phones = new Category("Phones", electronics);
        Category smartphones = new Category("Smartphones", phones);
        Category basicPhones = new Category("Basic Phones", phones);
        phones.addSubcategory(smartphones);
        phones.addSubcategory(basicPhones);

        // Audio category
        Category audio = new Category("Audio", electronics);
        Category headphones = new Category("Headphones", audio);
        Category speakers = new Category("Speakers", audio);
        Category earbuds = new Category("Earbuds", audio);
        audio.addSubcategory(headphones);
        audio.addSubcategory(speakers);
        audio.addSubcategory(earbuds);

        // Accessories category
        Category accessories = new Category("Accessories", electronics);
        Category chargers = new Category("Chargers", accessories);
        Category cases = new Category("Cases", accessories);
        Category cables = new Category("Cables", accessories);
        accessories.addSubcategory(chargers);
        accessories.addSubcategory(cases);
        accessories.addSubcategory(cables);

        electronics.addSubcategory(laptops);
        electronics.addSubcategory(phones);
        electronics.addSubcategory(audio);
        electronics.addSubcategory(accessories);

        return electronics;
    }

    /**
     * Initialize sample products
     */
    private void initializeSampleProducts() {
        List<Product> products = Arrays.asList(
            // Gaming Laptops
            new Product.Builder()
                .id(1001)
                .name("ASUS ROG Strix G15")
                .category("Gaming Laptops")
                .price(1499.99)
                .rating(4.7)
                .description("Powerful gaming laptop with RTX 3070 graphics")
                .addSpec("Processor", "AMD Ryzen 9 5900HX")
                .addSpec("RAM", "16GB DDR4")
                .addSpec("Storage", "1TB SSD")
                .addSpec("Display", "15.6\" FHD 144Hz")
                .addSpec("GPU", "NVIDIA RTX 3070")
                .build(),

            new Product.Builder()
                .id(1002)
                .name("MSI Raider GE76")
                .category("Gaming Laptops")
                .price(2299.99)
                .rating(4.8)
                .description("High-end gaming laptop with RGB keyboard")
                .addSpec("Processor", "Intel Core i9-11980HK")
                .addSpec("RAM", "32GB DDR4")
                .addSpec("Storage", "2TB SSD")
                .addSpec("Display", "17.3\" FHD 360Hz")
                .addSpec("GPU", "NVIDIA RTX 3080")
                .build(),

            new Product.Builder()
                .id(1003)
                .name("Alienware m15 R6")
                .category("Gaming Laptops")
                .price(1899.99)
                .rating(4.6)
                .description("Premium gaming experience with advanced cooling")
                .addSpec("Processor", "Intel Core i7-11800H")
                .addSpec("RAM", "16GB DDR4")
                .addSpec("Storage", "512GB SSD")
                .addSpec("Display", "15.6\" QHD 240Hz")
                .addSpec("GPU", "NVIDIA RTX 3070")
                .build(),

            // Business Laptops
            new Product.Builder()
                .id(1004)
                .name("Dell Latitude 7420")
                .category("Business Laptops")
                .price(1299.99)
                .rating(4.5)
                .description("Professional laptop with enterprise security")
                .addSpec("Processor", "Intel Core i7-1185G7")
                .addSpec("RAM", "16GB DDR4")
                .addSpec("Storage", "512GB SSD")
                .addSpec("Display", "14\" FHD")
                .addSpec("Battery", "Up to 18 hours")
                .build(),

            new Product.Builder()
                .id(1005)
                .name("Lenovo ThinkPad X1 Carbon")
                .category("Business Laptops")
                .price(1599.99)
                .rating(4.8)
                .description("Ultra-light business laptop with legendary keyboard")
                .addSpec("Processor", "Intel Core i7-1165G7")
                .addSpec("RAM", "16GB LPDDR4")
                .addSpec("Storage", "1TB SSD")
                .addSpec("Display", "14\" FHD")
                .addSpec("Weight", "2.49 lbs")
                .build(),

            // Ultrabooks
            new Product.Builder()
                .id(1006)
                .name("MacBook Air M2")
                .category("Ultrabooks")
                .price(1199.99)
                .rating(4.9)
                .description("Apple's powerful and efficient ultraportable laptop")
                .addSpec("Processor", "Apple M2")
                .addSpec("RAM", "8GB Unified Memory")
                .addSpec("Storage", "256GB SSD")
                .addSpec("Display", "13.6\" Liquid Retina")
                .addSpec("Battery", "Up to 18 hours")
                .build(),

            new Product.Builder()
                .id(1007)
                .name("Dell XPS 13")
                .category("Ultrabooks")
                .price(999.99)
                .rating(4.7)
                .description("Premium ultrabook with InfinityEdge display")
                .addSpec("Processor", "Intel Core i5-1135G7")
                .addSpec("RAM", "8GB LPDDR4")
                .addSpec("Storage", "512GB SSD")
                .addSpec("Display", "13.4\" FHD+")
                .addSpec("Weight", "2.64 lbs")
                .build(),

            // Smartphones
            new Product.Builder()
                .id(2001)
                .name("iPhone 14 Pro")
                .category("Smartphones")
                .price(999.99)
                .rating(4.8)
                .description("Apple's flagship smartphone with Dynamic Island")
                .addSpec("Display", "6.1\" Super Retina XDR")
                .addSpec("Processor", "A16 Bionic")
                .addSpec("Camera", "48MP Main + 12MP Ultra Wide")
                .addSpec("Storage", "128GB")
                .addSpec("5G", "Yes")
                .build(),

            new Product.Builder()
                .id(2002)
                .name("Samsung Galaxy S23 Ultra")
                .category("Smartphones")
                .price(1199.99)
                .rating(4.7)
                .description("Samsung's premium phone with S Pen")
                .addSpec("Display", "6.8\" Dynamic AMOLED 2X")
                .addSpec("Processor", "Snapdragon 8 Gen 2")
                .addSpec("Camera", "200MP Main + 10MP Telephoto")
                .addSpec("Storage", "256GB")
                .addSpec("5G", "Yes")
                .build(),

            new Product.Builder()
                .id(2003)
                .name("Google Pixel 7 Pro")
                .category("Smartphones")
                .price(899.99)
                .rating(4.6)
                .description("Google's flagship with advanced AI camera")
                .addSpec("Display", "6.7\" LTPO AMOLED")
                .addSpec("Processor", "Google Tensor G2")
                .addSpec("Camera", "50MP Main + 12MP Ultra Wide")
                .addSpec("Storage", "128GB")
                .addSpec("5G", "Yes")
                .build(),

            // Headphones
            new Product.Builder()
                .id(3001)
                .name("Sony WH-1000XM5")
                .category("Headphones")
                .price(399.99)
                .rating(4.9)
                .description("Industry-leading noise canceling headphones")
                .addSpec("Type", "Over-ear")
                .addSpec("Noise Canceling", "Yes")
                .addSpec("Battery Life", "30 hours")
                .addSpec("Connectivity", "Bluetooth 5.2")
                .addSpec("Weight", "250g")
                .build(),

            new Product.Builder()
                .id(3002)
                .name("Bose QuietComfort 45")
                .category("Headphones")
                .price(329.99)
                .rating(4.7)
                .description("Premium comfort with world-class noise cancellation")
                .addSpec("Type", "Over-ear")
                .addSpec("Noise Canceling", "Yes")
                .addSpec("Battery Life", "24 hours")
                .addSpec("Connectivity", "Bluetooth 5.1")
                .addSpec("Weight", "240g")
                .build(),

            // Earbuds
            new Product.Builder()
                .id(3003)
                .name("AirPods Pro 2")
                .category("Earbuds")
                .price(249.99)
                .rating(4.8)
                .description("Apple's premium earbuds with active noise cancellation")
                .addSpec("Type", "In-ear")
                .addSpec("Noise Canceling", "Yes")
                .addSpec("Battery Life", "6 hours (30 with case)")
                .addSpec("Connectivity", "Bluetooth 5.3")
                .addSpec("Water Resistance", "IPX4")
                .build(),

            new Product.Builder()
                .id(3004)
                .name("Samsung Galaxy Buds2 Pro")
                .category("Earbuds")
                .price(229.99)
                .rating(4.6)
                .description("Premium wireless earbuds with 360 audio")
                .addSpec("Type", "In-ear")
                .addSpec("Noise Canceling", "Yes")
                .addSpec("Battery Life", "5 hours (18 with case)")
                .addSpec("Connectivity", "Bluetooth 5.3")
                .addSpec("Water Resistance", "IPX7")
                .build(),

            // Speakers
            new Product.Builder()
                .id(3005)
                .name("Sonos One")
                .category("Speakers")
                .price(219.99)
                .rating(4.7)
                .description("Smart speaker with rich sound and voice control")
                .addSpec("Type", "Smart Speaker")
                .addSpec("Voice Assistant", "Alexa, Google Assistant")
                .addSpec("Connectivity", "WiFi, AirPlay 2")
                .addSpec("Power", "AC Powered")
                .addSpec("Dimensions", "4.7 x 4.7 x 6.4 inches")
                .build(),

            new Product.Builder()
                .id(3006)
                .name("JBL Flip 6")
                .category("Speakers")
                .price(129.99)
                .rating(4.8)
                .description("Portable Bluetooth speaker with powerful bass")
                .addSpec("Type", "Portable Speaker")
                .addSpec("Battery Life", "12 hours")
                .addSpec("Connectivity", "Bluetooth 5.1")
                .addSpec("Water Resistance", "IP67")
                .addSpec("Weight", "550g")
                .build(),

            // Accessories
            new Product.Builder()
                .id(4001)
                .name("Anker PowerCore 20000")
                .category("Chargers")
                .price(49.99)
                .rating(4.6)
                .description("High-capacity portable charger")
                .addSpec("Capacity", "20000mAh")
                .addSpec("Output Ports", "2 USB-A")
                .addSpec("Fast Charging", "Yes")
                .addSpec("Input", "Micro-USB, USB-C")
                .addSpec("Weight", "356g")
                .build(),

            new Product.Builder()
                .id(4002)
                .name("Apple MagSafe Charger")
                .category("Chargers")
                .price(39.99)
                .rating(4.5)
                .description("Magnetic wireless charging for iPhone")
                .addSpec("Type", "Wireless Charger")
                .addSpec("Power", "15W")
                .addSpec("Compatibility", "iPhone 12 and later")
                .addSpec("Cable Length", "1m")
                .build(),

            new Product.Builder()
                .id(4003)
                .name("OtterBox Defender Series")
                .category("Cases")
                .price(59.99)
                .rating(4.7)
                .description("Heavy-duty phone case with multi-layer protection")
                .addSpec("Type", "Phone Case")
                .addSpec("Protection", "Drop, Dust, Scratch")
                .addSpec("Material", "Polycarbonate + Rubber")
                .addSpec("Screen Protector", "Included")
                .build(),

            new Product.Builder()
                .id(4004)
                .name("Anker USB-C to Lightning Cable")
                .category("Cables")
                .price(19.99)
                .rating(4.8)
                .description("Fast charging cable for iPhone")
                .addSpec("Type", "USB-C to Lightning")
                .addSpec("Length", "6ft / 1.8m")
                .addSpec("Fast Charging", "Yes")
                .addSpec("MFi Certified", "Yes")
                .addSpec("Durability", "10000+ bend lifespan")
                .build()
        );

        // Add all products to data structures and categories
        for (Product product : products) {
            addProduct(product);
        }
    }

    /**
     * Add a product to all data structures
     * Time Complexity: O(log n) for BST
     */
    public void addProduct(Product product) {
        productBST.insert(product);

        // Add to category
        Category category = categoryTree.findCategory(product.getCategory());
        if (category != null) {
            category.addProduct(product);
        }
    }

    /**
     * Get all products in sorted order (by ID) using BST
     * Time Complexity: O(n)
     */
    public List<Product> getAllProducts() {
        return productBST.toList();
    }

    /**
     * Get products by category using recursive tree traversal
     * Demonstrates: Recursion
     */
    public List<Product> getProductsByCategory(String categoryName) {
        Category category = categoryTree.findCategory(categoryName);
        if (category == null) {
            return new ArrayList<>();
        }
        return categoryTree.getAllProducts(category);
    }

    /**
     * Get root category
     */
    public Category getRootCategory() {
        return rootCategory;
    }
}

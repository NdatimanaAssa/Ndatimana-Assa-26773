package auca.ac.rw.restfullApiAssignment.controller.ecommerce;

import auca.ac.rw.restfullApiAssignment.modal.ecommerce.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing e-commerce products
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // In-memory list to store products
    private List<Product> products = new ArrayList<>();
    private Long nextId = 11L;

    // Initialize with sample data
    public ProductController() {
        products.add(new Product(1L, "iPhone 15 Pro", "Latest Apple smartphone with A17 Pro chip", 999.99, "Electronics", 50, "Apple"));
        products.add(new Product(2L, "Samsung Galaxy S24", "Flagship Android smartphone", 899.99, "Electronics", 30, "Samsung"));
        products.add(new Product(3L, "MacBook Pro", "16-inch laptop with M3 chip", 2499.99, "Electronics", 15, "Apple"));
        products.add(new Product(4L, "Dell XPS 15", "High-performance Windows laptop", 1799.99, "Electronics", 20, "Dell"));
        products.add(new Product(5L, "Sony WH-1000XM5", "Noise-canceling wireless headphones", 399.99, "Audio", 100, "Sony"));
        products.add(new Product(6L, "AirPods Pro", "Wireless earbuds with active noise cancellation", 249.99, "Audio", 75, "Apple"));
        products.add(new Product(7L, "Nike Air Max", "Comfortable running shoes", 129.99, "Footwear", 200, "Nike"));
        products.add(new Product(8L, "Adidas Ultraboost", "Premium running shoes with boost technology", 189.99, "Footwear", 150, "Adidas"));
        products.add(new Product(9L, "Levi's 501 Jeans", "Classic straight fit jeans", 69.99, "Clothing", 0, "Levi's"));
        products.add(new Product(10L, "Canon EOS R6", "Full-frame mirrorless camera", 2499.99, "Electronics", 10, "Canon"));
    }

    /**
     * GET /api/products - Get all products with optional pagination
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        
        if (page != null && limit != null && page >= 0 && limit > 0) {
            int start = page * limit;
            int end = Math.min(start + limit, products.size());
            
            if (start >= products.size()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
            
            List<Product> paginatedProducts = products.subList(start, end);
            return new ResponseEntity<>(paginatedProducts, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /api/products/{productId} - Get product details
     */
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();
        
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/products/category/{category} - Get products by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                result.add(product);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET /api/products/brand/{brand} - Get products by brand
     */
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable String brand) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getBrand().equalsIgnoreCase(brand)) {
                result.add(product);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET /api/products/search?keyword={keyword} - Search products by keyword
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(lowerKeyword) ||
                product.getDescription().toLowerCase().contains(lowerKeyword)) {
                result.add(product);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET /api/products/price-range?min={min}&max={max} - Get products within price range
     */
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<Product> result = new ArrayList<>();
        
        for (Product product : products) {
            if (product.getPrice() >= min && product.getPrice() <= max) {
                result.add(product);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET /api/products/in-stock - Get products with stock > 0
     */
    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> getInStockProducts() {
        List<Product> result = new ArrayList<>();
        
        for (Product product : products) {
            if (product.getStockQuantity() > 0) {
                result.add(product);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * POST /api/products - Add new product
     */
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        product.setProductId(nextId++);
        products.add(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * PUT /api/products/{productId} - Update product details
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                updatedProduct.setProductId(productId);
                products.set(i, updatedProduct);
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * PATCH /api/products/{productId}/stock?quantity={quantity} - Update stock quantity
     */
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long productId, @RequestParam int quantity) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                product.setStockQuantity(quantity);
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * DELETE /api/products/{productId} - Delete product
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean removed = products.removeIf(product -> product.getProductId().equals(productId));
        
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
package auca.ac.rw.restfullApiAssignment.controller.restaurant;

import auca.ac.rw.restfullApiAssignment.modal.restaurant.MenuItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing restaurant menu
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    // In-memory list to store menu items
    private List<MenuItem> menuItems = new ArrayList<>();
    private Long nextId = 9L;

    // Initialize with sample data
    public MenuController() {
        menuItems.add(new MenuItem(1L, "Caesar Salad", "Fresh romaine lettuce with Caesar dressing", 8.99, "Appetizer", true));
        menuItems.add(new MenuItem(2L, "Garlic Bread", "Toasted bread with garlic butter", 5.99, "Appetizer", true));
        menuItems.add(new MenuItem(3L, "Grilled Salmon", "Atlantic salmon with lemon butter sauce", 22.99, "Main Course", true));
        menuItems.add(new MenuItem(4L, "Beef Steak", "Premium ribeye steak with vegetables", 28.99, "Main Course", true));
        menuItems.add(new MenuItem(5L, "Margherita Pizza", "Classic pizza with tomato, mozzarella, and basil", 14.99, "Main Course", false));
        menuItems.add(new MenuItem(6L, "Chocolate Cake", "Rich chocolate cake with vanilla ice cream", 7.99, "Dessert", true));
        menuItems.add(new MenuItem(7L, "Tiramisu", "Italian coffee-flavored dessert", 8.99, "Dessert", true));
        menuItems.add(new MenuItem(8L, "Lemonade", "Fresh squeezed lemonade", 3.99, "Beverage", true));
    }

    /**
     * GET /api/menu - Get all menu items
     */
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    /**
     * GET /api/menu/{id} - Get specific menu item
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        Optional<MenuItem> menuItem = menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
        
        if (menuItem.isPresent()) {
            return new ResponseEntity<>(menuItem.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/menu/category/{category} - Get items by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable String category) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : menuItems) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                result.add(item);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET /api/menu/available - Get only available items
     */
    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItems(@RequestParam(defaultValue = "true") boolean available) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : menuItems) {
            if (item.isAvailable() == available) {
                result.add(item);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET /api/menu/search?name={name} - Search menu items by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenuItemsByName(@RequestParam String name) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : menuItems) {
            if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(item);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * POST /api/menu - Add new menu item
     */
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        menuItem.setId(nextId++);
        menuItems.add(menuItem);
        return new ResponseEntity<>(menuItem, HttpStatus.CREATED);
    }

    /**
     * PUT /api/menu/{id}/availability - Toggle item availability
     */
    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                item.setAvailable(!item.isAvailable());
                return new ResponseEntity<>(item, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * DELETE /api/menu/{id} - Remove menu item
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        boolean removed = menuItems.removeIf(item -> item.getId().equals(id));
        
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
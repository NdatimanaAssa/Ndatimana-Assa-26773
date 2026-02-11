package auca.ac.rw.restfullApiAssignment.controller.userprofile;

import auca.ac.rw.restfullApiAssignment.modal.userprofile.ApiResponse;
import auca.ac.rw.restfullApiAssignment.modal.userprofile.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing user profiles with ApiResponse wrapper
 */
@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    // In-memory list to store user profiles
    private List<UserProfile> userProfiles = new ArrayList<>();
    private Long nextId = 6L;

    // Initialize with sample data
    public UserProfileController() {
        userProfiles.add(new UserProfile(1L, "john_doe", "john@example.com", "John Doe", 28, "USA", "Software developer passionate about Java and Spring Boot", true));
        userProfiles.add(new UserProfile(2L, "jane_smith", "jane@example.com", "Jane Smith", 25, "Canada", "Tech enthusiast and full-stack developer", true));
        userProfiles.add(new UserProfile(3L, "mike_wilson", "mike@example.com", "Mike Wilson", 32, "UK", "DevOps engineer with cloud expertise", true));
        userProfiles.add(new UserProfile(4L, "sarah_jones", "sarah@example.com", "Sarah Jones", 29, "Australia", "Data scientist and machine learning expert", false));
        userProfiles.add(new UserProfile(5L, "alex_brown", "alex@example.com", "Alex Brown", 27, "Germany", "Mobile app developer specializing in Android", true));
    }

    /**
     * GET /api/users - Get all user profiles
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserProfile>>> getAllUsers() {
        ApiResponse<List<UserProfile>> response = new ApiResponse<>(
            true,
            "Successfully retrieved all user profiles",
            userProfiles
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /api/users/{userId} - Get user profile by ID
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> getUserById(@PathVariable Long userId) {
        Optional<UserProfile> user = userProfiles.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();
        
        if (user.isPresent()) {
            ApiResponse<UserProfile> response = new ApiResponse<>(
                true,
                "User profile found",
                user.get()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<UserProfile> response = new ApiResponse<>(
                false,
                "User profile not found with ID: " + userId,
                null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/users/search/username/{username} - Search user by username
     */
    @GetMapping("/search/username/{username}")
    public ResponseEntity<ApiResponse<UserProfile>> getUserByUsername(@PathVariable String username) {
        Optional<UserProfile> user = userProfiles.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
        
        if (user.isPresent()) {
            ApiResponse<UserProfile> response = new ApiResponse<>(
                true,
                "User profile found",
                user.get()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<UserProfile> response = new ApiResponse<>(
                false,
                "User profile not found with username: " + username,
                null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/users/search/country/{country} - Search users by country
     */
    @GetMapping("/search/country/{country}")
    public ResponseEntity<ApiResponse<List<UserProfile>>> getUsersByCountry(@PathVariable String country) {
        List<UserProfile> result = new ArrayList<>();
        for (UserProfile user : userProfiles) {
            if (user.getCountry().equalsIgnoreCase(country)) {
                result.add(user);
            }
        }
        
        ApiResponse<List<UserProfile>> response = new ApiResponse<>(
            true,
            "Found " + result.size() + " user(s) from " + country,
            result
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /api/users/search/age-range?min={min}&max={max} - Search users by age range
     */
    @GetMapping("/search/age-range")
    public ResponseEntity<ApiResponse<List<UserProfile>>> getUsersByAgeRange(
            @RequestParam int min,
            @RequestParam int max) {
        List<UserProfile> result = new ArrayList<>();
        
        for (UserProfile user : userProfiles) {
            if (user.getAge() >= min && user.getAge() <= max) {
                result.add(user);
            }
        }
        
        ApiResponse<List<UserProfile>> response = new ApiResponse<>(
            true,
            "Found " + result.size() + " user(s) in age range " + min + "-" + max,
            result
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /api/users/active - Get all active users
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<UserProfile>>> getActiveUsers() {
        List<UserProfile> result = new ArrayList<>();
        for (UserProfile user : userProfiles) {
            if (user.isActive()) {
                result.add(user);
            }
        }
        
        ApiResponse<List<UserProfile>> response = new ApiResponse<>(
            true,
            "Found " + result.size() + " active user(s)",
            result
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /api/users - Create a new user profile
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserProfile>> createUserProfile(@RequestBody UserProfile userProfile) {
        userProfile.setUserId(nextId++);
        userProfiles.add(userProfile);
        
        ApiResponse<UserProfile> response = new ApiResponse<>(
            true,
            "User profile created successfully",
            userProfile
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * PUT /api/users/{userId} - Update user profile
     */
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UserProfile updatedProfile) {
        
        for (int i = 0; i < userProfiles.size(); i++) {
            if (userProfiles.get(i).getUserId().equals(userId)) {
                updatedProfile.setUserId(userId);
                userProfiles.set(i, updatedProfile);
                
                ApiResponse<UserProfile> response = new ApiResponse<>(
                    true,
                    "User profile updated successfully",
                    updatedProfile
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        
        ApiResponse<UserProfile> response = new ApiResponse<>(
            false,
            "User profile not found with ID: " + userId,
            null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * PATCH /api/users/{userId}/activate - Activate user profile
     */
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<ApiResponse<UserProfile>> activateUser(@PathVariable Long userId) {
        for (UserProfile user : userProfiles) {
            if (user.getUserId().equals(userId)) {
                user.setActive(true);
                
                ApiResponse<UserProfile> response = new ApiResponse<>(
                    true,
                    "User profile activated successfully",
                    user
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        
        ApiResponse<UserProfile> response = new ApiResponse<>(
            false,
            "User profile not found with ID: " + userId,
            null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * PATCH /api/users/{userId}/deactivate - Deactivate user profile
     */
    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<ApiResponse<UserProfile>> deactivateUser(@PathVariable Long userId) {
        for (UserProfile user : userProfiles) {
            if (user.getUserId().equals(userId)) {
                user.setActive(false);
                
                ApiResponse<UserProfile> response = new ApiResponse<>(
                    true,
                    "User profile deactivated successfully",
                    user
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        
        ApiResponse<UserProfile> response = new ApiResponse<>(
            false,
            "User profile not found with ID: " + userId,
            null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * DELETE /api/users/{userId} - Delete user profile
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserProfile(@PathVariable Long userId) {
        boolean removed = userProfiles.removeIf(user -> user.getUserId().equals(userId));
        
        if (removed) {
            ApiResponse<Void> response = new ApiResponse<>(
                true,
                "User profile deleted successfully",
                null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Void> response = new ApiResponse<>(
                false,
                "User profile not found with ID: " + userId,
                null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
package com.example.subsavvy.Controller;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.example.subsavvy.Data.Subscription;
import com.example.subsavvy.Data.User;
import com.example.subsavvy.Service.UserService;
import com.example.subsavvy.dto.SubscriptionDto;
import com.example.subsavvy.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    public UserController(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    // Endpoint pour récupérer tous les abonnements d'un utilisateur
    @GetMapping("/{userId}/subscriptions")
    public List<Subscription> getAllSubscriptions(@PathVariable UUID userId) {
        return userService.getSubscriptionsForUser(userId);  // Appel à la méthode du service UserService pour récupérer les abonnements
    }

    // Endpoint pour ajouter un abonnement pour un utilisateur
    @PostMapping("/{userId}/subscriptions")
    public ResponseEntity<Subscription> addSubscription(
            @PathVariable UUID userId,
            @RequestBody SubscriptionDto subscriptionDto
    ) {
        Subscription addedSubscription = userService.addSubscription(userId, subscriptionDto);  // Appel à la méthode du service UserService pour ajouter un abonnement
        return ResponseEntity.ok(addedSubscription);
    }

    // Endpoint pour mettre à jour un abonnement d'un utilisateur
    @PutMapping("/{userId}/subscriptions/{subscriptionId}")
    public ResponseEntity<Subscription> updateSubscription(
            @PathVariable UUID userId,
            @PathVariable UUID subscriptionId,
            @RequestBody Subscription subscription
    ) {
        Subscription updatedSubscription = userService.updateSubscription(userId, subscriptionId, subscription);  // Appel à la méthode du service UserService pour mettre à jour l'abonnement
        return ResponseEntity.ok(updatedSubscription);
    }

    // Endpoint pour supprimer un abonnement d'un utilisateur
    @DeleteMapping("/{userId}/subscriptions/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable UUID userId,
            @PathVariable UUID subscriptionId
    ) {
        userService.deleteSubscription(userId, subscriptionId);  // Appel à la méthode du service UserService pour supprimer l'abonnement
        return ResponseEntity.noContent().build();
    }

    // Endpoint pour ajouter un utilisateur
    @PostMapping
    public User addUser(@RequestBody UserDto user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        return userService.addUser(user.getName(), user.getMail(), encodedPassword, user.getProfil_picture(), user.isAdmin());
    }


    // Endpoint pour récupérer tous les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Endpoint pour récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    // Endpoint pour mettre à jour un utilisateur
    @PutMapping("/{id}")
    public User updateUser(@PathVariable UUID id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser.getName(), updatedUser.getMail(), updatedUser.getPassword());
    }

    // Endpoint pour supprimer un utilisateur
    @DeleteMapping("/{user}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}

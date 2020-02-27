package com.hcs.cloud.tutorial.controller;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hcs.cloud.tutorial.exception.UserNotFoundException;
import com.hcs.cloud.tutorial.model.User;
import com.hcs.cloud.tutorial.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/api/users")
    public List<User> retrieveUsers() {
        List<User> users = this.userService.retrieveUsers();
        return users;
    }

    @GetMapping("/api/users/{id}")
    public User retriveUser(@PathVariable Long id) throws UserNotFoundException {
        User user = this.userService.retrieveUser(id);

        // HATEOAS

        return user;
    }

    @PostMapping("api/users")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        User savedUser = this.userService.saveUser(user);

        // státusz kód beállítása
        // return new ResponseEntity(HttpStatus.CREATED);

        // új resource link visszaküldése
        // path: hozzáfűzi a stringet, és az {id} helyére beilleszti a
        // savedUser.getId() -t
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/api/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
    }

    @GetMapping("/api/hello")
    public String helloWorld() {
        return messageSource.getMessage("hello.message", null, Locale.US);
    }
}

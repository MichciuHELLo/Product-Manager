package com.example.MG_RestaurantManager20.user.adapters.web;

import com.example.MG_RestaurantManager20.user.domain.User;
import com.example.MG_RestaurantManager20.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/add")
    public User addNewProduct(@RequestBody User user) {
        return userService.addNewUser(user);
    }

}

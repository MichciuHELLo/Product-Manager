package com.example.MG_RestaurantManager20.mail.domain;

import com.example.MG_RestaurantManager20.mail.service.EmailService;
import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.service.ProductService;
import com.example.MG_RestaurantManager20.user.domain.User;
import com.example.MG_RestaurantManager20.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductInformer {

    private static ProductService productService = null;
    private static UserService userService = null;
    private static EmailService emailService = null;

    public ProductInformer(ProductService productService, UserService userService, EmailService emailService) {
        ProductInformer.productService = productService;
        ProductInformer.userService = userService;
        ProductInformer.emailService = emailService;
    }

    public static void informMissingProducts() {
        Map<Long, List<Product>> map = new HashMap<>();
        List<Product> products = productService.getProducts();

        for (Product item : products) {
            map.put(item.getUser_fk(), new ArrayList<>());
        }

        for (Product item : products) {
            if (item.getMin() >= item.getQuantity()) {
                List<Product> missingProducts = map.get(item.getUser_fk());
                missingProducts.add(item);
                map.put(item.getUser_fk(), missingProducts);
            }
        }

        for (Long userId : map.keySet()) {
            if (!map.get(userId).isEmpty()) {
                User user = userService.getUserByIdFetch(userId);
                StringBuilder text = new StringBuilder("Dear " + user.getName() + "\nYou are running out of resources!\n\n Here's list of your products with low quantity:");
                for (int i = 0; i < map.get(userId).size(); i++) {
                    int count = i + 1;
                    text.append("\n    " + count + ": " + map.get(userId).get(i).getName() + ". Minimum: " + map.get(userId).get(i).getMin() + ". Currently: " + map.get(userId).get(i).getQuantity());
                }
                emailService.sendEmail(user.getEmail(), "Resources report.", text.toString());
            }
        }
    }

}

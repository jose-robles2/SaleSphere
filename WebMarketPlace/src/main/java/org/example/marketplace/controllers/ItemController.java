package org.example.marketplace.controllers;

import org.example.marketplace.entities.Item;
import org.example.marketplace.entities.User;
import org.example.marketplace.services.ItemService;
import org.example.marketplace.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class ItemController {
    private UserService userService;

    private ItemService itemService;

    public ItemController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @RequestMapping("/") // root of the app
    public String getItems(Model model) {
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("newItem", new Item());  // addItem form submit
        model.addAttribute("users", userService.findAll());
        model.addAttribute("item", new Item());     // buyItem form submit
        model.addAttribute("user", new User());
        model.addAttribute("currentUser", new User());
        return "index";
    }

    @PostMapping("/buyItem")
    public String buyItem(@ModelAttribute Item item, Model model) {
        buyItemHelper(item, model);
        return "redirect:/";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item, Model model) {
        itemService.save(item);
        return "redirect:/"; // send us back to the root so we can display info to user
    }

    private void buyItemHelper(Item item, Model model) {
        Item updatedItem = itemService.buyItem(item, 1);
        itemService.save(updatedItem);
        model.addAttribute("items", itemService.findAll()); // Refresh the list of items and add it to the model
    }

    @PostMapping("/setUser")
    public String setUserSubmit(@ModelAttribute User user, Model model) {
        setUserHelper(user, model);
        return "redirect:/";
    }

    private void setUserHelper(User user, Model model) {
        Optional<User> currentUser = userService.getUser(user.getId());
        userService.setCurrentUser(currentUser);
        model.addAttribute("currentUser", userService.getCurrentUser());
    }

    @RequestMapping("/getCurrentUser")
    public String getCurrentUser(Model model)
    {
        System.out.println(userService.getCurrentUser());
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "redirect:/";
    }

}

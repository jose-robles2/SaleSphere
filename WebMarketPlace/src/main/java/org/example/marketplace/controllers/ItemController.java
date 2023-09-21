package org.example.marketplace.controllers;

import org.example.marketplace.entities.Item;
import org.example.marketplace.services.ItemService;
import org.example.marketplace.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("items", itemService.findAll()); // use the service middleman
        model.addAttribute("newItem", new Item()); // This object lets users add to database
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @PostMapping("/buyItem")
    public String buyItem(@ModelAttribute Item item, Model model) {
        System.out.println("WE MADE IT !");
        return "redirect:/";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item, Model model) {
        itemService.save(item);
        return "redirect:/"; // send us back to the root so we can display info to user
    }
}

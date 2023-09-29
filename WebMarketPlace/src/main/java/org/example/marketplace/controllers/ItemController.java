package org.example.marketplace.controllers;

import org.example.marketplace.entities.Item;
import org.example.marketplace.services.ItemService;
import org.example.marketplace.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.HashSet;
import java.util.List;

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
        updateShoppingCart(model);
        return "index";
    }

    @PostMapping("/buyItem")
    public String buyItem(@ModelAttribute Item item, Model model) {
        buyItemHelper(item, model, 1);
        return "redirect:/";
    }



//    @PostMapping("/triggerError")
//    public String triggerError(RedirectAttributes redirectAttributes) {
//        // This is just a simple example. In a real application,
//        // you might have some condition or validation logic here.
//        String errorMsg = "An error was triggered by the button!";
//
//        redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
//        return "redirect:/";
//    }

    @PostMapping("/triggerError")
    public String triggerError(@RequestParam String customErrorMsg, RedirectAttributes redirectAttributes) {
        String errorMsg = (customErrorMsg != null && !customErrorMsg.isEmpty()) ? customErrorMsg : "An error was triggered by the button!";
        redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
        return "redirect:/";
    }







    @PostMapping("/addItemToCart")
    public String addItemToCart(@ModelAttribute Item item, Model model) {
        itemService.addItemToCart(item);
        updateShoppingCart(model);
        return "redirect:/";
    }

    @PostMapping("/buyItemsFromCart")
    public String buyItemsFromCart(Model model,RedirectAttributes redirectAttributes) {
        List<Item> shoppingCartCopy = itemService.getShoppingCartItems();
        HashSet<String> trackedItemNames = new HashSet<>();
        String ErrorMessage = "";

        for (Item item : shoppingCartCopy) {
            if (!trackedItemNames.contains(item.getName())) {
                int quantity = itemService.getQuantityToDeductStock(item);

                ErrorMessage = itemService.getErrorMessage(item,quantity);
                if (!ErrorMessage.isEmpty())
                {
                    return triggerError(ErrorMessage,redirectAttributes);
                    // Call triggerError if the condition is met


                }
                    trackedItemNames.add(item.getName());
                buyItemHelper(item, model, quantity);
            }
        }
        clearShoppingCart(model);
        updateShoppingCart(model);

        return "redirect:/";

    }

    @PostMapping("/clearShoppingCart")
    public String clearShoppingCart(Model model) {
        itemService.clearShoppingCart();
        updateShoppingCart(model);
        return "redirect:/";

    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item, Model model) {
        itemService.save(item);
        return "redirect:/"; // send us back to the root so we can display info to user
    }

    private void buyItemHelper(Item item, Model model, int quantity) {
        Item updatedItem = itemService.buyItem(item, quantity);
        itemService.save(updatedItem);
        model.addAttribute("items", itemService.findAll()); // Refresh the list of items and add it to the model
    }

    private void updateShoppingCart(Model model) {
        model.addAttribute("cartSize", itemService.getShoppingCartSize());
        model.addAttribute("cartTotal", itemService.getShoppingCartTotal());
    }
}

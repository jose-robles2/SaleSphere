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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
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
        model.addAttribute("users", userService.findAll()); // do we need this?
        model.addAttribute("item", new Item());     // buyItem form submit
        model.addAttribute("user", new User());     // login user form

        updateUserLoginForm(model);
        updateShoppingCart(model);
        return "index";
    }

    @PostMapping("/buyItem")
    public String buyItem(@ModelAttribute Item item, Model model) {
        buyItemHelper(item, model, 1);
        return "redirect:/";
    }

    @PostMapping("/addItemToCart")
    public String addItemToCart(@ModelAttribute Item item, Model model) {
        itemService.addItemToCart(item);
        updateShoppingCart(model);
        return "redirect:/";
    }
    @PostMapping("/triggerError")
    public String triggerError(@RequestParam String customErrorMsg, RedirectAttributes redirectAttributes) {
        String errorMsg = (customErrorMsg != null && !customErrorMsg.isEmpty()) ? customErrorMsg : "An error was triggered by the button!";
        redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
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

    @PostMapping("/setUser")
    public String setUserSubmit(@ModelAttribute User user, Model model) {
        setUserHelper(user, model);
        return "redirect:/";
    }

    private void setUserHelper(User user, Model model) {
        User currentUser = userService.getUser(user.getId());
        userService.setCurrentUser(currentUser);
        updateUserLoginForm(model);
    }

//    @RequestMapping("/getCurrentUser")
//    public String getCurrentUser(Model model)
//    {
//        System.out.println(userService.getCurrentUser());
//        model.addAttribute("currentUser", userService.getCurrentUser());
//        return "redirect:/";
//    }

    private void updateUserLoginForm(Model model) {
        model.addAttribute("currentUser", userService.getCurrentUser()); // add an attribute for current user so index.html can acess and display properties
        model.addAttribute("users", userService.getCurrentUser()); // add current user to list of all users
    }

    private void updateShoppingCart(Model model) {
        model.addAttribute("cartSize", itemService.getShoppingCartSize());
        model.addAttribute("cartTotal", itemService.getShoppingCartTotal());
    }
}

package org.example.marketplace.controllers;

import org.example.marketplace.entities.Item;
import org.example.marketplace.entities.User;
import org.example.marketplace.entities.State;
import org.example.marketplace.services.ItemService;
import org.example.marketplace.services.StateService;
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

    private StateService stateService;

    public ItemController(UserService userService, ItemService itemService, StateService stateService) {
        this.userService = userService;
        this.itemService = itemService;
        this.stateService = stateService;
    }

    @RequestMapping("/") // root of the app
    public String getItems(Model model) {
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("newItem", new Item());  // addItem form submit
        model.addAttribute("users", userService.findAll()); // do we need this?
        model.addAttribute("item", new Item());     // buyItem form submit
        model.addAttribute("user", new User());     // login user form

        updateFrontEnd(model);
        return "index";
    }

    @PostMapping("/buyItem")
    public String buyItem(@ModelAttribute Item item, Model model, RedirectAttributes redirectAttributes) {
        String ErrorMessage = "";
        int quantity = itemService.getQuantityToDeductStock(item);
        ErrorMessage = itemService.getErrorMessage(item,quantity);
        if (!ErrorMessage.isEmpty())
        {
            return triggerError(ErrorMessage,redirectAttributes);
            // Call triggerError if the condition is met
        }


        buyItemHelper(item, model, 1);
        return "redirect:/";
    }

    @PostMapping("/triggerError")
    public String triggerError(@RequestParam String customErrorMsg, RedirectAttributes redirectAttributes) {
        String errorMsg = (customErrorMsg != null && !customErrorMsg.isEmpty()) ? customErrorMsg : "An error was triggered by the button!";
        redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
        return "redirect:/";
    }

    @PostMapping("/addItemToCart")
    public String addItemToCart(@ModelAttribute Item item, Model model,RedirectAttributes redirectAttributes) {
        String ErrorMessage = "";
        int quantity = itemService.getQuantityToDeductStock(item);
        ErrorMessage = itemService.getErrorMessage(item,quantity);
        if (!ErrorMessage.isEmpty())
        {
            return triggerError(ErrorMessage,redirectAttributes);
            // Call triggerError if the condition is met
        }


        itemService.addItemToCart(item);
        return "redirect:/";
    }

    @PostMapping("/buyItemsFromCart")
    public String buyItemsFromCart(Model model) {
        HashSet<String> trackedItemNames = new HashSet<>();

        for (Item item : itemService.getShoppingCartItems()) {
            if (!trackedItemNames.contains(item.getName())) {
                int quantity = itemService.getQuantityToDeductStock(item);
                buyItemHelper(item, model, quantity);
                trackedItemNames.add(item.getName());
            }
        }

        // These must be called here or else the app will only update the shopping cart for 1 item bought individually
        clearShoppingCart(model);
        updateShoppingCart(model);
        return "redirect:/";
    }




    @PostMapping("/clearShoppingCart")
    public String clearShoppingCart(Model model) {
        itemService.clearShoppingCart();
        return "redirect:/";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item, Model model) {
        itemService.save(item);
        return "redirect:/"; // send us back to the root so we can display info to user
    }

    @PostMapping("/setUser")
    public String setUserSubmit(@ModelAttribute User user, Model model) {
        setUserHelper(user, model);
        return "redirect:/";
    }


    private void buyItemHelper(Item item, Model model, int quantity) {
        Item updatedItem = itemService.buyItem(item, quantity);
        itemService.save(updatedItem);
        model.addAttribute("items", itemService.findAll()); // Refresh the list of items and add it to the model
        userService.makePurchase(item.getPrice(), quantity, this.userService.getCurrentUser());
    }

    private void setUserHelper(User user, Model model) {
        if(userService.userExists(user.getId()))
        {
            User currentUser = userService.getUser(user.getId());
            userService.setCurrentUser(currentUser);
        }
        else
        {
            // Add pop up window here
        }
    }

    private void updateFrontEnd(Model model) {
        updateUserLoginForm(model);
        updateShoppingCart(model);
    }

    private void updateUserLoginForm(Model model) {
        model.addAttribute("currentUser", userService.getCurrentUser()); // add an attribute for current user so index.html can acess and display properties
        model.addAttribute("users", userService.getCurrentUser()); // add current user to list of all users
    }

    private void updateShoppingCart(Model model) {
        model.addAttribute("cartSize", itemService.getShoppingCartSize());
        model.addAttribute("cartTotal", itemService.getShoppingCartTotal());
    }
}

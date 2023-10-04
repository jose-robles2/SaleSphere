package org.example.marketplace.controllers;

import org.example.marketplace.entities.Item;
import org.example.marketplace.entities.User;
import org.example.marketplace.services.ItemService;
import org.example.marketplace.services.StateService;
import org.example.marketplace.services.UserService;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashSet;

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

    @RequestMapping("/")
    public String getItems(Model model) {
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("newItem", new Item());  // addItem form submit
        model.addAttribute("item", new Item());     // buyItem form submit
        model.addAttribute("user", new User());     // setUser form submit
        updateFrontEnd(model);
        return "index";
    }

    @PostMapping("/buyItem")
    public String buyItem(@ModelAttribute Item item, Model model) {
        RedirectView redirectView = buyItemHelper(item, model, 1);
        return redirectView.getUrl();
    }

    @PostMapping("/addItemToCart")
    public String addItemToCart(@ModelAttribute Item item, Model model,RedirectAttributes redirectAttributes) {
        itemService.addItemToCart(item);
        return "redirect:/";
    }

    @GetMapping("/triggerError")
    public String triggerError(@RequestParam String customErrorMsg, RedirectAttributes redirectAttributes) {
        String errorMsg = (customErrorMsg != null && !customErrorMsg.isEmpty()) ? customErrorMsg : "An error was triggered by the button!";
        redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
        return "redirect:/";
    }

    @PostMapping("/buyItemsFromCart")
    public String buyItemsFromCart(Model model) {
        if(userService.checkBalance(itemService.getShoppingCartTotal(), this.userService.getCurrentUser()))
        {
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
        else
        {
            // Return error message
            String errMsg = "The total of the cart is more than your balance.";
            triggerErrorHelper(errMsg);
            return "redirect:/";
        }
    }

    @PostMapping("/clearShoppingCart")
    public String clearShoppingCart(Model model) {
        itemService.clearShoppingCart();
        return "redirect:/";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item, Model model) {
        itemService.save(item);
        // item service find by ID, if item already exists don't allow for creation
        return "redirect:/"; // send us back to the root so we can display info to user
    }

    @PostMapping("/setUser")
    public String setUserSubmit(@ModelAttribute User user, Model model) {
        RedirectView redirectView = setUserHelper(user, model);
        return redirectView.getUrl();
    }

    private RedirectView buyItemHelper(Item item, Model model, int quantity) {
        if(userService.checkBalance(item.getPrice(), this.userService.getCurrentUser()))
        {
            Item updatedItem = itemService.buyItem(item, quantity);
            itemService.save(updatedItem);
            model.addAttribute("items", itemService.findAll()); // Refresh the list of items and add it to the model
            userService.makePurchase(item.getPrice(), quantity, this.userService.getCurrentUser());
            return new RedirectView("redirect:/", true);
        }
        else
        {
            // Trigger error
            return new RedirectView("redirect:/", true);
        }

    }

    private RedirectView setUserHelper(User user, Model model) {
        if(userService.userExists(user.getId())) {
            User currentUser = userService.getUser(user.getId());
            userService.setCurrentUser(currentUser);
        }
        else {
            return triggerErrorHelper("ERROR: Inputted user ID does not exist...");
        }
        return new RedirectView("redirect:/", true);
    }

    private RedirectView triggerErrorHelper(String errorMessage) {
        // Redirect to a URL where the error message can be displayed -> calls the @GetMapping triggerError()
        String redirectUrl = "redirect:/triggerError?customErrorMsg=" + errorMessage;
        return new RedirectView(redirectUrl, true);
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
        model.addAttribute("tax", userService.getTax(itemService.getShoppingCartTotal()));
        model.addAttribute("subtotal", userService.getTotalWithTax(itemService.getShoppingCartTotal()));
    }
}

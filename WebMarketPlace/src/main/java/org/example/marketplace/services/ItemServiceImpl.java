package org.example.marketplace.services;

import org.example.marketplace.entities.*;
import org.example.marketplace.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private final ItemRepository itemRepository;

    private final ShoppingCart shoppingCart;

    public ItemServiceImpl(ItemRepository itemRepository, List<Item> shoppingCart) {
        this.itemRepository = itemRepository;
        this.shoppingCart = new ShoppingCart(shoppingCart);
    }

    @Override
    public Iterable<Item> findAll() { return itemRepository.findAll(); }

    @Override
    public Item save(Item item) { return itemRepository.save(item); }

    @Override
    public void delete(Item item) { this.itemRepository.delete(item); }

    @Override
    public Item buyItem(Item item, int quantity) {
        item.setStock(item.getStock() - quantity);
        this.shoppingCart.remove(item);
        return item;
    }

    @Override
    public boolean addItemToCart(Item item) { return shoppingCart.add(item); }

    @Override
    public int getQuantityToDeductStock(Item item) { return shoppingCart.getQuantityToDeductStock(item); }

    @Override
    public List<Item> getShoppingCartItems() { return shoppingCart.getCartItems(); }

    @Override
    public int getShoppingCartSize() { return shoppingCart.getSize(); }

    @Override
    public double getShoppingCartTotal() { return Double.parseDouble(decimalFormat.format(this.shoppingCart.getTotal())); }

    @Override
    public void clearShoppingCart() { shoppingCart.clear(); }

    public boolean isValidPurchase(User currUser, Item item) {
        if (item.getCategory() < 0 || item.getCategory() >= Category.values().length) {
            return false;
        }

        Category category = Category.values()[item.getCategory()]; // Convert category ordinal to enum

        // Check if the item is allowed in the user's state
        if (!isCategoryAllowed(category, currUser)) {
            return false;
        }

        return currUser.getAge() >= getCategoryAge(category, currUser);
    }

    private boolean isCategoryAllowed(Category category, User user) {
        return switch (category) {
            case FIREARM -> user.getState().isFirearmsAllowed();
            case ALCOHOL -> user.getState().isAlcoholAllowed();
            case DRUGS -> user.getState().isDrugAllowed();
            case MEDICINE -> user.getState().isMedicineAllowed();
            case TECHNOLOGY -> user.getState().isTechnologyAllowed();
            case TOBACCO -> user.getState().isTobaccoAllowed();
            default -> false;
        };
    }

    private int getCategoryAge(Category category, User user) {
        return switch (category) {
            case FIREARM -> user.getState().getFirearmsAge();
            case ALCOHOL -> user.getState().getAlcoholAge();
            case DRUGS -> user.getState().getDrugsAge();
            case MEDICINE -> user.getState().getMedicineAge();
            case TECHNOLOGY -> user.getState().getTechnologyAge();
            case TOBACCO -> user.getState().getTobaccoAge();
            default -> 0;
        };
    }

    private double getCategoryTaxRate(Category category, String userState) {
        return switch (category) {
            case FIREARM -> (userState.equals("AZ") || userState.equals("AR")) ? 0.05 : 0;
            case ALCOHOL -> (userState.equals("AZ") || userState.equals("AK")) ? 0.10 : 0;
            case DRUGS -> (userState.equals("CA") || userState.equals("AR")) ? 0.15 : 0;
            case MEDICINE -> (userState.equals("AK") || userState.equals("CA")) ? 0.05 : 0;
            case TECHNOLOGY -> (userState.equals("AR") || userState.equals("AZ")) ? 0.07 : 0;
            case TOBACCO -> (userState.equals("CA") || userState.equals("AK")) ? 0.09 : 0;
            default -> 0;
        };
    }

//    public boolean isValidPurchase(User currUser, Item item)
//    {
//        // This will be used to make sure that purchases are allowed
//        if(item.getCategory() == Category.FIREARM.ordinal())
//        {
//            if(!currUser.getState().isFirearmsAllowed())
//            {
//                return false;
//            }
//            else
//            {
//                if (currUser.getAge() >= currUser.getState().getFirearmsAge())
//                {
//                    String userStateName = currUser.getState().getStateName();
//                    if (Objects.equals(userStateName, "AZ") ||
//                            (Objects.equals(userStateName, "AR")))
//                    {
//                        // Firearms in these states receive an added 5% tax
//                        // Increase the items field as this field will only be changed for the current item
//                        double newPrice = item.getPrice() + (item.getPrice() * 0.05);
//                        item.setPrice(newPrice);
//                    }
//                    return true;
//                }
//                return false;
//            }
//        }
//
//        if(item.getCategory() == Category.ALCOHOL.ordinal())
//        {
//            if(!currUser.getState().isAlcoholAllowed())
//            {
//                return false;
//            }
//            else
//            {
//                if (currUser.getAge() >= currUser.getState().getAlcoholAge())
//                {
//                    String userStateName = currUser.getState().getStateName();
//                    if (Objects.equals(userStateName, "AZ") ||
//                            (Objects.equals(userStateName, "AK")))
//                    {
//                        // Alcohol in these states receive an added 10% tax
//                        // Increase the items field as this field will only be changed for the current item
//                        double newPrice = item.getPrice() + (item.getPrice() * 0.10);
//                        item.setPrice(newPrice);
//                    }
//                    return true;
//                }
//                return false;
//            }
//        }
//
//        if(item.getCategory() == Category.DRUGS.ordinal())
//        {
//            if(!currUser.getState().isDrugAllowed())
//            {
//                return false;
//            }
//            else
//            {
//                if (currUser.getAge() >= currUser.getState().getDrugsAge())
//                {
//                    String userStateName = currUser.getState().getStateName();
//                    if (Objects.equals(userStateName, "CA") ||
//                            (Objects.equals(userStateName, "AR")))
//                    {
//                        // Drugs in these states receive an added 15% tax
//                        // Increase the items field as this field will only be changed for the current item
//                        double newPrice = item.getPrice() + (item.getPrice() * 0.15);
//                        item.setPrice(newPrice);
//                    }
//                    return true;
//                }
//                return false;
//            }
//        }
//
//        if(item.getCategory() == Category.MEDICINE.ordinal())
//        {
//            if(!currUser.getState().isMedicineAllowed())
//            {
//                return false;
//            }
//            else
//            {
//                if (currUser.getAge() >= currUser.getState().getMedicineAge())
//                {
//                    String userStateName = currUser.getState().getStateName();
//                    if (Objects.equals(userStateName, "AK") ||
//                            (Objects.equals(userStateName, "CA")))
//                    {
//                        // Medicine in these states receive an added 5% tax
//                        // Increase the items field as this field will only be changed for the current item
//                        double newPrice = item.getPrice() + (item.getPrice() * 0.05);
//                        item.setPrice(newPrice);
//                    }
//                    return true;
//                }
//                return false;
//            }
//        }
//
//        if(item.getCategory() == Category.TECHNOLOGY.ordinal())
//        {
//            if(!currUser.getState().isTechnologyAllowed())
//            {
//                return false;
//            }
//            else
//            {
//                if (currUser.getAge() >= currUser.getState().getTechnologyAge())
//                {
//                    String userStateName = currUser.getState().getStateName();
//                    if (Objects.equals(userStateName, "AR") ||
//                            (Objects.equals(userStateName, "AZ")))
//                    {
//                        // TECHNOLOGY in these states receive an added 7% tax
//                        // Increase the items field as this field will only be changed for the current item
//                        double newPrice = item.getPrice() + (item.getPrice() * 0.07);
//                        item.setPrice(newPrice);
//                    }
//                    return true;
//                }
//                return false;
//            }
//        }
//
//        if(item.getCategory() == Category.TOBACCO.ordinal())
//        {
//            if(!currUser.getState().isTobaccoAllowed())
//            {
//                return false;
//            }
//            else
//            {
//                if (currUser.getAge() >= currUser.getState().getTobaccoAge())
//                {
//                    String userStateName = currUser.getState().getStateName();
//                    if (Objects.equals(userStateName, "CA") ||
//                            (Objects.equals(userStateName, "AK")))
//                    {
//                        // TECHNOLOGY in these states receive an added 9% tax
//                        // Increase the items field as this field will only be changed for the current item
//                        double newPrice = item.getPrice() + (item.getPrice() * 0.09);
//                        item.setPrice(newPrice);
//                    }
//                    return true;
//                }
//                return false;
//            }
//        }
//        return false;
//    }
}

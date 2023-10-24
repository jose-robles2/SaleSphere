package org.example.marketplace.services;

import org.example.marketplace.entities.Item;
import org.example.marketplace.entities.State;
import org.example.marketplace.entities.User;
import org.example.marketplace.entities.Category;
import org.example.marketplace.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ItemServiceImpl implements ItemService {

    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private final ItemRepository itemRepository;

    private final List<Item> shoppingCart;

    private final HashMap<String, Integer> itemCountMap = new HashMap<>();

    public ItemServiceImpl(ItemRepository itemRepository, List<Item> shoppingCart) {
        this.itemRepository = itemRepository;
        this.shoppingCart = shoppingCart;
    }

    @Override
    public Iterable<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item save(Item item) { return itemRepository.save(item); }

    @Override
    public void delete(Item item) { this.itemRepository.delete(item); }

    @Override
    public Item buyItem(Item item, int quantity) {
        if (item.getStock() <= 0) {
            System.out.println("ERROR: item is no longer in stock...");
            return item;
        }
        item.setStock(item.getStock() - quantity);

        // Remove the bought item from cart
        for(Item cartItem : this.shoppingCart) {
            if (Objects.equals(item.getName(), cartItem.getName())) {
                this.shoppingCart.remove(cartItem);
                break;
            }
        }

        return item;
    }

    @Override
    public void addItemToCart(Item item) {
        //Don't allow for more items in cart than available stock for a certain item
        if (itemCountMap.containsKey(item.getName()) && itemCountMap.get(item.getName()) == item.getStock() || item.getStock() == 0) {
            System.out.println("Number of items in cart for " + item.getName() + " cannot exceed item stock amount");
            return;
        }

        // Add the item to the cart and the itemCount map
        shoppingCart.add(item);
        if (!itemCountMap.containsKey(item.getName())) {
            itemCountMap.put(item.getName(), 1);
        }
        else {
            Integer count = itemCountMap.get(item.getName());
            count += 1;
            itemCountMap.put(item.getName(), count);
        }
    }

    @Override
    public int getQuantityToDeductStock(Item targetItem) {
        int quantity = 0;
        for (Item item : shoppingCart) {
            if (item.getName().equals(targetItem.getName())) {
                quantity += 1;
            }
        }
        return quantity;
    }

    @Override
    public List<Item> getShoppingCartItems() {
        return new ArrayList<Item>(shoppingCart);
    }

    @Override
    public int getShoppingCartSize() {
        return shoppingCart.size();
    }

    @Override
    public double getShoppingCartTotal() {
        double total = 0.0;
        for (Item item : shoppingCart) {
            total += item.getPrice();
        }
        return Double.parseDouble(decimalFormat.format(total));
    }

    @Override
    public void clearShoppingCart() {
        itemCountMap.clear();
        shoppingCart.clear();
    }

    public boolean isValidPurchase(User currUser, Item item)
    {
        // This will be used to make sure that purchases are allowed
        if(item.getCategory() == Category.FIREARM.ordinal())
        {
            if(!currUser.getState().isFirearmsAllowed())
            {
                return false;
            }
            else
            {
                if (currUser.getAge() >= currUser.getState().getFirearmsAge())
                {
                    String userStateName = currUser.getState().getStateName();
                    if (Objects.equals(userStateName, "CA") ||
                            (Objects.equals(userStateName, "AR")))
                    {
                        // Firearms in these states receive an added 5% tax
                        // Increase the items field as this field will only be changed for the current item
                        double newPrice = item.getPrice() + (item.getPrice() * 0.05);
                        item.setPrice(newPrice);
                    }
                    return true;
                }
                return false;
            }
        }

        if(item.getCategory() == Category.ALCOHOL.ordinal())
        {
            if(!currUser.getState().isAlcoholAllowed())
            {
                return false;
            }
            else
            {
                if (currUser.getAge() >= currUser.getState().getAlcoholAge())
                {
                    String userStateName = currUser.getState().getStateName();
                    if (Objects.equals(userStateName, "AZ") ||
                            (Objects.equals(userStateName, "AK")))
                    {
                        // Alcohol in these states receive an added 10% tax
                        // Increase the items field as this field will only be changed for the current item
                        double newPrice = item.getPrice() + (item.getPrice() * 0.10);
                        item.setPrice(newPrice);
                    }
                    return true;
                }
                return false;
            }
        }

        if(item.getCategory() == Category.DRUGS.ordinal())
        {
            if(!currUser.getState().isDrugAllowed())
            {
                return false;
            }
            else
            {
                if (currUser.getAge() >= currUser.getState().getDrugsAge())
                {
                    String userStateName = currUser.getState().getStateName();
                    if (Objects.equals(userStateName, "AL") ||
                            (Objects.equals(userStateName, "AK")))
                    {
                        // Drugs in these states receive an added 15% tax
                        // Increase the items field as this field will only be changed for the current item
                        double newPrice = item.getPrice() + (item.getPrice() * 0.15);
                        item.setPrice(newPrice);
                    }
                    return true;
                }
                return false;
            }
        }

        if(item.getCategory() == Category.MEDICINE.ordinal())
        {
            if(!currUser.getState().isMedicineAllowed())
            {
                return false;
            }
            else
            {
                if (currUser.getAge() >= currUser.getState().getMedicineAge())
                {
                    String userStateName = currUser.getState().getStateName();
                    if (Objects.equals(userStateName, "WA") ||
                            (Objects.equals(userStateName, "CA")))
                    {
                        // Medicine in these states receive an added 5% tax
                        // Increase the items field as this field will only be changed for the current item
                        double newPrice = item.getPrice() + (item.getPrice() * 0.05);
                        item.setPrice(newPrice);
                    }
                    return true;
                }
                return false;
            }
        }

        if(item.getCategory() == Category.TECHNOLOGY.ordinal())
        {
            if(!currUser.getState().isTechnologyAllowed())
            {
                return false;
            }
            else
            {
                if (currUser.getAge() >= currUser.getState().getTechnologyAge())
                {
                    String userStateName = currUser.getState().getStateName();
                    if (Objects.equals(userStateName, "AR") ||
                            (Objects.equals(userStateName, "AZ")))
                    {
                        // TECHNOLOGY in these states receive an added 7% tax
                        // Increase the items field as this field will only be changed for the current item
                        double newPrice = item.getPrice() + (item.getPrice() * 0.07);
                        item.setPrice(newPrice);
                    }
                    return true;
                }
                return false;
            }
        }

        if(item.getCategory() == Category.TOBACCO.ordinal())
        {
            if(!currUser.getState().isTobaccoAllowed())
            {
                return false;
            }
            else
            {
                if (currUser.getAge() >= currUser.getState().getTobaccoAge())
                {
                    String userStateName = currUser.getState().getStateName();
                    if (Objects.equals(userStateName, "CA") ||
                            (Objects.equals(userStateName, "WA")))
                    {
                        // TECHNOLOGY in these states receive an added 9% tax
                        // Increase the items field as this field will only be changed for the current item
                        double newPrice = item.getPrice() + (item.getPrice() * 0.09);
                        item.setPrice(newPrice);
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}

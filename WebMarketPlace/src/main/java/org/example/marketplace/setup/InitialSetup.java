package org.example.marketplace.setup;

import org.example.marketplace.entities.Category;
import org.example.marketplace.entities.Item;
import org.example.marketplace.entities.User;
import org.example.marketplace.repositories.ItemRepository;
import org.example.marketplace.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Spring will initialize this component, then look at the deps. and inject instances of the deps. into its constr.

@Component
public class InitialSetup implements CommandLineRunner {

    private UserRepository userRepository;

    private ItemRepository itemRepository;

    public InitialSetup(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing database...");
        Item phone = new Item("Phone", "IPhone 15", "/image", "Washington", 1199.99, 5, Category.TECHNOLOGY);
        Item laptop = new Item("Laptop", "Macbook Pro", "/image", "Washington", 2199.99, 5, Category.TECHNOLOGY);
        Item tv = new Item("TV", "Samsund 4K TV", "/image", "Washington", 999.99, 5, Category.TECHNOLOGY);
        Item tablet = new Item("Tablet", "iPad Pro", "/image", "Washington", 799.99, 5, Category.TECHNOLOGY);
        Item headphones = new Item("Headphones", "Sony Noise-Canceling", "/image", "Washington", 299.99, 5, Category.TECHNOLOGY);
        Item camera = new Item("Camera", "Canon EOS", "/image", "Washington", 899.99, 5, Category.TECHNOLOGY);

//        Item smartwatch = new Item("Smartwatch", "Apple Watch Series 7", "/image", "Washington", 399.99, 5);
//        Item speaker = new Item("Speaker", "Bose SoundLink", "/image", "Washington", 199.99, 5);
//        Item gamingConsole = new Item("Gaming Console", "PlayStation 5", "/image", "Washington", 499.99, 5);
//        Item keyboard = new Item("Keyboard", "Logitech G Pro", "/image", "Washington", 129.99, 5);
//        Item mouse = new Item("Mouse", "Razer DeathAdder", "/image", "Washington", 69.99, 5);
//        Item printer = new Item("Printer", "HP LaserJet Pro", "/image", "Washington", 249.99, 5);

        Item handgun = new Item("Handgun", "Kimber Stainless 45 Auto", "/image", "Idaho", 699.99, 3, Category.FIREARM);
        Item semiAutoRifle = new Item("Semi-Auto Rifle", "CMMG Mk4 B16", "/image", "Alabama", 999.99, 1, Category.FIREARM);
        Item shotgun = new Item("Shotgun", "Benelli M1014 12 Gauge", "/image", "Florida", 1899.99, 1, Category.FIREARM);

        Item moonShine = new Item("Moonshine", "Naturally brewed alcohol", "/image", "Washington", 59.99, 4, Category.ALCOHOL);
        Item vodka = new Item("Vodka", "Tito's Vodka", "/image", "California", 39.99, 4, Category.ALCOHOL);
        Item rum = new Item("Rum", "Malibu Caribbean Rum", "/image", "Oregon", 19.99, 4, Category.ALCOHOL);

        Item percocet = new Item("Percocet Painkillers", "Percocet Max Strength", "/image", "Washington", 59.99, 4, Category.MEDICINE);
        Item oxycodone = new Item("Oxycodone Painkillers", "Oxycodone Max Strength", "/image", "Oregon", 59.99, 4, Category.MEDICINE);
        Item hydrocodone = new Item("Hydrocodone Painkillers", "Hydrocodone Max Strength", "/image", "California", 59.99, 4, Category.MEDICINE);

        Item tobacco = new Item("Classic Cigarettes", "Large tobacco cigarette pack", "/image", "Washington", 9.99, 4, Category.DRUGS);
        Item vape = new Item("Classic Vape", "Large vape pack", "/image", "Oregon", 9.99, 4, Category.DRUGS);
        Item marijuana = new Item("Classic Marijuana", "Large weed pack", "/image", "California", 9.99, 4, Category.DRUGS);

        phone = itemRepository.save(phone);
        laptop = itemRepository.save(laptop);
        tv = itemRepository.save(tv);
        tablet = itemRepository.save(tablet);
        headphones = itemRepository.save(headphones);
        camera = itemRepository.save(camera);

//        smartwatch = itemRepository.save(smartwatch);
//        speaker = itemRepository.save(speaker);
//        gamingConsole = itemRepository.save(gamingConsole);
//        keyboard = itemRepository.save(keyboard);
//        mouse = itemRepository.save(mouse);
//        printer = itemRepository.save(printer);

        itemRepository.save(handgun);
        itemRepository.save(semiAutoRifle);
        itemRepository.save(shotgun);

        itemRepository.save(moonShine);
        itemRepository.save(vodka);
        itemRepository.save(rum);

        itemRepository.save(percocet);
        itemRepository.save(oxycodone);
        itemRepository.save(hydrocodone);

        itemRepository.save(tobacco);
        itemRepository.save(vape);
        itemRepository.save(marijuana);

        User user = new User("Will", "Hiatt", "wHiatt", "Washington");
        user = userRepository.save(user);

        System.out.println(phone);
        System.out.println(laptop);
        System.out.println(tv);
        System.out.println(user);
        System.out.println(tablet);
        System.out.println(headphones);
        System.out.println(camera);

//        System.out.println(smartwatch);
//        System.out.println(speaker);
//        System.out.println(gamingConsole);
//        System.out.println(keyboard);
//        System.out.println(mouse);
//        System.out.println(printer);

    }
}

package org.example.marketplace.setup;

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
        Item phone = new Item("Phone", "IPhone 15", "/image", "Washington", 1199.99, 5);
        Item laptop = new Item("Laptop", "Macbook Pro", "/image", "Washington", 2199.99, 5);
        Item tv = new Item("TV", "Samsund 4K TV", "/image", "Washington", 999.99, 5);
        Item tablet = new Item("Tablet", "iPad Pro", "/image", "Washington", 799.99, 5);
        Item headphones = new Item("Headphones", "Sony Noise-Canceling", "/image", "Washington", 299.99, 5);
        Item camera = new Item("Camera", "Canon EOS", "/image", "Washington", 899.99, 5);

        Item smartwatch = new Item("Smartwatch", "Apple Watch Series 7", "/image", "Washington", 399.99, 5);
        Item speaker = new Item("Speaker", "Bose SoundLink", "/image", "Washington", 199.99, 5);
        Item gamingConsole = new Item("Gaming Console", "PlayStation 5", "/image", "Washington", 499.99, 5);
        Item keyboard = new Item("Keyboard", "Logitech G Pro", "/image", "Washington", 129.99, 5);
        Item mouse = new Item("Mouse", "Razer DeathAdder", "/image", "Washington", 69.99, 5);
        Item printer = new Item("Printer", "HP LaserJet Pro", "/image", "Washington", 249.99, 0);


        phone = itemRepository.save(phone);
        laptop = itemRepository.save(laptop);
        tv = itemRepository.save(tv);
        tablet = itemRepository.save(tablet);
        headphones = itemRepository.save(headphones);
        camera = itemRepository.save(camera);

        smartwatch = itemRepository.save(smartwatch);
        speaker = itemRepository.save(speaker);
        gamingConsole = itemRepository.save(gamingConsole);
        keyboard = itemRepository.save(keyboard);
        mouse = itemRepository.save(mouse);
        printer = itemRepository.save(printer);


        User user = new User("Will", "Hiatt", "wHiatt", "Washington");
        user = userRepository.save(user);

        System.out.println(phone);
        System.out.println(laptop);
        System.out.println(tv);
        System.out.println(user);
        System.out.println(tablet);
        System.out.println(headphones);
        System.out.println(camera);

        System.out.println(smartwatch);
        System.out.println(speaker);
        System.out.println(gamingConsole);
        System.out.println(keyboard);
        System.out.println(mouse);
        System.out.println(printer);

    }
}

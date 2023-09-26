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

        phone = itemRepository.save(phone);
        laptop = itemRepository.save(laptop);
        tv = itemRepository.save(tv);
        tablet = itemRepository.save(tablet);
        headphones = itemRepository.save(headphones);
        camera = itemRepository.save(camera);

        User user = new User("Will", "Hiatt", "wHiatt", "Washington");
        User user2 = new User("Jose", "Robles", "jRob", "Washington");
        user = userRepository.save(user);
        user2 = userRepository.save(user2);
        System.out.println(phone);
        System.out.println(laptop);
        System.out.println(tv);
        System.out.println(tablet);
        System.out.println(headphones);
        System.out.println(camera);

        System.out.println(user);
        System.out.println(user2);
    }
}

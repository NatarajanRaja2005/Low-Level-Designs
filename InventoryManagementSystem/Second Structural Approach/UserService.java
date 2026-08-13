package main;

import java.util.HashMap;
import java.util.List;

public class UserService {
    public static Long count=1L;
    
    HashMap<String,User> map=new HashMap<>();

    public User createUser(String name,String email,String password){
        User user=getUserByEmail(email);
        if(user!=null){
            System.out.println("User already exists...");
            return user;
        }
        user=new User();
        user.setId(count++);
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);

        map.put(email.toLowerCase(), user);
        System.out.println("User created successfully!");
        return user;
    }

    public User getUserByEmail(String email){
        email=email.toLowerCase();
        User user=map.get(email);
        if(user==null){
            System.out.println("New User found!");
            return null;
        }
        return user;
    }
    
    public void displayOrderOfParticularUser(String email){
        User user=getUserByEmail(email);
        if(user==null){
            return;
        }
        List<Order> orders=user.getOrders();
        for(int i=0;i<orders.size();i++){
            orders.get(i).display();
        }
    }
}

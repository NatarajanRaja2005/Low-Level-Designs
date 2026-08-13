package main;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private List<Order> orders=new ArrayList<>();
    
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString(){
        return "Name: "+this.name+" Email: "+this.email;
    }
}

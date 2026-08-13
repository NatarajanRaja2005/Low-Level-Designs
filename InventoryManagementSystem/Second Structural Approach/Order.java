package main;

import java.util.*;

enum OrderStatus{
    PENDING,DELIVERED,FAILED,CANCELLED;
}
public class Order {
    private Long id;
    private User user;
    private List<OrderDetails> orderDetails=new ArrayList<>();
    private OrderStatus orderStatus;
    private int price;

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
    
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    public void display(){
        System.out.println("Order id: "+this.id+" UserName: "+
        this.getUser().getEmail()+" Order status: "+this.getOrderStatus().toString());
        for(OrderDetails od:orderDetails){
            System.out.println(od.toString());
        }
    }
}

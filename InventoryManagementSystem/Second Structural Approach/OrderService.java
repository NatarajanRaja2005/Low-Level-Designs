package main;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class OrderService {
    public static Long count=1L;
    
    UserService userService;
    ProductService productService;
    OrderDetailsService orderDetailsService = new OrderDetailsService();
    HashMap<Long, Order> map = new HashMap<>();
 
    public OrderService(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }
    
    public Order createOrder(String email){
        User user=userService.getUserByEmail(email);
        if(user==null){
            return null;
        }
        Order order=new Order();
        order.setOrderStatus(OrderStatus.PENDING);
        order.setUser(user);
        order.setId(count++);

        map.put(order.getId(), order);
        return order;
    }

    public Order addProductsToOrder(Long orderId,String productName,int quantity){
        Product product=productService.getProductByName(productName);
        Order order=getOrderById(orderId);
        if(product==null || order==null){
            return null;
        }
        OrderDetails orderDetails=orderDetailsService.createOrderDetails(order, product, quantity);
        order.getOrderDetails().add(orderDetails);
        order.getOrderDetails().add(orderDetails);
        product.setInventory(product.getInventory() - quantity); 
        order.setPrice(updatePriceOfOrder(orderId));             
        System.out.println("Products are added to the order Successfully!");
        return order;
    }

    public Order getOrderById(Long orderId){
        Order order=map.get(orderId);
        if(order==null){
            System.out.println("Order is not found!");
            return null;
        }
        return order;
    }

    public Order cancelOrder(Long orderId){
        Order order=getOrderById(orderId);
        if(order==null){
            return null;
        }
        for (OrderDetails od : order.getOrderDetails()) {
            od.getProduct().setInventory(od.getProduct().getInventory() + od.getQuantity()); 
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return order;
    }

    public int updatePriceOfOrder(Long orderId){
        Order order=getOrderById(orderId);
        if(order==null){
            return 0;
        }
        int price=0;
        List<OrderDetails> details=order.getOrderDetails();
        for(OrderDetails od:details){
            price+=od.getPrice();
        }

        return price;
    }
}

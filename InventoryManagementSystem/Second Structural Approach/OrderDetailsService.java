package main;

import java.util.*;

public class OrderDetailsService {
    public static Long count=1L;
    Map<Long,OrderDetails> map=new HashMap();

    public OrderDetails createOrderDetails(Order order,Product product,int quantity){
        OrderDetails orderDetails=new OrderDetails();
        orderDetails.setId(count++);
        orderDetails.setOrder(order);
        orderDetails.setProduct(product);
        orderDetails.setQuantity(quantity);
        orderDetails.setPrice(quantity*product.getPrice());

        map.put(orderDetails.getId(), orderDetails);
        return orderDetails;
    }

    

    public OrderDetails getOrderDetailsById(Long id){
        OrderDetails orderDetails=map.get(id);
        if(orderDetails==null){
            System.out.println("Orde details not found!");
            return null;
        }
        return orderDetails;
    }
}

package main;

import java.util.HashSet;
import java.util.Set;

public class Category {
    private Long id;
    private String categoryName;
    private Set<Product> products=new HashSet<>();
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Set<Product> getProducts() {
        return products;
    }
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public String toString(){
        System.out.println("Category Name: "+this.categoryName+" Products are: ");
        for(Product p:this.products){
            System.out.println(p.toString());
        }
        return " ";
    }
}

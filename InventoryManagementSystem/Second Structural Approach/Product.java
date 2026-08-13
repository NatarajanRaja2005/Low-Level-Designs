package main;

public class Product {
    private Long id;
    private String productName;
    private String description;
    private Category category;
    private int price;
    private int inventory;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getInventory() {
        return inventory;
    }
    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString(){
        return "ProductName: "+this.productName+" Description: "+this.description+" Category: "+this.category.getCategoryName()+" Inventory: "+this.inventory+" Price: "+this.price;
    }
}

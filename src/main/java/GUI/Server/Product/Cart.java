package GUI.Server.Product;

import DTO.Product;

import java.util.List;

public class Cart {
    private List<CartItem> itemList;
    private double total;

    public void addItem(Product product, int quantity) {
        CartItem item = new CartItem(product,quantity);
        itemList.add(item);
        product.setStock(product.getStock()-quantity);
    }
}

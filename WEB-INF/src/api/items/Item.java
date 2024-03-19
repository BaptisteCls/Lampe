package api.items;

import java.util.ArrayList;
import java.util.List;

public class Item {
    String name;
    List<String> images, colors;
    int id;
    double price;

    public Item(String name, double price, int id) {
        this.name = name;
        this.price = price;
        this.id = id;
        images = new ArrayList<>();
        colors = new ArrayList<>();
    }

    public void addAColor(String color){
        colors.add(color);
    }

    public void addAnImage(String image){
        images.add(image);
    }
}

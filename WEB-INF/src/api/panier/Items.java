package api.panier;

public class Items {
    String name, image, color;
    int number, id;
    double price;

    public Items(String name, String image, String color, int number, double price, int id) {
        this.name = name;
        this.image = image;
        this.color = color;
        this.number = number;
        this.price = price;
        this.id = id;
    }

    
}

//Base class for all products the store will sell
public abstract class Product implements Comparable<Product>{
    private double price;
    private int stockQuantity;
    private int soldQuantity;

    //a4
    private int cartQuantity;

    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
        //a4 set the initial value to be 0
        cartQuantity=0;
        soldQuantity=0;
    }

    //here
    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public double getPrice() {
        return price;
    }

    //a4 setter
    public void setStockQuantity(int num) {
        stockQuantity=num;
    }
    //a4 setter and getter for cartQuantity
    public int getCartQuantity(){return cartQuantity;}
    public void setCartQuantity(int num){cartQuantity=num;}
    public void setSoldQuantity(int num) {
        soldQuantity=num;
    }
    public int getsSoldQuantity(){return soldQuantity;}


    //Returns the total revenue (price * amount) if there are at least amount items in stock
    //Return 0 otherwise (i.e., there is no sale completed)
    public double sellUnits(int amount) {
        if (amount > 0 && stockQuantity >= amount) {
            stockQuantity -= amount;
            soldQuantity += amount;
            return price * amount;
        }
        return 0.0;
    }

    public int compareTo(Product p){
        return (p.soldQuantity-this.soldQuantity);
    }
}
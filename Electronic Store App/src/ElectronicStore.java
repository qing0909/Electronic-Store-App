//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import java.util.*;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have / 10 types
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue;
    //a4
    private int numOfSales;
    private Product[] exactStock;
    private boolean flag;
    private double curCartMoney;
    private Product[] popularSortedList;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        //a4
        numOfSales = 0;
        flag = true;
        curCartMoney = 0;
    }


    public String getName() {
        return name;
    }

    //a4 getters and setters
    public double getRevenue() {return revenue;}
    public int getNumOfSales(){return numOfSales;}
    public double getCurCartMoney() {return curCartMoney;}
    public void setCurCartMoney(double num) {curCartMoney = num;}
    public Product[] getPopularSortedList(){return popularSortedList;}


    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }

    //not sure whether it is a good way to get the exactStock
    //get the exact stock (without null) to avoid null-pointer
    public Product[] getExactStock() {
        if (flag) {                                 //this process can only be executed only once
            exactStock = new Product[curProducts];
            for (int i = 0; i < curProducts; i++) {
                exactStock[i] = stock[i];
            }
        }
        flag = false;
        return exactStock;
    }


    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }

    public int findStockIndex(String item) {
        int index = -1;
        for (int i = 0; i < getExactStock().length; i++) {
            if (getExactStock()[i].toString().equals(item)) {
                index = i;
            }
        }
        return index;         //if finds nothing then returns -1
    }


    //add to Cart
    public void addToCart(String s) {
        int indexOfStock = findStockIndex(s);
        //do operation 'stock should decrease'
        int preQ1 = getExactStock()[indexOfStock].getStockQuantity();
        getExactStock()[indexOfStock].setStockQuantity(preQ1 - 1);
        //do operation 'cart should increase'
        int preQ2 = getExactStock()[indexOfStock].getCartQuantity();
        getExactStock()[indexOfStock].setCartQuantity(preQ2 + 1);
    }

    //remove from Cart
    public void removeFromCart(String s) {
        //modify string s (since it always has 'number x' in front of the whole string)
        String[] parts = s.split(" ");   //use blank space to split the string
        String result = parts[2];              //parts[0] is a number; parts[1] is char 'x' ; should ignore first two elements
        for (int i = 3; i < parts.length; i++) {
            result += " " + parts[i];          //'glue' the parts together by using blank space ''
        }

        int indexOfStock = findStockIndex(result);
        //do operation 'cart should decrease'
        int preQ1 = getExactStock()[indexOfStock].getCartQuantity();
        getExactStock()[indexOfStock].setCartQuantity(preQ1 - 1);
        //do operation 'stock should increase'
        int preQ2 = getExactStock()[indexOfStock].getStockQuantity();
        getExactStock()[indexOfStock].setStockQuantity(preQ2 + 1);

    }

    //update the stock-list and cart-list
    public void complete() {
        boolean flag2 = false;              //local variable
        // cartList data set to be zero (let the third column to be blank)
        for (int i = 0; i < exactStock.length; i++) {
            if (exactStock[i].getCartQuantity() > 0) {
                int num = exactStock[i].getCartQuantity();
                int numPreSold= exactStock[i].getSoldQuantity();
                exactStock[i].setSoldQuantity(numPreSold+num);   //update the sold quantity
                exactStock[i].setCartQuantity(0);  //clear the cart-list (let 3rd col be blank)
                flag2 = true;                       //means there is at least one element in cart-list
            }
        }

        if (flag2) {
            numOfSales += 1;      //should increase by 1 if there is at least one product in the current cart
            revenue+=getCurCartMoney(); //update the revenue
            setCurCartMoney(0);   //when it completes we should clear the money for cart

            //update the popular list (sort)
            ArrayList<Product> sortList=new ArrayList<Product>(Arrays.asList(getExactStock()));
            Collections.sort(sortList);
            popularSortedList=sortList.toArray(new Product[sortList.size()]);
        }


    }


}




import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ElectronicStoreView extends Pane{
    private ElectronicStore model;

    //user interface components
    //3 ListView
    private ListView<String> popularList;
    private ListView<String> stockList;
    private ListView<String> cartList;
    //3 TextField
    private TextField newItemField1;   //for # Sales
    private TextField newItemField2;   //for Revenue
    private TextField newItemField3;   //for $/Sale
    //1 Label
    private Label cartLabel;
    //4 buttons
    private Button resetButton;
    private Button addButton;
    private Button removeButton;
    private Button completeButton;

    //getters for ListView
    public ListView<String> getStockList(){return stockList;}
    public ListView<String> getCartList(){return cartList;}

    //getter for buttons
    public Button getResetButton(){return resetButton;}
    public Button getCompleteButton() {return completeButton;}
    public Button getAddButton() {return addButton;}
    public Button getRemoveButton(){return removeButton;}


    //constructor for the view
    public ElectronicStoreView(){

        //create and position the labels(6+1)
        Label label1=new Label("Store Summary:");
        label1.relocate(10,10); label1.setPrefSize(200,40);
        label1.setAlignment(Pos.CENTER);

        Label label2=new Label("# Sales:");
        label2.relocate(10,55); label2.setPrefSize(95,40);
        label2.setAlignment(Pos.CENTER_RIGHT);

        Label label3=new Label("Revenue:");
        label3.relocate(10,100); label3.setPrefSize(95,40);
        label3.setAlignment(Pos.CENTER_RIGHT);

        Label label4=new Label("$/Sale:");
        label4.relocate(10,145); label4.setPrefSize(95,40);
        label4.setAlignment(Pos.CENTER_RIGHT);

        Label label5=new Label("Most Popular Items:");
        label5.relocate(10,190); label5.setPrefSize(200,30);
        label5.setAlignment(Pos.CENTER);

        Label label6=new Label("Store Stock:");
        label6.relocate(220,10); label6.setPrefSize(300,40);
        label6.setAlignment(Pos.CENTER);

        cartLabel=new Label("Current Cart:");   //would be changed later
        cartLabel.relocate(530,10); cartLabel.setPrefSize(300,40);
        cartLabel.setAlignment(Pos.CENTER);


        //create and position the textFields(3)
        newItemField1=new TextField("0");
        newItemField1.relocate(115,55); newItemField1.setPrefSize(95,40);

        newItemField2=new TextField("0.00");
        newItemField2.relocate(115,100); newItemField2.setPrefSize(95,40);

        newItemField3=new TextField("N/A");
        newItemField3.relocate(115,145); newItemField3.setPrefSize(95,40);

        //create and position the ListViews(3)
        popularList=new ListView<String>();
        popularList.relocate(10,225); popularList.setPrefSize(200,125);

        stockList=new ListView<String>();
        stockList.relocate(220,55); stockList.setPrefSize(300,295);

        cartList=new ListView<String>();
        cartList.relocate(530,55); cartList.setPrefSize(300,295);

        //create and position the buttons(4)
        resetButton=new Button("Reset Store");
        resetButton.relocate(60,360); resetButton.setPrefSize(100,50);
        resetButton.setAlignment(Pos.CENTER);

        addButton=new Button("Add to Cart");
        addButton.relocate(320,360); addButton.setPrefSize(100,50);
        addButton.setAlignment(Pos.CENTER);

        removeButton=new Button("Remove from Cart");
        removeButton.relocate(530,360); removeButton.setPrefSize(150,50);
        removeButton.setAlignment(Pos.CENTER);

        completeButton=new Button("Complete Sale");
        completeButton.relocate(680,360); completeButton.setPrefSize(150,50);
        completeButton.setAlignment(Pos.CENTER);

        getChildren().addAll(label1,label2,label3,label4,label5,label6,cartLabel,
                newItemField1,newItemField2,newItemField3,popularList,stockList,cartList,
                resetButton,addButton,removeButton,completeButton);

    }

    public void update(ElectronicStore model){
        //create exactStockList(>0) for stock list view(middle-column--2nd column)
        ArrayList<String> list1=new ArrayList<String>();
        for (int i=0;i<model.getExactStock().length;i++){
            if (model.getExactStock()[i].getStockQuantity()>0){     //if the StockQuantity is  bigger than 0, then display it
                list1.add(model.getExactStock()[i].toString());
            }
        }
        String[] exactStockList=list1.toArray(new String[list1.size()]);


        //create exactCartList(>0) for cart list view(3rd-column)
        double price=0;       //each update we would need to calculate the price again. so we set it to be zero
        ArrayList<String> list2=new ArrayList<String>();
        for (int i=0;i<model.getExactStock().length;i++){
            if (model.getExactStock()[i].getCartQuantity()>0){      //if the CartQuantity is  bigger than 0, then display it
                list2.add(model.getExactStock()[i].getCartQuantity()+" x "+model.getExactStock()[i].toString());
                //calculate the price
                price+=(model.getExactStock()[i].getCartQuantity())*(model.getExactStock()[i].getPrice());
            }
        }
        String[] exactCartList=list2.toArray(new String[list2.size()]);


        //show the current cart price
        cartLabel.setText("Current Cart ($"+String.format("%.2f",price)+"):");
        model.setCurCartMoney(price);      //set the cartMoney which might be used later for total revenue

        //show the store summary (3 textFields)
        newItemField1.setText(""+model.getNumOfSales());    //#Sales
        newItemField2.setText(String.format("%.2f",model.getRevenue()));
        if (model.getNumOfSales()>0){
        newItemField3.setText(String.format("%.2f",(model.getRevenue()/model.getNumOfSales())));}
        else{newItemField3.setText("N/A");}

        //stockList
        //ensure that the list does not get unselected in the update method.
        int selectedIndex1 = stockList.getSelectionModel().getSelectedIndex();
        stockList.setItems(FXCollections.observableArrayList(exactStockList));
        stockList.getSelectionModel().select(selectedIndex1);

        //cartList
        //ensure that the list does not get unselected in the update method.
        int selectedIndex2 = cartList.getSelectionModel().getSelectedIndex();
        cartList.setItems(FXCollections.observableArrayList(exactCartList));
        cartList.getSelectionModel().select(selectedIndex2);

        //disable add button and remove button
        removeButton.setDisable(cartList.getSelectionModel().getSelectedIndex() <0);
        addButton.setDisable(stockList.getSelectionModel().getSelectedIndex() <0);

        if (exactCartList.length>0){
            completeButton.setDisable(false);
        }
        else{completeButton.setDisable(true);}


        //show the popular list
        //if we never click the complete button , then we use the exactStock list
        //if we have clicked the complete button, then we would use a newly created popularSorted list
        String[] exactPopularList=new String[3];
        if(model.getNumOfSales()==0){for (int i=0;i<3;i++){
            exactPopularList[i]=model.getExactStock()[i].toString();
        }}
        else{
        for (int i=0;i<3;i++){
            exactPopularList[i]=model.getPopularSortedList()[i].toString();
        }}
        popularList.setItems(FXCollections.observableArrayList(exactPopularList));
    }



}

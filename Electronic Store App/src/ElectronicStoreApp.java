import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.input.MouseEvent;

public class ElectronicStoreApp extends Application{
    private ElectronicStore model;
    private ElectronicStoreView  view;

    //constructor
    public ElectronicStoreApp(){
        model=ElectronicStore.createStore();
        view=new ElectronicStoreView();
    }

    public void start(Stage primaryStage) {
        Pane  aPane = new Pane();
        aPane.getChildren().add(view);
        primaryStage.setTitle(""+model.getName());   //set window title
        primaryStage.setResizable(false);            //make the window non-resizable
        primaryStage.setScene(new Scene(aPane,840,420));
        primaryStage.show();

        view.update(model);

        //event handler-add button
        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
              int index=view.getStockList().getSelectionModel().getSelectedIndex();
              if (index>=0){
                  model.addToCart(view.getStockList().getItems().get(index));  //pass a string
                  view.update(model);
              }
            }
        });

        //event handler-remove button
        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                int index=view.getCartList().getSelectionModel().getSelectedIndex();
                if (index>=0){
                    model.removeFromCart(view.getCartList().getItems().get(index));  //pass a string
                    view.update(model);
                }
            }
        });

        //event handler-complete button
        view.getCompleteButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.complete();        //would get the newly sorted list
                view.update(model);
            }
        });

        //event handler-complete button
        view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model = ElectronicStore.createStore();
                view.update(model);
            }
        });

        //re-able 'remove from cart' button
        view.getCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update(model); } });

        //re-able 'add to cart' button
        view.getStockList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update(model); } });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

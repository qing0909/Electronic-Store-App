1. Install Java
2. Download JavaFX SDK version 17.0.2 from https://gluonhq.com/products/javafx/
3. Extract its contents to a location on your hard-drive. Get the path of the folder (e.g., C:\javafx-sdk-17.0.2 on Windows or /Users/yourname/javafx-sdk-17.0.2 on Mac)

-----------------------------------------------
1. Open the Java project within IntelliJ
2. Go to File -> Project Structure -> Libraries. Click the + sign to add a new library and select Java. Select the 'lib' folder within the JavaFX folder, click OK and then click Apply
3. Go to Run -> Edit Configurations. Select the 'Modify options' dropdown and then select 'Add VM options'. Within the VM options text box, enter the following (remember to update the /path/to/ part): --module-path /path/to/javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml
Click Apply
4. Run ElectronicStoreApp.java
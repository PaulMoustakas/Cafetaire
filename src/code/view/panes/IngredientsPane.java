package code.view.panes;

import code.entities.Ingredient;
import code.entities.Styles;
import code.view.popups.IngredientPopup;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import code.control.Callback;
import javax.swing.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * The class is the Ingredients panel for the Cafetairé application.
 * @author Tor Stenfeldt, Georg Grankvist, Lucas Eliasson
 * @version 1.0
 */
public class IngredientsPane extends StackPane {
    private Spinner<Integer> numberSpinner = new Spinner<>();

    private TableView<Ingredient> tableView;
    private TableColumn<Ingredient, String> nameColumn = new TableColumn<>();
    private TableColumn<Ingredient, String> categoryColumn = new TableColumn<>();
    private TableColumn<Ingredient, Integer> stockColumn = new TableColumn<>();
    private TableColumn<Ingredient, String> supplierColumn = new TableColumn<>();

    private TextField searchTextField;

    private Callback callback;


    public IngredientsPane (Callback callback) {
        this.callback = callback;
        VBox mainContainer = new VBox();
        mainContainer.setMaxSize(1036, 698);

        mainContainer.getChildren().addAll(initTopContainer(), initFlowBottom());
        getChildren().add(mainContainer);

        mainContainer.setAlignment(Pos.CENTER);
        setStyle(Styles.getPane());
        mainContainer.setStyle(Styles.getPane());

        setPrefSize(1086, 768);
    }

    public HBox initUpperHBox () {

        Text textTitle = new Text();
        Font menuTitle = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 24);
        textTitle.setFill(Paint.valueOf("#619f81"));
        textTitle.setFont(menuTitle);
        textTitle.setText("INGREDIENTS");

        HBox hBox = new HBox();
        hBox.setPrefSize(1036, 75);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(textTitle);
        hBox.setStyle("-fx-background-radius: 20 20 0 0;" +
                "-fx-background-color: #FFFFFF;");

        return hBox;
    }

    public HBox initFillerHBox () {
        HBox hBoxFiller = new HBox();
        hBoxFiller.setMinSize(1036, 40);
        hBoxFiller.setMaxSize(1036, 40);
        hBoxFiller.setStyle("-fx-border-color: #6B6C6A; -fx-background-color: #FFFFFF");
        return hBoxFiller;
    }

    public HBox initHBoxLeft() {

        Button button_newIngredient = new Button("ADD INGREDIENT");
        Button button_removeIngredient = new Button("REMOVE");
        Button button_editIngredient = new Button( "EDIT");

        searchTextField = new TextField();
        searchTextField.setPromptText("SEARCH");
        searchTextField.setPrefHeight(32);
        searchTextField.setPrefWidth(150);
        searchTextField.textProperty().addListener(this::searchRecord);

        button_newIngredient.setStyle(Styles.getButton());
        button_editIngredient.setStyle(Styles.getButton());
        button_removeIngredient.setStyle(Styles.getButton());

        HBox hBox = new HBox(15, button_newIngredient, button_removeIngredient, button_editIngredient, searchTextField);
        hBox.setSpacing(10);
        hBox.setMinSize(600, 75);
        hBox.setMaxSize(650, 75);
        hBox.setAlignment(Pos.CENTER_LEFT);

        hBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        button_newIngredient.setOnAction(e -> addNewIngredientAction());
        button_removeIngredient.setOnAction(e -> removeIngredient());
        button_editIngredient.setOnAction(e -> editAction());

        hBox.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 0 50 0 50");

        return hBox;
    }

    public HBox initHBoxRight(){
        Button button_Add = new Button("INCREASE");
        Button button_Remove = new Button("DECREASE");

        final SpinnerValueFactory.IntegerSpinnerValueFactory svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 80);
        numberSpinner.setValueFactory(svf);
        numberSpinner.disabledProperty();
        numberSpinner.setEditable(true);
        numberSpinner.setPrefHeight(38);
        numberSpinner.setPrefWidth(100);

        button_Add.setStyle(Styles.getButton());
        button_Remove.setStyle(Styles.getButton());

        button_Add.setPrefSize(110,30);
        button_Remove.setPrefSize(110,30);

        button_Add.setOnAction(e -> addQuantity());
        button_Remove.setOnAction(e -> removeQuantity());

        HBox hBox = new HBox(15, numberSpinner, button_Add, button_Remove);

        hBox.setMaxSize(435, 75);
        hBox.setMinSize(435, 75);

        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 0 50 0 50;");

        return hBox;
    }

    public HBox initBtnContainer() {
        HBox hBox = new HBox();
        setPrefSize(1036, 75);
        hBox.getChildren().addAll(initHBoxLeft(), initHBoxRight());
        return hBox;
    }

    public FlowPane initFlowBottom() {
        FlowPane pane = new FlowPane();

        pane.setPadding(new Insets(15,15,15,15));

        pane.setMinSize(1036, 508);
        pane.setMaxSize(1036, 508);

        tableView = new TableView<>();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        nameColumn.setPrefWidth(233);
        categoryColumn.setPrefWidth(234);
        stockColumn.setPrefWidth(234);
        supplierColumn.setPrefWidth(234);

        nameColumn.setStyle(Styles.getTableColumn());
        categoryColumn.setStyle(Styles.getTableColumn());
        stockColumn.setStyle(Styles.getTableColumn());
        supplierColumn.setStyle(Styles.getTableColumn());

        tableView.getColumns().addAll(nameColumn, categoryColumn, stockColumn, supplierColumn);

        tableView.setPrefHeight(458);
        tableView.setStyle(Styles.getTableRowSelected());

        pane.setAlignment(Pos.CENTER);

        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.getChildren().add(tableView);

        tableView.setItems(getIngredient());

        pane.setStyle("-fx-alignment: center;" +
                " -fx-background-color: #fff;" +
                " -fx-background-radius: 0 0 20 20;" +
                " -fx-padding: 0 0 50 0;");

        return pane;
    }

    public VBox initTopContainer(){
        VBox vBox =  new VBox(initUpperHBox(),initFillerHBox(), initBtnContainer());
        vBox.setPrefSize(1036, 190);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20 20 0 0;");

        return vBox;
    }

    public int getNumberSpinnerValue() {
        return numberSpinner.getValue();
    }

    /**
     * @param ingredient
     */
    public void addNewIngredient(Ingredient ingredient) {
        tableView.getItems().add(ingredient);
    }

    /**
     * Adds a new ingredient from user input
     */
    public void addNewIngredientAction() {
        try {
            new IngredientPopup(this, callback, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to edit an item in the tableView
     */
    public void editAction() {
        String name = tableView.getSelectionModel().getSelectedItem().getType();
        IngredientPopup pane;

        if (name != null) {
            try {
                pane = new IngredientPopup(this, callback, 1);
                Ingredient ingredient = callback.getIngredient(name);
                pane.setOrgIngredient(name);

                if (ingredient.getSupplier() == null) {
                    pane.setValuesForIngredient(ingredient.getType(), ingredient.getCategory(), "");
                } else {
                    pane.setValuesForIngredient(ingredient.getType(), ingredient.getCategory(), ingredient.getSupplier().getName());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Removes selected ingredient from the stock
     */
    public void removeIngredient() {
        ObservableList<Ingredient> ingredientSelected, allIngredients;
        allIngredients = tableView.getItems();
        ingredientSelected = tableView.getSelectionModel().getSelectedItems();
        code.entities.Ingredient ingredient = tableView.getSelectionModel().getSelectedItem();

        try {
            ingredientSelected.forEach(allIngredients::remove);
            callback.removeIngredient(ingredient.getType());

        }catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Increments the selected ingredients stock by 1
     */
    public void addQuantity() {
        Ingredient ingredient = tableView.getSelectionModel().getSelectedItem();

        if (ingredient != null){
            int prodQuantity = ingredient.getStock();
            ingredient.setStock(prodQuantity + getNumberSpinnerValue());
        } else {
            noIngredientSelected();
        }

        refresh();
    }


    /**
     * Remove quantity from existing product
     */
    public void removeQuantity() {
        Ingredient ingredient = tableView.getSelectionModel().getSelectedItem();

        if (ingredient != null){
            int prodQuantity = ingredient.getStock();
            ingredient.setStock(prodQuantity - getNumberSpinnerValue());
        } else {
            noIngredientSelected();
        }

        tableView.refresh();
    }

    /**
     * Expands the pane and makes the menuPane smaller
     */
    public void expand() {
        setPrefWidth(1346);
        System.out.println("Expanding");
    }

    /**
     * Makes the pane smaller and expands the menuPane
     */
    public void contract() {
        setPrefWidth(1086);
        System.out.println("Contracting");
    }

    /**
     * Searchbar functionality.
     */
    private void searchRecord(Observable observable, String oldValue, String newValue) {
        if (!searchTextField.getText().equals("")) {
            FilteredList<Ingredient> filteredList = new FilteredList<>(getIngredient(), p -> true);
            filteredList.setPredicate(tableView -> {



                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String typedText = newValue.toLowerCase();

                if (tableView.getType().toLowerCase().contains(typedText)) {
                    return true;

                } else if (tableView.getSupplier().getName().toLowerCase().contains(typedText)) {

                    return true;

                } else if (String.valueOf(tableView.getStock()).toLowerCase().contains(typedText))

                    return true;

                else
                    return false;

            });

            SortedList<Ingredient> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedList);
        } else {
            tableView.setItems(getIngredient());
        }
    }

    private ObservableList<Ingredient> getIngredient() {
        ObservableList <Ingredient> ingredients = FXCollections.observableArrayList();
        Ingredient[] receivedIngredients = callback.getIngredients();
        ingredients.addAll(Arrays.asList(receivedIngredients));
        return ingredients;
    }

    public void noIngredientSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Ingredient Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select an ingredient!");

        alert.showAndWait();
    }

    /**
     * Refreshes the tableView
     */
    public void refresh(){
        tableView.refresh();
    }
}

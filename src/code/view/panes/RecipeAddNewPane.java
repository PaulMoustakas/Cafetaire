package code.view.panes;

import code.control.Callback;
import code.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RecipeAddNewPane extends Pane implements EnhancedPane {
    // main functionality
    private Callback callback;
    private VBox mainContainer;
    private Label titleLabel;

    // lis values
    private Label listLabel;
    private TableView<Content> ingredientsList; // Change to tableView with input as columns
    TableColumn<Content, Ingredient> nameCol;
    TableColumn<Content, Double> amountCol;
    TableColumn<Content, Units> unitCol;

    // input
    private ComboBox field_Name;
    private TextField field_Amount;
    private ComboBox field_Unit;
    private Button button_Enter;
    private Label label_Instructions;
    private TextArea field_Instructions;
    private TextField field_RName;
    private TextField field_RAmount;

    // save & cancel
    private Button button_Save;
    private Button button_Cancel;

    // source
    private RecipePane source;
    private RecipeListPane pane;

    private HBox container_list_instr;

    public RecipeAddNewPane(Callback callback, RecipePane source, RecipeListPane pane) {
        this.callback = callback;
        this.source = source;
        this.pane = pane;

        /* Title */
        HBox titleBox = new HBox();
        titleBox.setPrefSize(1160, 58);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle(
                "-fx-background-radius: 20 20 0 0;" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-border-color: #000;"
        );
        Font titleFont = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 24);
        titleLabel = new Label("ADD NEW RECIPE");
        titleLabel.setFont(titleFont);
        titleLabel.setTextFill(Paint.valueOf("#619f81"));
        titleLabel.setPadding(new Insets(0,0,17,0));
        titleBox.getChildren().add(titleLabel);

        /* Recipe Name */
        HBox recipeNameBox = new HBox(10);
        recipeNameBox.setPrefSize(1014, 40);
        recipeNameBox.setStyle(
                "-fx-background-color: #fff;" + "\n" +
                "-fx-padding: 30, 0, 0, 0"
        );
        Label label_RecipeName = new Label("Recipe name: ");
        field_RName = new TextField();
        field_RName.setPromptText("Recipe name");
        field_RName.setPrefSize(220,40);
        recipeNameBox.getChildren().addAll(label_RecipeName, field_RName);

        /* Recipe amount */
        HBox recipeAmountBox = new HBox(10);
        recipeNameBox.setPrefSize(1014, 40);
        recipeNameBox.setStyle(
                "-fx-background-color: #fff;" + "\n" +
                "-fx-padding: 30, 600, 0, 0"
        );
        recipeNameBox.setAlignment(Pos.CENTER_LEFT);
        Label label_RecipeAmount = new Label("Servings: ");
        field_RAmount = new TextField();
        field_RAmount.setPromptText("E.g. 24");
        field_RAmount.setPrefSize(100,40);
        field_RAmount.getProperties().put("vktype", "numeric");
        recipeNameBox.getChildren().addAll(label_RecipeAmount, field_RAmount);

        /* List view */
        VBox listBox = new VBox(10);
        listBox.setPrefSize(480,200);
        listBox.setAlignment(Pos.CENTER);

        ingredientsList = new TableView<>();
        ingredientsList.setPrefSize(460,200);
        ingredientsList.getStyleClass().add("list");
        ingredientsList.setEditable(false);

        nameCol = new TableColumn<>();
        nameCol.setPrefWidth(248);
        nameCol.setText("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
        amountCol = new TableColumn<>();
        amountCol.setPrefWidth(115);
        amountCol.setText("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        unitCol  = new TableColumn<>();
        unitCol.setPrefWidth(115);
        unitCol.setText("Unit");
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));

        ingredientsList.getColumns().addAll(nameCol, amountCol, unitCol);

        listLabel = new Label("Added ingredients");
        listBox.getChildren().addAll(listLabel, ingredientsList);

        /* Instructions view */ // -----> Move
        VBox instructionsBox = new VBox(10);
        instructionsBox.setPrefSize(480,200);
        instructionsBox.setAlignment(Pos.CENTER);
        field_Instructions = new TextArea();
        field_Instructions.setPrefSize(460,200);
        field_Instructions.getStyleClass().add("text-field");
        field_Instructions.setWrapText(true);
        label_Instructions = new Label("Enter instructions");
        instructionsBox.getChildren().addAll(label_Instructions, field_Instructions);

        /* List + Instructions */
        container_list_instr = new HBox(20);
        container_list_instr.setPrefSize(1014,200);
        container_list_instr.setAlignment(Pos.CENTER);
        container_list_instr.getChildren().addAll(listBox, instructionsBox);

        /* TextFields */
        HBox fieldBox = new HBox(20);
        fieldBox.setPrefSize(1014,100);
        fieldBox.setAlignment(Pos.CENTER);

        field_Name = new ComboBox();
        field_Name.setPrefSize(200,30);
        field_Name.setEditable(true);
        field_Name.setPromptText("Name");
        field_Name.getStyleClass().add("text-field");
        field_Name.setItems(loadIngredients());

        field_Amount = new TextField();
        field_Amount.setPrefSize(100,30);
        field_Amount.setEditable(true);
        field_Amount.setPromptText("Amount");
        field_Amount.getStyleClass().add("text-field");

        field_Unit = new ComboBox();
        field_Unit.setPrefSize(100,30);
        field_Unit.setEditable(false);
        field_Unit.setItems(getUnits());
        field_Unit.setPromptText("Unit");
        field_Unit.getStyleClass().add("text-field");
        field_Unit.setButtonCell(new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(!(empty || item==null)){
                    setText(item.toString());
                }
            }
        });

        button_Enter = new Button("ADD");
        button_Enter.setPrefSize(100,30);
        button_Enter.getStyleClass().add("greenButton");
        button_Enter.setOnAction(e -> addIngredient());

        fieldBox.getChildren().addAll(field_Name, field_Amount, field_Unit,  button_Enter);

        // TODO: Add more ?

        /* Space box */
        HBox spacing_addAndButtons = new HBox();
        spacing_addAndButtons.setStyle(
                "-fx-background-color: #fff;"
        );
        spacing_addAndButtons.setPrefSize(1014, 120);

        /* buttons */
        HBox buttonBox = new HBox(20);
        buttonBox.setPrefSize(1086,60);
        buttonBox.setAlignment(Pos.CENTER);

        button_Save = new Button("SAVE");
        button_Save.setPrefSize(200,60);
        button_Save.getStyleClass().add("bigGreenButton");
        button_Save.setOnAction(e -> saveRecipe());

        button_Cancel = new Button("CANCEL");
        button_Cancel.setPrefSize(200,60);
        button_Cancel.getStyleClass().add("bigGrayButton");
        button_Cancel.setOnAction(e -> goBack());

        buttonBox.getChildren().addAll(button_Save, button_Cancel);

        /* Bottom container */
        HBox bottomSpacing = new HBox();
        bottomSpacing.setStyle(
                "-fx-background-color: #fff;" +
                "-fx-background-radius: 25, 25, 25, 25"
        );
        bottomSpacing.setPrefSize(1014, 40);

        /* Container */
        this.mainContainer = new VBox();
        this.mainContainer.setPrefSize(1014,695);
        this.mainContainer.setStyle(
                "-fx-background-color: #fff;" +
                "-fx-background-radius: 20"
        );
        this.mainContainer.setLayoutX(20);
        this.mainContainer.setLayoutY(20);
        this.mainContainer.getChildren().addAll(titleBox, recipeNameBox, recipeAmountBox, this.container_list_instr, fieldBox, spacing_addAndButtons, buttonBox, bottomSpacing);

        this.setPrefSize(1054,736);
        this.setStyle("-fx-background-color: #6B6C6A;");
        this.getStylesheets().add("styles.css");
        this.getChildren().add(this.mainContainer);
    }

    @Override
    public void expand() {
        this.setPrefWidth(1200);
        this.mainContainer.setPrefWidth(1160);
    }

    @Override
    public void contract() {
        this.setPrefWidth(1160);
        this.mainContainer.setPrefWidth(1014);
    }

    @Override
    public void refresh() {
        ingredientsList.refresh();
        field_Name.setItems(loadIngredients());
        if (this.callback.getExpanded()) {
            contract();
        } else {
            expand();
        }
    }

    private void openKeyboard() {
        try {
            Runtime.getRuntime().exec("cmd /c osk");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets already stored ingredients and adds them to the name combobox
     * @return List of strings
     */
    private ObservableList<String> loadIngredients() {
        ObservableList <String> strings = FXCollections.observableArrayList();
        Ingredient[] collectedIngredients = callback.getIngredients();
        String[] newStrings = new String[collectedIngredients.length];
        for (int i = 0; i < collectedIngredients.length; i++) {
            newStrings[i] = collectedIngredients[i].getType();
        }
        strings.addAll(Arrays.asList(newStrings));
        return strings;
    }

    /**
     * Saves the Recipe to the database
     */
    private void saveRecipe() {
        String name, instructions;
        double amount;
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Recipe recipe;
        Ingredient ingredient;
        Content content;

        name = field_RName.getText();
        instructions = field_Instructions.getText();
        try {
            amount = Double.parseDouble(field_RAmount.getText());
        } catch (Exception e) {
            amount = 0;
        }

        for (Content c: ingredientsList.getItems()) {
            ingredient = new Ingredient(c.getIngredient().getType());
            if (!callback.addIngredient(ingredient)) {
                ingredients.add(ingredient);
            } else {
                System.out.println("Create new");
                callback.addIngredient(ingredient);
                ingredients.add(ingredient);
            }
        }

        if (name.equals("") || amount <= 0 || ingredients.size() <= 0 || instructions.equals("")) {
            Alert insufficientIngredientsAlert = new Alert(Alert.AlertType.ERROR);
            insufficientIngredientsAlert.setTitle("Error Dialog");
            insufficientIngredientsAlert.setHeaderText("Input error");
            insufficientIngredientsAlert.setContentText("Ooops, make sure all fields are filled in correctly!");
            insufficientIngredientsAlert.showAndWait();
        } else {
            recipe = new Recipe(name);
            for (Content c : ingredientsList.getItems()) {
                content = c;
                recipe.addContent(content);
            }
            recipe.setInstructions(instructions);
            recipe.setAmount(amount);
            System.out.println();

            System.out.println("Name: " + recipe.getName());
            System.out.println("Instructions: " + recipe.getInstructions());
            System.out.println("Ingredients: " + recipe.toString());

            pane.addNewRecipe(recipe);
            callback.addRecipe(recipe);
            goBack();
        }
    }

    /**
     * Adds ingredient to the tableView
     */
    private void addIngredient() {
        String name = "";
        Units unit = null;
        double amount = 0;
        Ingredient ingredient; // To check against the database

        try {
            name = (String) field_Name.getValue();
            amount = Double.parseDouble((String) field_Amount.getText());
            unit = Units.valueOf((String) field_Unit.getValue());

            if (!(name.equals("") || amount == 0.0)) {
                System.out.println("Added Ingredient: \n" +
                        "Name: " + name + "\n" +
                        "Amount: " + amount + " " + unit);
                clearInput();
            } else {
                JOptionPane.showMessageDialog(null, "Please choose an unit");
            }

        } catch (Exception e) {
            System.out.println("Please enter a correct amount");
        }

        ingredient = new Ingredient(name);

        // if ingredient exist, use it, else create new
        if (!callback.addIngredient(ingredient)) {
            System.out.println("Already exist");
            ingredient = callback.getIngredient(ingredient.getType());
        } else {
            System.out.println("Created new ingredient: " + ingredient.getType());
        }

        Content content = new Content(ingredient, amount, unit);
        ingredientsList.getItems().add(content);
    }

    /**
     * Clear the input comboboxes
     */
    private void clearInput() {
        field_Name.setValue("");
        field_Amount.setText("");
        field_Unit.setValue("Unit");
    }

    /**
     * Return to the landing page
     */
    private void goBack() {
        clearInput();
        ingredientsList.getItems().clear();
        field_Instructions.setText("");
        field_RName.setText("");
        source.setView(RecipePanes.RecipeListPane);
    }

    /**
     * Returns an array of units
     * @return ObservableList of units
     */
    private ObservableList<String> getUnits() {
        ObservableList<String> unitList = FXCollections.observableArrayList();
        for (Units u: Units.values()) {
            unitList.add(u.name());
        }
        return unitList;
    }
}

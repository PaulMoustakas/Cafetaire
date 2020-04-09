package Control;

import Entities.Food;
import Entities.Ingredient;
import Entities.Supplier;
import Model.Database;
import View.MainPane;

/**
 * Instantiates the database and the mainFrame and holds the class handling callback between them.
 * @author Tor Stenfeldt
 * @version 2.0
 */
public class Controller {
    private Database database;
    private MainPane mainPane;

    public Controller() {
        this.database = new Database();

        // TODO: must comment away for testing purposes?
        //this.mainPane = new MainPane(new CallbackHandler());
    }

    public MainPane getMainPane() {
        return mainPane;
    }

    public CallbackHandler getCallback() {
        return new CallbackHandler();
    }

    /**
     * Implements Callback in order to receive data from Frames and send to the Database.
     */
    private class CallbackHandler implements Callback {
        @Override
        public boolean addIngredient(Ingredient ingredient) {
            return database.addIngredient(ingredient);
        }

        @Override
        public Ingredient getIngredient(String ingredient) {
            return database.getIngredient(ingredient);
        }

        @Override
        public Ingredient[] getIngredients() {
            return database.getIngredients();
        }

        @Override
        public int getNumIngredients(String ingredient) {
            return database.getNumIngredients(ingredient);
        }

        @Override
        public boolean increaseIngredient(String ingredient) {
            return database.increaseIngredient(ingredient);
        }

        @Override
        public boolean increaseIngredient(Ingredient ingredient) {
            return database.increaseIngredient(ingredient);
        }

        @Override
        public boolean decreaseIngredient(String ingredient) {
            return database.decreaseIngredient(ingredient);
        }

        @Override
        public boolean decreaseIngredient(Ingredient ingredient) {
            return database.decreaseIngredient(ingredient);
        }

        @Override
        public boolean removeIngredient(String ingredient) {
            return database.removeIngredient(ingredient);
        }

        @Override
        public boolean addFood(Food food) {
            return database.addFood(food);
        }

        @Override
        public Food getFood(String food) {
            return database.getFood(food);
        }

        @Override
        public Food[] getFood() {
            return database.getFood();
        }

        @Override
        public int getNumFood(String food) {
            return database.getNumFood(food);
        }

        @Override
        public boolean increaseFood(String food) {
            return database.increaseFood(food);
        }

        @Override
        public boolean increaseFood(Food food) {
            return database.increaseFood(food);
        }

        @Override
        public boolean decreaseFood(String food) {
            return database.decreaseFood(food);
        }

        @Override
        public boolean decreaseFood(Food food) {
            return database.decreaseFood(food);
        }

        @Override
        public boolean removeFood(String food) {
            return database.removeFood(food);
        }

        @Override
        public boolean addSupplier(Supplier supplier) {
            return database.addSupplier(supplier);
        }

        @Override
        public boolean addSupplier(String name) {
            return database.addSupplier(name);
        }

        @Override
        public Supplier getSupplier(String supplier) {
            return database.getSupplier(supplier);
        }

        @Override
        public Supplier[] getSuppliers() {
            return database.getSuppliers();
        }

        @Override
        public int getNumSuppliers() {
            return database.getNumSuppliers();
        }

        @Override
        public boolean removeSupplier(String name) {
            return database.removeSupplier(name);
        }
    }
}

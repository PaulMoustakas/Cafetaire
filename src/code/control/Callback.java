package code.control;

import code.entities.*;

import java.util.ArrayList;

/**
 * Used for callback functionality between the Controller and the different Frames.
 * @author Tor Stenfeldt
 * @version 2.0
 */
@SuppressWarnings("unused")
public interface Callback {
    boolean addIngredient(Ingredient ingredient);
    Ingredient getIngredient(String ingredient);
    Ingredient[] getIngredients();
    int getNumIngredients(String ingredient);
    boolean increaseIngredient(String ingredient);
    boolean increaseIngredient(Ingredient ingredient);
    boolean decreaseIngredient(String ingredient);
    boolean decreaseIngredient(Ingredient ingredient);
    boolean removeIngredient(String ingredient);

    boolean addFood(Food food);
    Food getFood(String food);
    Food[] getFood();
    int getNumFood(String food);
    boolean increaseFood(String food);
    boolean increaseFood(Food food);
    boolean decreaseFood(String food);
    boolean decreaseFood(Food food);
    boolean removeFood(String food);

    boolean addSupplier (Supplier supplier);
    boolean addSupplier (String name);
    Supplier getSupplier(String supplier);
    ArrayList<Supplier> getSuppliers();
    int getNumSuppliers();
    boolean removeSupplier (String name);

    /* Testing purposes */
    boolean addIngredientTest(IngredientTest ingredient);
    IngredientTest getIngredientTest(String ingredient);
    IngredientTest[] getIngredientsTest();
    int getNumIngredientsTest(String ingredient);
    boolean increaseIngredientTest(String ingredient);
    boolean increaseIngredientTest(IngredientTest ingredient);
    boolean decreaseIngredientTest(String ingredient);
    boolean decreaseIngredientTest(IngredientTest ingredient);
    boolean removeIngredientTest(String ingredient);

    boolean addProductTest(Product product);
    Product getProductTest(String product);
    Product[] getProductTest();
    int getNumProductTest(String product);
    boolean increaseProductTest(String product);
    boolean increaseProductTest(Product product);
    boolean decreaseProductTest(String product);
    boolean decreaseProductTest(Product  product);
    boolean removeProductTest(String name);
}
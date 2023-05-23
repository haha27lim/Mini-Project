package sg.edu.nus.iss.miniproject.models;


import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonStructure;
import jakarta.json.JsonValue;
import jakarta.json.JsonValue.ValueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeNutrients {
    
    public int id;
    public String title;
    public String image;
    public int calories;
    public String carbs;
    public String fat;
    public String protein;


    public JsonObject toJSON() {

        return Json.createObjectBuilder()
        .add("id", getId())
        .add("title", getTitle())
        .add("image", getImage())
        .add("calories", getCalories())
        .add("carbs", getCarbs())
        .add("fat", getFat())
        .add("protein", getProtein())
        .build();
    }
    
    public static RecipeNutrients toRecipeNutrients(JsonObject obj) {
        RecipeNutrients recipeNutrients = new RecipeNutrients();
        recipeNutrients.setId(obj.getInt("id"));
        recipeNutrients.setTitle(obj.getString("title"));
    
        if (obj.containsKey("image")) {
            JsonString imageJson = obj.getJsonString("image");
            String image = imageJson.getString();
            recipeNutrients.setImage(image);
        }
    
        int calories = obj.containsKey("calories") ? obj.getJsonNumber("calories").intValue() : 0;
        recipeNutrients.setCalories(calories);
        
        recipeNutrients.setCarbs(obj.getString("carbs"));
        recipeNutrients.setFat(obj.getString("fat"));
        recipeNutrients.setProtein(obj.getString("protein"));

        
        return recipeNutrients;
    }
    
}

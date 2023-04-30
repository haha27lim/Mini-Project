package sg.edu.nus.iss.miniproject.models;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String image;
    private String sourceUrl;
    private String spoonacularSourceUrl;
    private List<String> cuisines;
    private List<String> dishTypes;
    private List<String> diets;
    private String instructions;
    private List<ExtendedIngredients> extendedIngredients;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExtendedIngredients {
        private String originalName;
        private double amount;
        private String unitShort;
    }

    public JsonObject toJSON() {
        JsonArrayBuilder ingredientsArrayBuilder = Json.createArrayBuilder();
        for (ExtendedIngredients ingredient : getExtendedIngredients()) {
            JsonObjectBuilder ingredientBuilder = Json.createObjectBuilder()
                    .add("originalName", ingredient.getOriginalName())
                    .add("amount", ingredient.getAmount())
                    .add("unitShort", ingredient.getUnitShort());
            ingredientsArrayBuilder.add(ingredientBuilder.build());
        }

        return Json.createObjectBuilder()
            .add("id", getId())
            .add("title", getTitle())
            .add("readyInMinutes", getReadyInMinutes())
            .add("servings", getServings())
            .add("image", getImage())
            .add("sourceUrl", getSourceUrl())
            .add("spoonacularSourceUrl", getSpoonacularSourceUrl())
            .add("instructions", getInstructions())
            .add("dishTypes", Json.createArrayBuilder(getDishTypes()))
            .add("cuisines", Json.createArrayBuilder(getCuisines()))
            .add("diets", Json.createArrayBuilder(getDiets()))
            .add("extendedIngredients", ingredientsArrayBuilder.build())
            .build();
    }

    public static Recipe toRecipe (JsonObject obj) {
        Recipe recipe = new Recipe();
        recipe.setId(obj.getInt("id"));
        recipe.setTitle(obj.getString("title"));
        recipe.setReadyInMinutes(obj.getInt("readyInMinutes"));
        recipe.setServings(obj.getInt("servings"));
        recipe.setImage(obj.getString("image"));
        recipe.setSourceUrl(obj.getString("sourceUrl"));
        recipe.setSpoonacularSourceUrl(obj.getString("spoonacularSourceUrl"));
        recipe.setInstructions(obj.getString("instructions"));
        List<String> dishTypes = obj.getJsonArray("dishTypes").getValuesAs(JsonString.class)
            .stream()
            .map(JsonString::getString)
            .collect(Collectors.toList());
        recipe.setDishTypes(dishTypes);
        List<String> cuisines = obj.getJsonArray("cuisines").getValuesAs(JsonString.class)
            .stream()
            .map(JsonString::getString)
            .collect(Collectors.toList());
        recipe.setCuisines(cuisines);
        List<String> diets = obj.getJsonArray("diets").getValuesAs(JsonString.class)
            .stream()
            .map(JsonString::getString)
            .collect(Collectors.toList());
        recipe.setDiets(diets);

        List<ExtendedIngredients> extendedIngredients = obj.getJsonArray("extendedIngredients")
                .getValuesAs(JsonObject.class).stream()
                .map(jsonIngredient -> {
                    String originalName = jsonIngredient.getString("originalName");
                    double amount = jsonIngredient.getJsonObject("measures").getJsonObject("metric")
                            .getJsonNumber("amount").doubleValue();
                    String unitShort = jsonIngredient.getJsonObject("measures").getJsonObject("metric")
                            .getString("unitShort");
                    return new ExtendedIngredients(originalName, amount, unitShort);
                })
                .collect(Collectors.toList());
        recipe.setExtendedIngredients(extendedIngredients);

        return recipe;
    }

    
}

package sg.edu.nus.iss.miniproject.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
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
        private String unit;
    }

    public JsonObject toJSON() {
        JsonArrayBuilder ingredientsArrayBuilder = Json.createArrayBuilder();
        for (ExtendedIngredients ingredient : getExtendedIngredients()) {
            JsonObjectBuilder ingredientBuilder = Json.createObjectBuilder()
                    .add("originalName", ingredient.getOriginalName())
                    .add("amount", ingredient.getAmount())
                    .add("unit", ingredient.getUnit());
            ingredientsArrayBuilder.add(ingredientBuilder.build());
        }

        return Json.createObjectBuilder()
            .add("id", getId())
            .add("title", getTitle())
            .add("readyInMinutes", getReadyInMinutes())
            .add("servings", getServings())
            .add("image", getImage())
            .add("sourceUrl", getSourceUrl() != null ? getSourceUrl() : "")
            .add("spoonacularSourceUrl", getSpoonacularSourceUrl() != null ? getSpoonacularSourceUrl() : "")
            .add("instructions", getInstructions() != null ? getInstructions() : "")
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
        int readyInMinutes = obj.getJsonNumber("readyInMinutes") != null ? obj.getJsonNumber("readyInMinutes").intValue() : 0;
        recipe.setReadyInMinutes(readyInMinutes);
        int servings = obj.getJsonNumber("servings") != null ? obj.getJsonNumber("servings").intValue() : 0;
        recipe.setServings(servings);
        recipe.setImage(obj.getString("image"));
        JsonString sourceUrlJson = obj.getJsonString("sourceUrl");
        if (sourceUrlJson != null) {
            recipe.setSourceUrl(sourceUrlJson.getString());
        }
        JsonString spoonacularSourceUrlJson = obj.getJsonString("spoonacularSourceUrl");
        if (spoonacularSourceUrlJson != null) {
            recipe.setSpoonacularSourceUrl(spoonacularSourceUrlJson.getString());
        }
        JsonValue instructionsJson = obj.get("instructions");
        if (instructionsJson != null && !JsonValue.NULL.equals(instructionsJson)) {
            JsonString instructionsString = (JsonString) instructionsJson;
            String instructions = instructionsString.getString();
            recipe.setInstructions(instructions);
        }
        List<String> dishTypes = new ArrayList<>();
        JsonArray dishTypesJsonArray = obj.getJsonArray("dishTypes");
        if (dishTypesJsonArray != null) {
            dishTypes = dishTypesJsonArray.getValuesAs(JsonString.class)
                    .stream()
                    .map(JsonString::getString)
                    .collect(Collectors.toList());
        }
        recipe.setDishTypes(dishTypes);
        JsonArray cuisinesJson = obj.getJsonArray("cuisines");
        List<String> cuisines = cuisinesJson != null
                ? cuisinesJson.getValuesAs(JsonString.class)
                        .stream()
                        .map(JsonString::getString)
                        .collect(Collectors.toList())
                : new ArrayList<>();
        recipe.setCuisines(cuisines);

        JsonArray dietsJson = obj.getJsonArray("diets");
        List<String> diets = dietsJson != null
                ? dietsJson.getValuesAs(JsonString.class)
                        .stream()
                        .map(JsonString::getString)
                        .collect(Collectors.toList())
                : new ArrayList<>();
        recipe.setDiets(diets);

        if (obj.containsKey("extendedIngredients")) {
            List<ExtendedIngredients> extendedIngredients = obj.getJsonArray("extendedIngredients")
                .getValuesAs(JsonObject.class).stream()
                .map(jsonIngredient -> {
                    String originalName = jsonIngredient.getString("originalName");
                    double amount = jsonIngredient.getJsonNumber("amount").doubleValue();
                    String unit = jsonIngredient.getString("unit");
                    return new ExtendedIngredients(originalName, amount, unit);
                })
                .collect(Collectors.toList());
            recipe.setExtendedIngredients(extendedIngredients);
        } else {
            recipe.setExtendedIngredients(Collections.emptyList());
        }

        return recipe;
    }

    
}

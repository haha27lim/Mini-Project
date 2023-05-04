package sg.edu.nus.iss.miniproject.models;

import java.util.Collections;
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
public class RecipeIngredient {
    
    public int id;
    public String title;
    public String image;
    public int usedIngredientCount;
    public int missedIngredientCount;
    public int likes;
    public List<MissedIngredients> missedIngredients;
    public List<UsedIngredients> usedIngredients;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MissedIngredients {
        public String originalName;
        public String image;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UsedIngredients {
        public String originalName;
        public String image;
    }

    public JsonObject toJSON() {
        JsonArrayBuilder ingredientsArrayBuilder = Json.createArrayBuilder();
        for (MissedIngredients ingredient : getMissedIngredients()) {
            JsonObjectBuilder ingredientBuilder = Json.createObjectBuilder()
                    .add("originalName", ingredient.getOriginalName())
                    .add("image", ingredient.getImage());
            ingredientsArrayBuilder.add(ingredientBuilder.build());
        }

        JsonArrayBuilder usedIngredientsArrayBuilder = Json.createArrayBuilder();
        for (UsedIngredients ingredient : getUsedIngredients()) {
            JsonObjectBuilder ingredientBuilder = Json.createObjectBuilder()
                    .add("originalName", ingredient.getOriginalName())
                    .add("image", ingredient.getImage());
            usedIngredientsArrayBuilder.add(ingredientBuilder.build());
        }
        
        return Json.createObjectBuilder()
        .add("id", getId())
        .add("title", getTitle())
        .add("image", getImage())
        .add("usedIngredientCount", getUsedIngredientCount())
        .add("missedIngredientCount", getMissedIngredientCount())
        .add("likes", getLikes())
        .add("missedIngredients", ingredientsArrayBuilder.build())
        .add("usedIngredients", usedIngredientsArrayBuilder.build())
        .build();
    }

    public static RecipeIngredient toRecipeIngredient (JsonObject obj) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(obj.getInt("id"));
        recipeIngredient.setTitle(obj.getString("title"));
        JsonString imageJson = obj.getJsonString("image");
        if (imageJson != null) {
            String image = imageJson.getString();
            recipeIngredient.setImage(image);
        }
        int usedIngredientCount = obj.getJsonNumber("usedIngredientCount") != null ? obj.getJsonNumber("usedIngredientCount").intValue() : 0;
        recipeIngredient.setUsedIngredientCount(usedIngredientCount);
        int missedIngredientCount = obj.getJsonNumber("missedIngredientCount") != null ? obj.getJsonNumber("missedIngredientCount").intValue() : 0;
        recipeIngredient.setMissedIngredientCount(missedIngredientCount);
        int likes = obj.getJsonNumber("likes") != null ? obj.getJsonNumber("likes").intValue() : 0;
        recipeIngredient.setLikes(likes);

        if (obj.containsKey("missedIngredients")) {
            List<MissedIngredients> missedIngredients = obj.getJsonArray("missedIngredients")
                .getValuesAs(JsonObject.class).stream()
                .map(jsonIngredient -> {
                    String originalName = jsonIngredient.getString("originalName");
                    String image = jsonIngredient.getString("image");
                    return new MissedIngredients(originalName, image);
                })
                .collect(Collectors.toList());
            recipeIngredient.setMissedIngredients(missedIngredients);
        } else {
            recipeIngredient.setMissedIngredients(Collections.emptyList());
        }

        if (obj.containsKey("usedIngredients")) {
            List<UsedIngredients> usedIngredients = obj.getJsonArray("usedIngredients")
                .getValuesAs(JsonObject.class).stream()
                .map(jsonIngredient -> {
                    String originalName = jsonIngredient.getString("originalName");
                    String image = jsonIngredient.getString("image");
                    return new UsedIngredients(originalName, image);
                })
                .collect(Collectors.toList());
            recipeIngredient.setUsedIngredients(usedIngredients);
        } else {
            recipeIngredient.setUsedIngredients(Collections.emptyList());
        }
        
        return recipeIngredient;
    }

}

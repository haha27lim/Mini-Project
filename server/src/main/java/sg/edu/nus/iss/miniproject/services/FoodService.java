package sg.edu.nus.iss.miniproject.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.miniproject.models.Recipe;
import sg.edu.nus.iss.miniproject.models.RecipeIngredient;

@Service
public class FoodService {
    
    private static final String BASE_URL = "https://api.spoonacular.com/recipes/";
    private static final String COMPLEX_SEARCH = "complexSearch";
    private static final String RANDOM = "random";
    private static final String FINDINGREDIENTS = "findByIngredients";

	@Value("${apikey}")
    private String API_KEY;

    public List<Recipe> searchGenRecipes(String query, boolean recipeinfo, int number) {

		String url = UriComponentsBuilder
						.fromUriString(BASE_URL + COMPLEX_SEARCH)
						.queryParam("query", query.replaceAll(" ", "+"))
                        .queryParam("addRecipeInformation", recipeinfo)
                        .queryParam("number", number)
                        .queryParam("apiKey", API_KEY)
						.toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
				
        RestTemplate template = new RestTemplate();
		ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }

		String payload = resp.getBody();
		System.out.println("Payload: " + payload);

		
		JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject recResp = reader.readObject();
		if (recResp.isNull("results")) {
			return new LinkedList<Recipe>();
		}
		JsonArray jsonArr = recResp.getJsonArray("results");

        return jsonArr.stream()
                .map(v -> v.asJsonObject())
                .map(Recipe::toRecipe)
                .toList();
	}

	public List<Recipe> searchRecipes(String query, String cuisine, String diet, String excludeIngredients,
     boolean recipeinfo, int number) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL + COMPLEX_SEARCH)
                .queryParam("query", query.replaceAll(" ", "+"))
                .queryParam("cuisine", cuisine)
                .queryParam("addRecipeInformation", recipeinfo)
                .queryParam("number", number)
                .queryParam("apiKey", API_KEY);

        if (diet != null) {
            builder.queryParam("diet", diet);
        }
        if (excludeIngredients != null) {
            builder.queryParam("excludeIngredients", excludeIngredients);
        }

        String url = builder.toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
				
        RestTemplate template = new RestTemplate();
		ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }

		String payload = resp.getBody();
		System.out.println("Payload: " + payload);

		
		JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject recResp = reader.readObject();
		if (recResp.isNull("results")) {
			return new LinkedList<Recipe>();
		}
		JsonArray jsonArr = recResp.getJsonArray("results");

        return jsonArr.stream()
                .map(v -> v.asJsonObject())
                .map(Recipe::toRecipe)
                .toList();
	}

    public List<Recipe> getRandomRecipes(int number) {
        String url = UriComponentsBuilder
                        .fromUriString(BASE_URL + RANDOM)
                        .queryParam("number", number)
                        .queryParam("apiKey", API_KEY)
                        .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
				
        RestTemplate template = new RestTemplate();
		ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }

		String payload = resp.getBody();
		System.out.println("Payload: " + payload);

        JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject recResp = reader.readObject();
		if (recResp.isNull("recipes")) {
			return new LinkedList<Recipe>();
		}
		JsonArray jsonArr = recResp.getJsonArray("recipes");
        System.out.println("Unknown: " + jsonArr);
        return jsonArr.stream()
                .map(v -> v.asJsonObject())
                .map(Recipe::toRecipe)
                .toList();
    }

    public List<Recipe> getRandomRecipesTags(int number, String tags) {
        String url = UriComponentsBuilder
                        .fromUriString(BASE_URL + RANDOM)
                        .queryParam("number", number)
                        .queryParam("tags", tags)
                        .queryParam("apiKey", API_KEY)
                        .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
				
        RestTemplate template = new RestTemplate();
		ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }

		String payload = resp.getBody();
		System.out.println("Payload: " + payload);

        JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject recResp = reader.readObject();
		if (recResp.isNull("recipes")) {
			return new LinkedList<Recipe>();
		}
		JsonArray jsonArr = recResp.getJsonArray("recipes");
        System.out.println("Unknown: " + jsonArr);
        return jsonArr.stream()
                .map(v -> v.asJsonObject())
                .map(Recipe::toRecipe)
                .toList();
    }

    public Recipe getRecipeById(int id) throws IOException {
        String url = UriComponentsBuilder
                .fromUriString(BASE_URL + "/{id}/information")
                .queryParam("apiKey", API_KEY)
                .buildAndExpand(id).toUriString();
        System.out.println("url > " + url);
    
        RequestEntity<Void> req = RequestEntity.get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        
        try {
            resp = template.exchange(req, String.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
        }

        String payload = resp.getBody();
        System.out.println("Payload: " + payload);

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject recResp = reader.readObject();
        Recipe recipe = Recipe.toRecipe(recResp);

        return recipe;
    }

    public List<Recipe> searchRecipesType(String type, boolean recipeinfo, int number) throws IOException {

		String url = UriComponentsBuilder
						.fromUriString(BASE_URL + COMPLEX_SEARCH)
                        .queryParam("type", type)
                        .queryParam("addRecipeInformation", recipeinfo)
                        .queryParam("number", number)
                        .queryParam("apiKey", API_KEY)
						.toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
				
        RestTemplate template = new RestTemplate();
		ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }

		String payload = resp.getBody();
		System.out.println("Payload: " + payload);

		
		JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject recResp = reader.readObject();
		if (recResp.isNull("results")) {
			return new LinkedList<Recipe>();
		}
		JsonArray jsonArr = recResp.getJsonArray("results");

        return jsonArr.stream()
                .map(v -> v.asJsonObject())
                .map(Recipe::toRecipe)
                .toList();
	}

    public List<Recipe> searchRecipesCuisine(String cuisine, boolean recipeinfo, int number) throws IOException {

		String url = UriComponentsBuilder
						.fromUriString(BASE_URL + COMPLEX_SEARCH)
                        .queryParam("cuisine", cuisine)
                        .queryParam("addRecipeInformation", recipeinfo)
                        .queryParam("number", number)
                        .queryParam("apiKey", API_KEY)
						.toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
				
        RestTemplate template = new RestTemplate();
		ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }

		String payload = resp.getBody();
		System.out.println("Payload: " + payload);

		
		JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject recResp = reader.readObject();
		if (recResp.isNull("results")) {
			return new LinkedList<Recipe>();
		}
		JsonArray jsonArr = recResp.getJsonArray("results");

        return jsonArr.stream()
                .map(v -> v.asJsonObject())
                .map(Recipe::toRecipe)
                .toList();
	}

    public List<Recipe> searchRecipesDiet(String diet, boolean recipeinfo, int number) throws IOException {

		String url = UriComponentsBuilder
						.fromUriString(BASE_URL + COMPLEX_SEARCH)
                        .queryParam("diet", diet)
                        .queryParam("addRecipeInformation", recipeinfo)
                        .queryParam("number", number)
                        .queryParam("apiKey", API_KEY)
						.toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
				
        RestTemplate template = new RestTemplate();
		ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }

		String payload = resp.getBody();
		System.out.println("Payload: " + payload);

		
		JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject recResp = reader.readObject();
		if (recResp.isNull("results")) {
			return new LinkedList<Recipe>();
		}
		JsonArray jsonArr = recResp.getJsonArray("results");

        return jsonArr.stream()
                .map(v -> v.asJsonObject())
                .map(Recipe::toRecipe)
                .toList();
	}

    public List<RecipeIngredient> searchRecipesIngredients(String ingredients, int ranking, int number)
            throws IOException {

        String url = UriComponentsBuilder
                .fromUriString(BASE_URL + FINDINGREDIENTS)
                .queryParam("ingredients", ingredients.replaceAll(" ", "+"))
                .queryParam("ranking", ranking)
                .queryParam("number", number)
                .queryParam("apiKey", API_KEY)
                .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }

        String payload = resp.getBody();
        System.out.println("Payload: " + payload);

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray jsonArr = reader.readArray();

        return jsonArr.stream()
                .map(v -> v.asJsonObject())
                .map(RecipeIngredient::toRecipeIngredient)
                .toList();
    }

    public String toJsonArray(List<RecipeIngredient> recipeIngredients) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (RecipeIngredient recipe : recipeIngredients) {
            arrayBuilder.add(recipe.toJSON());
        }
        return arrayBuilder.build().toString();
    }
}

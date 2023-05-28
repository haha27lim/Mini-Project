package sg.edu.nus.iss.miniproject.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import sg.edu.nus.iss.miniproject.models.Recipe;
import sg.edu.nus.iss.miniproject.models.RecipeIngredient;
import sg.edu.nus.iss.miniproject.models.RecipeNutrients;
import sg.edu.nus.iss.miniproject.services.FoodService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeController {
    
	private Logger logger = Logger.getLogger(RecipeController.class.getName());

    @Autowired
    private FoodService foodSvc;

	@GetMapping(path = "/search")
	public ResponseEntity<String> getGenSearch(@RequestParam(name="query", required = true) String title,
		@RequestParam boolean recipeinfo, @RequestParam int number) {

		List<Recipe> recipes = foodSvc.searchGenRecipes(title, recipeinfo, number);

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		if (recipes != null) {
			for (Recipe rv : recipes) {
				arrBuilder.add(rv.toJSON());
			}
		}
	
		JsonArray result = arrBuilder.build();
		return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(result.toString());
	}

	@GetMapping(path = "/advsearch")
	public ResponseEntity<String> getSearch(@RequestParam(name="query", required = true) String title, @RequestParam (name = "cuisine", required = false) String cuisine, 
		@RequestParam(name = "diet", required = false) String diet, @RequestParam(name = "excludeIngredients", required = false) 
		String excludeIngredients, @RequestParam boolean recipeinfo, @RequestParam int number) {

		logger.log(Level.INFO, "cuisine=%s, query=%s, diet=%s, excludeIngredients=%s"
				.formatted(cuisine, title, diet, excludeIngredients));

		List<Recipe> recipes = foodSvc.searchRecipes(title, cuisine, diet, excludeIngredients, recipeinfo, number);

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		if (recipes != null) {
			for (Recipe rv : recipes) {
				arrBuilder.add(rv.toJSON());
			}
		}
	
		JsonArray result = arrBuilder.build();
		return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(result.toString());
	}

	@GetMapping(path = "/random")
	public ResponseEntity<String> getRandom(@RequestParam int number, @RequestParam(required = false) String tags) {

		List<Recipe> recipes;
		if (tags != null && !tags.isEmpty()) {
			recipes = foodSvc.getRandomRecipesTags(number, tags);
		} else {
			recipes = foodSvc.getRandomRecipes(number);
		}

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		if (recipes != null) {
			for (Recipe rv : recipes) {
				arrBuilder.add(rv.toJSON());
			}
		}
	
		JsonArray result = arrBuilder.build();
		System.out.println("result: " + result);
		return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(result.toString());
	}

	@GetMapping(path = "/recipes/{id}")
    public ResponseEntity<String> getRecipeById(@PathVariable int id) throws IOException {

		Recipe recipes = foodSvc.getRecipeById(id);

		if (recipes != null) {
			return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(recipes.toJSON().toString());
		} else {
			String message = String.format("Recipe with ID %d not found", id);
			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body("{ \"message\": \"" + message + "\" }");
		}
	}

	@GetMapping(path = "/list/type/{type}")
	public ResponseEntity<String> getTypeSearch(@PathVariable String type, @RequestParam boolean recipeinfo,
	 @RequestParam int number) throws IOException {

		List<Recipe> recipes = foodSvc.searchRecipesType(type, recipeinfo, number);

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		if (recipes != null) {
			for (Recipe rv : recipes) {
				arrBuilder.add(rv.toJSON());
			}
		}
	
		JsonArray result = arrBuilder.build();
		return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(result.toString());
	}

	@GetMapping(path = "/list/cuisine/{cuisine}")
	public ResponseEntity<String> getCuisineSearch(@PathVariable String cuisine, @RequestParam boolean recipeinfo,
	 @RequestParam int number) throws IOException {

		List<Recipe> recipes = foodSvc.searchRecipesCuisine(cuisine, recipeinfo, number);

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		if (recipes != null) {
			for (Recipe rv : recipes) {
				arrBuilder.add(rv.toJSON());
			}
		}
	
		JsonArray result = arrBuilder.build();
		return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(result.toString());
	}

	@GetMapping(path = "/list/diet/{diet}")
	public ResponseEntity<String> getDietSearch(@PathVariable String diet, @RequestParam boolean recipeinfo,
	 @RequestParam int number) throws IOException {

		List<Recipe> recipes = foodSvc.searchRecipesDiet(diet, recipeinfo, number);

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		if (recipes != null) {
			for (Recipe rv : recipes) {
				arrBuilder.add(rv.toJSON());
			}
		}
	
		JsonArray result = arrBuilder.build();
		return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(result.toString());
	}

	@GetMapping(path = "/list/ingredients")
	public ResponseEntity<String> getIngredientsSearch(@RequestParam String ingredients,
			@RequestParam int ranking, @RequestParam int number) throws IOException {
		List<RecipeIngredient> recipeIngredients = foodSvc.searchRecipesIngredients(ingredients, ranking, number);

		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (RecipeIngredient recipe : recipeIngredients) {
            arrayBuilder.add(recipe.toJSON());
        }

		return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(arrayBuilder.build().toString());
	}


	@GetMapping(path = "/list/nutrients")
	public ResponseEntity<String> getIngredientsSearch(@RequestParam(defaultValue="100") int maxCarbs,
			@RequestParam(defaultValue="200") int maxCalories, @RequestParam(defaultValue="40") int maxFat
			, @RequestParam int number) throws IOException {
		List<RecipeNutrients> recipeNutrients = foodSvc.searchRecipesNutrients(maxCarbs, maxCalories, maxFat, number);
		

		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (RecipeNutrients recipe : recipeNutrients) {
            arrayBuilder.add(recipe.toJSON());
        }

		return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(arrayBuilder.build().toString());
	}

}

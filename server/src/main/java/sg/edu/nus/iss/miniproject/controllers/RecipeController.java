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
        @RequestParam boolean recipeinfo) {

		JsonArray result = null;
		List<Recipe> recipes = foodSvc.searchGenRecipes(title, recipeinfo);

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		if (recipes != null) {
			for (Recipe rv : recipes) {
				arrBuilder.add(rv.toJSON());
			}
		}
	
		result = arrBuilder.build();
		return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(result.toString());
	}

	@GetMapping(path = "/search/{cuisine}")
	public ResponseEntity<String> getSearch(@PathVariable String cuisine, @RequestParam(name="query", required = true) String title,
        @RequestParam boolean recipeinfo) {

        logger.log(Level.INFO, "cuisine=%s, query=%s".formatted(cuisine, title));

		JsonArray result = null;
		List<Recipe> recipes = foodSvc.searchRecipes(cuisine, title, recipeinfo);

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		if (recipes != null) {
			for (Recipe rv : recipes) {
				arrBuilder.add(rv.toJSON());
			}
		}
	
		result = arrBuilder.build();
		return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(result.toString());
	}

	@GetMapping(path = "/random")
	public ResponseEntity<String> getRandom(@RequestParam int number) {

		JsonArray result = null;
		List<Recipe> recipes = foodSvc.getRandomRecipes(number);

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		if (recipes != null) {
			for (Recipe rv : recipes) {
				arrBuilder.add(rv.toJSON());
			}
		}
	
		result = arrBuilder.build();
		System.out.println("result: " + result);
		return ResponseEntity
			.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(result.toString());
	}
	
	@GetMapping(path = "/recipes/{id}")
    public ResponseEntity<String> getRecipeById(@PathVariable int id) throws IOException {

        Recipe r = foodSvc.getRecipeById(id);
        if (r == null) {
            return ResponseEntity.notFound().build();
        } else {
            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
            arrBuilder.add(r.toJSON());
            JsonArray result = arrBuilder.build();
            return ResponseEntity.ok(result.toString());
        }
    }
}

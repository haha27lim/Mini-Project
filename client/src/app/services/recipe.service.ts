import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Recipe, SavedRecipe } from '../models/recipe';
import { RecipeIngredient } from '../models/recipeIngredient';
import { ContactForm } from '../models/contact-form';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  private SEARCH_URI: string = "/api/search";
  private RANDOM_URI: string = "/api/random";
  private DETAILS_URI: string = "/api/recipes";
  private LIST_TYPE_URI: string = "/api/list/type";
  private LIST_CUISINE_URI: string = "/api/list/cuisine";
  private LIST_DIET_URI: string = "/api/list/diet";
  private LIST_INGREDIENTS_URI: string = "/api/list/ingredients";
  private CONTACT_EMAIL_URI: string = "/api/contact";
  private SAVE_RECIPE_URI: string = "/api/saverecipes";

  constructor(private httpClient: HttpClient) { }

  getGenSearch(title: string, recipeinfo: boolean, number: number): Promise<Recipe[]> {
    const params = new HttpParams()
      .set('query', title)
      .set('recipeinfo', recipeinfo.toString())
      .set('number', number.toString());
  
    return lastValueFrom(this.httpClient
      .get<Recipe[]>(this.SEARCH_URI, {params: params})).then(
        (recipes) => {
          console.log('Found recipes:', recipes);
          return recipes;
        },
        (error) => {
          console.error(`Error loading recipes: `, error);
          throw error;
        }
      );
  }
  
  getSearch(cuisine: string, title: string, addRecipeInformation: boolean, number: number, diet?: string,
     excludeIngredients?: string): Promise<Recipe[]> {
    let params = new HttpParams()
      .set('query', title)
      .set('recipeinfo', addRecipeInformation.toString())
      .set('number', number.toString());

    if (cuisine !== undefined && cuisine !== null) {
      params = params.set('cuisine', cuisine);
    }
    if (diet !== undefined && diet !== null && diet !== "none") {
      params = params.set('diet', diet);
    }
    if (excludeIngredients) {
      params = params.set('excludeIngredients', excludeIngredients);
    }

    return lastValueFrom(this.httpClient.get<Recipe[]>(`${this.SEARCH_URI}/${cuisine}`, { params: params })).then(
        (recipes) => {
          console.log('Found recipes:', recipes);
          return recipes;
        },
        (error) => {
          console.error(`Error loading recipes: `, error);
          throw error;
        }
      );
  }

  getRandom(number: number, tags?: string): Promise<Recipe[]> {
    let params = new HttpParams()
              .set('number', number.toString())

    if (tags) {
      params = params.set('tags', tags);
    }

    return lastValueFrom(this.httpClient
        .get<Recipe[]>(this.RANDOM_URI, {params: params}))
  }

  getRecipeById(id: number): Promise<Recipe> {

    return lastValueFrom(this.httpClient.get<Recipe>(`${this.DETAILS_URI}/${id}`)).then(
      (recipe: Recipe) => {
        console.log(`Recipe with id ${id} loaded successfully: `, recipe);
        return recipe;
      },
      (error) => {
        console.error(`Error loading recipe with id ${id}: `, error);
        throw error;
      }
    );
  }
  
  getRecipesType(type: string, addRecipeInformation: boolean, number: number): Promise<Recipe[]> {
    let params = new HttpParams()
      .set('recipeinfo', addRecipeInformation.toString())
      .set('number', number.toString())
      .set('type', type);

    return lastValueFrom(this.httpClient.get<Recipe[]>(`${this.LIST_TYPE_URI}/${type}`, { params: params }))
  }

  getRecipesCuisine(cuisine: string, addRecipeInformation: boolean, number: number): Promise<Recipe[]> {
    let params = new HttpParams()
      .set('recipeinfo', addRecipeInformation.toString())
      .set('number', number.toString())
      .set('cuisine', cuisine);

    return lastValueFrom(this.httpClient.get<Recipe[]>(`${this.LIST_CUISINE_URI}/${cuisine}`, { params: params }))
  }

  getRecipesDiet(diet: string, addRecipeInformation: boolean, number: number): Promise<Recipe[]> {
    let params = new HttpParams()
      .set('recipeinfo', addRecipeInformation.toString())
      .set('number', number.toString())
      .set('diet', diet);

    return lastValueFrom(this.httpClient.get<Recipe[]>(`${this.LIST_DIET_URI}/${diet}`, { params: params }))
  }

  getRecipesIngredients(ingredients: string, ranking: number, number: number): Promise<RecipeIngredient[]> {
    const params = new HttpParams()
      .set('ingredients', ingredients)
      .set('ranking', ranking.toString())
      .set('number', number.toString())

    return lastValueFrom(this.httpClient.get<RecipeIngredient[]>(this.LIST_INGREDIENTS_URI, { params: params })).then(
      (recipes) => {
        console.log('Found recipes:', recipes)
        return recipes
      },
      (error) => {
        console.error(`Error loading recipes: `, error)
        throw error
      }
    );
  }
  
  shareRecipe(recipe: Recipe) {
    const shareUrl = 'https://example.com/recipes/' + recipe.id; // Replace with the actual URL of your recipe details page

    if (navigator.share) {
      navigator.share({
        title: recipe.title,
        text: 'Check out on this nice and delicious recipe: ' + recipe.title,
        url: shareUrl
      })
      .then(() => console.log('Recipe was shared successful'))
      .catch((error) => console.log('Error on sharing:', error));
    } else {
      console.log('Sharing was not able')
      console.log(shareUrl)
    }
  }

  sendContactEmail(contactForm: ContactForm): Promise<void> {
    return lastValueFrom(this.httpClient.post<void>(this.CONTACT_EMAIL_URI, contactForm));
  }

  getAllSavedRecipes(): Promise<SavedRecipe[]> {
    return lastValueFrom(this.httpClient.get<SavedRecipe[]>(this.SAVE_RECIPE_URI));
  }

  getSavedRecipes(userId: number): Promise<SavedRecipe[]> {
    return lastValueFrom(this.httpClient.get<SavedRecipe[]>(`${this.SAVE_RECIPE_URI}/${userId}`));
  }

  saveRecipe(savedRecipe: SavedRecipe): Promise<SavedRecipe> {
    return lastValueFrom(this.httpClient.post<SavedRecipe>(this.SAVE_RECIPE_URI, savedRecipe));
  }

  deleteRecipe(id: number): Promise<void> {
    return lastValueFrom(this.httpClient.delete<void>(`${this.SAVE_RECIPE_URI}/${id}`));
  }


}

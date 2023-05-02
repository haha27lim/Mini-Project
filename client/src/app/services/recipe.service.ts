import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, catchError, firstValueFrom, lastValueFrom, map, tap } from 'rxjs';
import { Recipe } from '../models/recipe';
import { CUISINES } from '../constants';

const API_KEY = "5f470da37d954bc0921f9dc9b4b2ae14";

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  private SEARCH_URI: string = "/api/search";
  private RANDOM_URI: string = "/api/random";
  private DETAILS_URI: string = "/api/recipes";

  constructor(private httpClient: HttpClient) { }

  getGenSearch(title: string, recipeinfo: boolean): Promise<Recipe[]> {
    const params = new HttpParams()
      .set('query', title)
      .set('recipeinfo', recipeinfo.toString());
  
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
  

  getSearch(cuisine: string, title: string, addRecipeInformation: boolean, diet?: string, excludeIngredients?: string): Promise<Recipe[]> {
    let params = new HttpParams()
      .set('query', title)
      .set('recipeinfo', addRecipeInformation.toString());

    if (cuisine !== undefined && cuisine !== null) {
      params = params.set('cuisine', cuisine);
    }
    if (diet !== undefined && diet !== null && diet !== "none") {
      params = params.set('diet', diet);
    }
    if (excludeIngredients) {
      params = params.set('excludeIngredients', excludeIngredients);
    }

    return lastValueFrom(this.httpClient
      .get<Recipe[]>(`${this.SEARCH_URI}/${cuisine}`, { params: params })).then(
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



  getRandom(number: number, id?: number): Promise<Recipe[]> {
    const params = new HttpParams()
              .set('number', number)

    // If an id is provided, filter by that id
    if (id) {
      params.set('id', id.toString());
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
  
  
  


  
  // getRecipeById(id: number): Promise<Recipe> {
  //   const params = new HttpParams()
  //             .set('apiKey', API_KEY)
  
  //   // send GET request to API with query parameters and headers
  //   return firstValueFrom(
  //       this.httpClient.get<Recipe>("https://api.spoonacular.com/recipes/" + id + "/information", { params })
  //         .pipe(
  //           map((data: any) => {
  //             return {
  //               id: data.id,
  //               title: data.title,
  //               readyInMinutes: data.readyInMinutes,
  //               servings: data.servings,
  //               image: data.image,
  //               sourceUrl: data.sourceUrl,
  //               dishTypes: data.dishTypes,
  //               diets: data.diets,
  //               instructions: data.instructions,
  //               extendedIngredients: data.extendedIngredients
  //             } as Recipe
  //           })
  //         )
  //     ).then(result => {
  //       console.info('>>> result: ', result)
  //       return result
  //     }) as Promise<Recipe>
  // }
}

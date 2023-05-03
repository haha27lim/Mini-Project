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
  private LIST_URI: string = '/api/list/';


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

    return lastValueFrom(this.httpClient.get<Recipe[]>(`${this.LIST_URI}/${type}`, { params: params }))
  }




}

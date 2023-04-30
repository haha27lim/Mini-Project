import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Recipe } from '../models/recipe';
import { CUISINES } from '../constants';

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
              .set('addRecipeInformation', recipeinfo.toString());

    // send GET request to API with query parameters and headers
    return lastValueFrom(this.httpClient
        .get<Recipe[]>(this.SEARCH_URI, {params: params}))
  }

  getSearch(cuisine: string, title: string, recipeinfo: boolean): Promise<Recipe[]> {
    const params = new HttpParams()
              .set('cuisine', cuisine)
              .set('query', title)
              .set('addRecipeInformation', recipeinfo.toString());

    // send GET request to API with query parameters and headers
    return lastValueFrom(this.httpClient
        .get<Recipe[]>(`${this.SEARCH_URI}/${CUISINES}`, {params: params}))
  }

  getRandom(number: number, id?: number): Promise<Recipe[]> {
    const params = new HttpParams()
              .set('number', number)

    // If an id is provided, filter by that id
    if (id) {
      params.set('id', id.toString());
    }

    // send GET request to API with query parameters and headers
    return lastValueFrom(this.httpClient
        .get<Recipe[]>(this.RANDOM_URI, {params: params}))
  }

  getRecipeById(id: number): Promise<Recipe> {

    // send GET request to API with query parameters and headers
    return lastValueFrom(this.httpClient
        .get<Recipe>(`${this.DETAILS_URI}/${id}`))
  }
 
}

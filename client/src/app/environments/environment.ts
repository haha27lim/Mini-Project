import { HttpHeaders } from '@angular/common/http';

export const environment = {
  production: false,
  url: 'https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/images/analyze',
  headers: new HttpHeaders({
    'x-rapidapi-host': 'spoonacular-recipe-food-nutrition-v1.p.rapidapi.com',
    'x-rapidapi-key': import.meta.env['NG_APP_RAPIDAPI_KEY']
  })
};
import { HttpHeaders } from '@angular/common/http';

export const environment = {
  production: true,
  url: 'https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/images/analyze',
  headers: new HttpHeaders({
    'x-rapidapi-host': 'spoonacular-recipe-food-nutrition-v1.p.rapidapi.com',
    'x-rapidapi-key': process.env['RAPIDAPI_KEY'] || ''
  })
};
import { HttpHeaders } from '@angular/common/http';

export const environment = {
  production: false,
  url: 'https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/images/analyze',
  headers: new HttpHeaders({
    'x-rapidapi-host': 'spoonacular-recipe-food-nutrition-v1.p.rapidapi.com',
    'x-rapidapi-key': 'a3a062dbf2msh2a4ce26f4b370f9p1a0c59jsnebf262f45e9d'
  })
};

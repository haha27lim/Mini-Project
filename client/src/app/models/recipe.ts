export interface Recipe {
    recipeDetails: RecipeDetails;
    id: number;
    title: string;
    readyInMinutes: number;
    servings: number;
    image: string;
    sourceUrl: string;
    spoonacularSourceUrl: string;
    cuisines: string[];
    dishTypes: string[];
    diets: string[];
    instructions: string;
    extendedIngredients: ExtendedIngredients[];
}

export interface ExtendedIngredients {
    originalName: string;
    amount: number;
    unit: string;
}

export interface Comment {
    title: string;
    name: string;
    email: string;
    subject: string;
    rating: number;
    comment: string; 
}

export interface SavedRecipe {
    id?: number;
    userId: number;
    recipeId: number;
    recipeTitle: string;
    recipeDetails: RecipeDetails;
}
  
export interface RecipeDetails {
    savedRecipeId: number;
    servings: number;
    readyInMinutes: number;
}

export interface UserRecipeCount {
    userId: number;
    username: string;
    recipeCount: number;
  }
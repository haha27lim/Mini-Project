export interface RecipeIngredient {
    id: number;
    title: string;
    image: string;
    usedIngredientCount: number;
    missedIngredientCount: number;
    likes: number;
    missedIngredients: MissedIngredients[];
    usedIngredients: UsedIngredients[];
  }
  
  export interface MissedIngredients {
    originalName: string;
    image: string;
  }
  
  export interface UsedIngredients {
    originalName: string;
    image: string;
  }
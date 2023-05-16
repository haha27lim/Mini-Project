export interface Recipe {
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
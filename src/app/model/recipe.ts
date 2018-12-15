export class Recipe {
  _id: string;
  name: string;
  photo: string;
  category: string;
  servings: number;
  calories: number;
  ingredients: Ingredient[];
  directions: Direction[];
  notes: string;
  prepTime: string;
  stars: number;
}

export class Ingredient {
  _id: string;  
  ingredient: string;
}

export class Direction {
  _id: string;  
  instruction: string;
}
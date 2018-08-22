export class Recipe {
    id: number;
    name: string;
    photo: string;// TODO URL ??
    category: string;
    servings: number;
    calories: number;
    ingredients: string[]; // TODO make object ??
    directions: string[]; // TODO make object ??
    notes: string;
    prepTime: string;
    stars: number;
  }
  
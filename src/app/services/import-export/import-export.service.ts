import { Injectable, OnInit, Inject } from '@angular/core';
import { Observable, of } from 'rxjs';
import { fromPath, writeToPath } from 'fast-csv';

import { NedbDaoService } from '../dao/nedb-dao.service';

import { Recipe, Ingredient, Direction } from '../../model/recipe'

var flatten = require('flat');

@Injectable({
  providedIn: 'root'
})
export class ImportExportService {

  constructor(private nedbDaoService: NedbDaoService) {}

  exportRecipes(path: string): void {
    var maxIng = this.maxIngredients();
    var maxDir = this.maxDirections();
    this.nedbDaoService.getRecipes().subscribe(recipes => {
      var normRecipes = this.normRecipes(recipes, maxIng, maxDir);
      writeToPath(path, normRecipes, {headers: true, quoteColumns: true})
      .on("finish", function(){
        console.log("Export completed");
      });
    });
  }

  private maxIngredients(): number {
    var maxIngredients = 0;
    this.nedbDaoService.getRecipes().subscribe(recipes => {
      maxIngredients = recipes
        .map((r, i, a) => {return r.ingredients.length;})
        .reduce((p, c, i, a) => {
          if (p >= c) {
            return p;
          } else {
            return c;
          }
        });
      });
    return maxIngredients;
  }

  private maxDirections(): number {
    var maxDirections = 0;
    this.nedbDaoService.getRecipes().subscribe(recipes => {
      maxDirections = recipes
        .map((r, i, a) => {return r.directions.length;})
        .reduce((p, c, i, a) => {
          if (p >= c) {
            return p;
          } else {
            return c;
          }
        });
      });
    return maxDirections;
  }

  private normRecipes(recipes: Recipe[], maxIngredients: number, maxDirections: number): Recipe[] {
    return recipes.map((recipe, i, a) => {
      var flatRecipe = Object.assign({}, recipe);
      flatRecipe.ingredients = this.normIngredients(recipe.ingredients, maxIngredients);
      flatRecipe.directions = this.normDirections(recipe.directions, maxDirections);
      return flatten(recipe);
    });
  }

  private normIngredients(ingredients: Ingredient[], length: number): Ingredient[] {
    var emptyIngredients = Array<Ingredient>(length);
    ingredients.forEach((ing, i, a) => {
      emptyIngredients[i] = ing;
    });
    return emptyIngredients;
  }

  private normDirections(directions: Direction[], length: number): Direction[] {
    var emptyDirections = Array<Direction>(length);
    directions.forEach((ing, i, a) => {
      emptyDirections[i] = ing;
    });
    return emptyDirections;
  }

  importRecipes(path: string): void {
    var dbService = this.nedbDaoService;
    const stream = fromPath(path, {
        headers: true,
        delimiter: ";"
    })
    stream.on("data", function(data){
        var recipe = new Recipe;
        recipe.name = data['Nome'];
        recipe.photo = "assets/img/recipe-book.png";
        recipe.category = data['Categoria'];
        recipe.servings = data['Persone'];
        recipe.calories = data['Caltot'];
        recipe.ingredients = [
          data['Ingr.1'],
          data['Ingr.2'],
          data['Ingr.3'],
          data['Ingr.4'],
          data['Ingr.5'],
          data['Ingr.6'],
          data['Ingr.7'],
          data['Ingr.8'],
          data['Ingr.9'],
          data['Ingr.10'],
          data['Ingr.11'],
          data['Ingr.12'],
          data['Ingr.13'],
          data['Ingr.14'],
          data['Ingr.15'],
          data['Ingr.16'],
          data['Ingr.17'],
          data['Ingr.18']
        ];
        recipe.directions = [
          data['Istruzioni 1'],
          data['Istruzioni 2'],
          data['Istruzioni 3'],
          data['Istruzioni 4'],
          data['Istruzioni 5'],
          data['Istruzioni 6'],
          data['Istruzioni 7'],
          data['Istruzioni 8'],
          data['Istruzioni 9'],
          data['Istruzioni 10'],
          data['Istruzioni 11'],
          data['Istruzioni 12'],
          data['Istruzioni 13'],
          data['Istruzioni 14'],
          data['Istruzioni 15'],
          data['Istruzioni 16'],
          data['Istruzioni 17'],
          data['Istruzioni 18'],
        ];
        recipe.notes = data['Categoria'];
        recipe.prepTime = data['Categoria'];
        recipe.stars = 3;
        dbService.addRecipe(recipe).subscribe();
    })
    .on("end", function(){
        console.log("Import completed");
    });
  }
}

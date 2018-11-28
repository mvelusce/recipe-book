import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import * as Nedb from 'nedb';

import { Recipe } from '../../model/recipe';

@Injectable({
  providedIn: 'root'
})
export class NedbDaoService {

  private db = new Nedb({ filename: './data/db/.recipes', autoload: true });

  constructor() {
    console.log("CONST");
    const recipes = this.createDb();

    this.db.insert(recipes, function (err, newDoc) { // TODO to remove and use file system
      console.debug("Recipes inserted in DB");
      console.log(newDoc);
    });
  }

  private createDb() {// TODO to remove and use file system
    const recipes = [
      {
        name: "recipe42",
        photo: "./default-bf.jpg",
        category: "Breakfast",
        servings: 42,
        calories: 123,
        ingredients: [{ingredient: "Onions"}, {ingredient: "Sugar"}],
        directions: [{instruction: "Do it"}],
        notes: "Great",
        prepTime: "15 mins", 
        stars: 1
      },{
        name: "recipe2",
        photo: "./default-bf.jpg",
        category: "Breakfast",
        servings: 42,
        calories: 123,
        ingredients: [{ingredient: "Onions"}],
        directions: [{instruction: "Do it"}],
        notes: "Great",
        prepTime: "15 mins", 
        stars: 1
      },{
        name: "recipe3",
        photo: "./default-bf.jpg",
        category: "Breakfast",
        servings: 42,
        calories: 123,
        ingredients: [{ingredient: "Onions"}],
        directions: [{instruction: "Do it"}],
        notes: "Great",
        prepTime: "15 mins", 
        stars: 1
      },
    ];
    return recipes;
  }

  getRecipes(): Observable<Recipe[]> {
    return new Observable((observer) => {
      this.db.find({}, function (err, results) {
        if (err != null) {
          return this.unsubscribeWhenError(`Error when getting all recipes.`, err);
        }
        console.debug(`Got all recipes from DB: `, results);
        observer.next(results);
        observer.complete();
      });
      return {unsubscribe() {}};
    });
  }

  getRecipe(id: string): Observable<Recipe> {
    return new Observable((observer) => {
      this.db.findOne<Recipe>({ _id: id }, function (err: Error, result: Recipe) {
        if (err != null) {
          return this.unsubscribeWhenError(`Error when getting recipe with id ${id}.`, err);
        }
        console.debug(`Got recipe with id ${id}.`, result);
        observer.next(result);
        observer.complete();
      });
      return {unsubscribe() {}}
    });
  }

  updateRecipe(recipe: Recipe): Observable<Recipe> {
    return new Observable((observer) => {
      this.db.update({_id: recipe._id}, {$set : recipe}, {upsert: true}, function(err, numUpdated) {// TODO this syntax works, need to update all other methods to interact with DB
        if (err != null) {
          console.error("ERROR UPDATE");
          console.error("message", err);
          return {unsubscribe() {}};
          //return this.unsubscribeWhenError(`Error when updating the recipe: ${recipe}.`, err);
        }
        console.debug("Updating recipe:", recipe, "Number of updated:", numUpdated);
        observer.next(recipe);
        observer.complete();
      });
      //this.db.persistence.compactDatafile();
      return {unsubscribe() {}}
    });
  }

  addRecipe(recipe: Recipe): Observable<Recipe> {
    return new Observable((observer) => {
      this.db.insert(recipe, function (err, newRecipe) {
        if (err != null) {
          return this.unsubscribeWhenError(`Error when adding the recipe: ${recipe}.`, err);
        }
        console.debug("Recipe inserted in DB:", newRecipe);
        observer.next(newRecipe);
        observer.complete();
      });
      return {unsubscribe() {}}
    });
  }
  
  deleteRecipe(recipe: Recipe | string): Observable<number> {
    const id = typeof recipe === 'string' ? recipe : recipe._id;
    return new Observable((observer) => {
      this.db.remove({_id: id}, {}, function(err, numRemoved) {
        if (err != null) {
          return this.unsubscribeWhenError(`Error when removing the recipe with id: ${id}.`, err);
        }
        console.debug(`Recipe removed from DB: ${id}`);
        observer.next(numRemoved);
        observer.complete();
      });
      return {unsubscribe() {}}
    });
  }

  searchRecipes(term: string): Observable<Recipe[]> {
    return new Observable((observer) => {
      this.db.find({name: term}, function(err, result) {// TODO search more than for name
        if (err != null) {
          return this.unsubscribeWhenError(`Error when searching recipe with name ${term}.`, err);
        }
        console.debug(`Got recipe with name ${term} from DB: ${result}`);
        observer.next(result);
        observer.complete();
      });
      return {unsubscribe() {}}
    });
  }

  private unsubscribeWhenError(message: String, error: String) {
    console.error(message, error);
    return {unsubscribe() {}};
  }
}

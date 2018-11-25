import { Component, OnInit } from '@angular/core';
import { NedbDaoService } from '../../services/dao/nedb-dao.service';
import { Recipe } from '../../model/recipe';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.scss']
})
export class RecipesComponent implements OnInit {
  recipes: Recipe[];

  constructor(private nedbDaoService: NedbDaoService) { }

  ngOnInit() {
    this.getRecipes();
  }

  getRecipes(): void {
    this.nedbDaoService.getRecipes().subscribe(recipes => {
      console.log("GET RECIPES IN COMP");
      console.debug(recipes);
      this.recipes = recipes
    });
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.nedbDaoService.addRecipe({ name } as Recipe)
      .subscribe(recipe => {
        console.debug("ADD RECIPE IN DB");
        this.recipes.push(recipe);
      });
  }

  delete(recipe: Recipe): void {
    this.recipes = this.recipes.filter(h => h !== recipe);
    this.nedbDaoService.deleteRecipe(recipe).subscribe();
  }
}

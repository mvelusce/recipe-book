import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Recipe } from '../../model/recipe';
import { NedbDaoService } from '../../services/dao/nedb-dao.service';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.scss']
})
export class RecipeDetailComponent implements OnInit {

  @Input() recipe: Recipe;

  constructor(
    private route: ActivatedRoute,
    private nedbDaoService: NedbDaoService,
    private location: Location
  ) {}

  ngOnInit() {
    this.getRecipe();
  }

  getRecipe(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.nedbDaoService.getRecipe(id).subscribe(recipe => this.recipe = recipe);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    console.debug("SAVE", this.recipe);
    this.nedbDaoService.updateRecipe(this.recipe)
      .subscribe(() => this.goBack());
  }

}

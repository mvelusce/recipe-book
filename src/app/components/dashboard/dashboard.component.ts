import { Component, OnInit } from '@angular/core';
import { Recipe } from '../../model/recipe';
import { NedbDaoService } from '../../services/dao/nedb-dao.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.scss' ]
})
export class DashboardComponent implements OnInit {
  recipes: Recipe[] = [];

  constructor(private nedbDaoService: NedbDaoService) { }

  ngOnInit() {
    this.getRecipes();
  }

  getRecipes(): void {
    this.nedbDaoService.getRecipes()
      .subscribe(recipes => this.recipes = recipes.slice(1, 5));
  }
}

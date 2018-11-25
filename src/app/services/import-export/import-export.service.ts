import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { NedbDaoService } from '../dao/nedb-dao.service';

@Injectable({
  providedIn: 'root'
})
export class ImportExportService {

  constructor(private nedbDaoService: NedbDaoService) { }

  exportRecipes(): void {
    this.nedbDaoService.getRecipes().subscribe(recipes => {
        console.log("GET RECIPES IN COMP");
        console.debug(recipes);
        // TODO write recipes
      });
  }

  importRecipes(): void {
    // TODO read recipes and insert in DB
    /* this.nedbDaoService.addRecipe({ name } as Recipe)
      .subscribe(recipe => {
        console.debug("ADD RECIPE IN DB");
        
      }); */
  }

}

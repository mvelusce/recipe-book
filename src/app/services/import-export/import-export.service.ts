import { Injectable, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { fromPath } from 'fast-csv'

import { NedbDaoService } from '../dao/nedb-dao.service';

@Injectable({
  providedIn: 'root'
})
export class ImportExportService implements OnInit {

  constructor(private nedbDaoService: NedbDaoService) { }

  ngOnInit() {
    this.importRecipes("data/ricette.csv");
  }

  exportRecipes(): void {
    this.nedbDaoService.getRecipes().subscribe(recipes => {
        console.log("GET RECIPES IN COMP");
        console.debug(recipes);
        // TODO write recipes
      });
  }

  importRecipes(path: string): void {
    const stream = fromPath(path, {
        headers: true,
        delimiter: ";"
    })
    stream.on("data", function(data){
        console.log(data);
    })
    .on("end", function(){
        console.log("done");
    });
    //fs.createReadStream(path);
    // TODO read recipes and insert in DB
    /* this.nedbDaoService.addRecipe({ name } as Recipe)
      .subscribe(recipe => {
        console.debug("ADD RECIPE IN DB");
        
      }); */
  }

}

import { Injectable, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { fromPath, writeToPath } from 'fast-csv';

import { NedbDaoService } from '../dao/nedb-dao.service';

import { Recipe } from '../../model/recipe'

var flatten = require('flat');

@Injectable({
  providedIn: 'root'
})
export class ImportExportService {

  constructor(private nedbDaoService: NedbDaoService) { }

  exportRecipes(path: string): void {

    this.nedbDaoService.getRecipes().subscribe(recipes => {
      var flatRecipes = recipes.map((v, i, a) => {return flatten(v)});// TODO this only works if all sub arrays are the same lenght - need to transform ingredients and directions
      writeToPath(path, flatRecipes, {headers: true, quoteColumns: true})
      .on("finish", function(){
        console.log("Export completed");
      });
    });
  }

  importRecipes(path: string): void {

    const stream = fromPath(path, {
        headers: true,
        delimiter: ";"
    })
    stream.on("data", function(data){
        console.log(data);
        var recipe = new Recipe;// TODO map all fields from old format to new
        recipe.name = data['Nome'];
        console.log(recipe);
    })
    .on("end", function(){
        console.log("Import completed");
    });
  }
}

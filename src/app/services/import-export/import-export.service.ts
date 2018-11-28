import { Injectable, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { fromPath, writeToPath } from 'fast-csv'

import { NedbDaoService } from '../dao/nedb-dao.service';

@Injectable({
  providedIn: 'root'
})
export class ImportExportService {

  constructor(private nedbDaoService: NedbDaoService) { }

  /* ngOnInit() {
    this.importRecipes("data/ricette.csv");
  } */

  exportRecipes(path: string): void {

    writeToPath(path, [
      {a: "a1", b: "b1"},
      {a: "a2", b: "b2"}], {headers: true})
    .on("finish", function(){
      console.log("done!");
    });

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
  }
}

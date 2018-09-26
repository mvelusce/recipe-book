import { Component, OnInit } from '@angular/core';
import { Hero } from '../heroes/hero';
import { NedbDaoService } from '../../services/dao/nedb-dao.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.scss' ]
})
export class DashboardComponent implements OnInit {
  heroes: Hero[] = [];

  constructor(private nedbDaoService: NedbDaoService) { }

  ngOnInit() {
    this.getHeroes();
  }

  getHeroes(): void {
    this.nedbDaoService.getHeroes()
      .subscribe(heroes => this.heroes = heroes.slice(1, 5));
  }
}
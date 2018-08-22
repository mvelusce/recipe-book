import { Component, OnInit } from '@angular/core';
import { Hero } from './hero';
import { HeroService } from '../../services/hero/hero.service';

import { NedbDaoService } from '../../services/dao/nedb-dao.service';

@Component({
  selector: 'app-heroes',
  templateUrl: './heroes.component.html',
  styleUrls: ['./heroes.component.scss']
})
export class HeroesComponent implements OnInit {
  heroes: Hero[];

  constructor(private heroService: HeroService, private nedbDaoService: NedbDaoService) { }

  ngOnInit() {
    this.getHeroes();
  }

  getHeroes(): void {
    /* this.heroService.getHeroes()
        .subscribe(heroes => this.heroes = heroes); */
    this.nedbDaoService.getHeroes().subscribe(heroes => {
      console.log("GET HEROES IN COMP");
      console.debug(heroes);
      this.heroes = heroes
    });
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.heroService.addHero({ name } as Hero)
      .subscribe(hero => {
        this.heroes.push(hero);
      });
  }

  delete(hero: Hero): void {
    this.heroes = this.heroes.filter(h => h !== hero);
    this.heroService.deleteHero(hero).subscribe();
  }
}

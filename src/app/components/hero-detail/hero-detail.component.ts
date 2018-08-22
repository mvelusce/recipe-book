import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Hero } from '../heroes/hero';
import { HeroService }  from '../../services/hero/hero.service';

import { NedbDaoService } from '../../services/dao/nedb-dao.service';

@Component({
  selector: 'app-hero-detail',
  templateUrl: './hero-detail.component.html',
  styleUrls: ['./hero-detail.component.scss']
})
export class HeroDetailComponent implements OnInit {

  @Input() hero: Hero;

  constructor(
    private route: ActivatedRoute,
    private heroService: HeroService,
    private nedbDaoService: NedbDaoService,
    private location: Location
  ) {}

  ngOnInit() {
    this.getHero();
  }

  getHero(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.nedbDaoService.getHero(id).subscribe(hero => this.hero = hero);
    /* this.heroService.getHero(id)
      .subscribe(hero => this.hero = hero); */
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.heroService.updateHero(this.hero)
      .subscribe(() => this.goBack());
  }

}

import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import * as Nedb from 'nedb';

import { Hero } from '../../components/heroes/hero';

@Injectable({
  providedIn: 'root'
})
export class NedbDaoService {

  // TODO doc DB: https://github.com/louischatriot/nedb

  private db = new Nedb();

  constructor() {
    console.log("CONST");
    const heroes = this.createDb();

    this.db.insert(heroes, function (err, newDoc) { // TODO to remove and use file system
      console.debug("Heroes inserted in DB");
      console.log(newDoc);
    });
  }

  private createDb() {// TODO to remove and use file system
    const heroes = [
      { id: 11, name: 'Mr. Nice' },
      { id: 12, name: 'Narco' },
      { id: 13, name: 'Bombasto' },
      { id: 14, name: 'Celeritas' },
      { id: 15, name: 'Magneta' },
      { id: 16, name: 'RubberMan' },
      { id: 17, name: 'Dynama' },
      { id: 18, name: 'Dr IQ' },
      { id: 19, name: 'Magma' },
      { id: 20, name: 'Tornado' },
      { id: 21, name: 'Hero of DB' }
    ];
    return heroes;
  }

  getHeroes(): Observable<Hero[]> {
    return new Observable((observer) => {
      this.db.find({}, function (err, results) {
        if (err != null) {
          return this.unsubscribeWhenError("Error when getting all heroes: ${err}");
        }
        console.debug("Got all heroes from DB: ${results}");
        observer.next(results);
        observer.complete();
      });
      return {unsubscribe() {}};
    });
  }

  getHero(id: number): Observable<Hero> {
    return new Observable((observer) => {
      this.db.findOne<Hero>({ id: id }, function (err, result) {
        if (err != null) {
          return this.unsubscribeWhenError("Error when getting hero with id ${id}. Error: ${err}");
        }
        console.debug("Got hero with id ${id} from DB: ${result}");
        observer.next(result);
        observer.complete();
      });
      return {unsubscribe() {}}
    });
  }

  updateHero(hero: Hero): Observable<any> {
    return new Observable((observer) => {
      this.db.update(hero.id, hero, {upsert: true}, function(err, numUpdated) {
        if (err != null) {
          return this.unsubscribeWhenError("Error when updating the hero: ${hero}. Error: ${err}");
        }
        console.debug("Updating hero: ${hero}. Number of updated: ${numUpdated}");
        observer.next(numUpdated);
        observer.complete();
      });
      return {unsubscribe() {}}
    });
  }

  addHero(hero: Hero): Observable<Hero> {
    return new Observable((observer) => {
      this.db.insert(hero, function (err, newHero) {
        if (err != null) {
          return this.unsubscribeWhenError("Error when adding the hero: ${hero}. Error: ${err}");
        }
        console.debug("Hero inserted in DB: ${newHero}");
        observer.next(newHero);
        observer.complete();
      });
      return {unsubscribe() {}}
    });
  }
  
  deleteHero(hero: Hero | number): Observable<Hero> {
    const id = typeof hero === 'number' ? hero : hero.id;
    return new Observable((observer) => {
      this.db.remove({id: id}, {}, function(err, numRemoved) {
        if (err != null) {
          return this.unsubscribeWhenError("Error when removing the hero with id: ${id}. Error: ${err}");
        }
        console.debug("Hero removed from DB: ${id}");
        let removedHero: Hero = new Hero();
        removedHero.id = id;// FIXME constructor ??
        observer.next(removedHero);
        observer.complete();
      });
      return {unsubscribe() {}}
    });
  }

  /* GET heroes whose name contains search term */
  searchHeroes(term: string): Observable<Hero[]> {
    return null;
    /* if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }
    return this.http.get<Hero[]>(`${this.heroesUrl}/?name=${term}`).pipe(
      tap(_ => this.log(`found heroes matching "${term}"`)),
      catchError(this.handleError<Hero[]>('searchHeroes', []))
    ); */
  }

  private unsubscribeWhenError(message: String) {
    console.error(message);
    return {unsubscribe() {}};
  }
}

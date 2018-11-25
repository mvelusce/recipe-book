import { TestBed, inject } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { TranslateModule } from '@ngx-translate/core';

import { NedbDaoService } from './nedb-dao.service';

describe('NedbDaoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NedbDaoService],
      imports: [
        RouterTestingModule,
        TranslateModule.forRoot()
      ]
    });
  });

  it('should be created', inject([NedbDaoService], (service: NedbDaoService) => {
    expect(service).toBeTruthy();
  }));

  it('should do stuff in DB', inject([NedbDaoService], (service: NedbDaoService) => {
    const recipes = service.getRecipes();
    expect(recipes).toBeDefined();
  }));
});

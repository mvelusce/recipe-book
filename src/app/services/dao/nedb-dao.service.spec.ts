import { TestBed, inject } from '@angular/core/testing';

import { NedbDaoService } from './nedb-dao.service';

describe('NedbDaoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NedbDaoService]
    });
  });

  it('should be created', inject([NedbDaoService], (service: NedbDaoService) => {
    expect(service).toBeTruthy();
  }));

  it('should do stuff in DB', inject([NedbDaoService], (service: NedbDaoService) => {
    const heroes = service.getHeroes();
    expect(heroes);
  }));
});

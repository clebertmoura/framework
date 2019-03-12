import { TestBed } from '@angular/core/testing';

import { LibFrameworkService } from './framework-lib.service';

describe('LibFrameworkService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LibFrameworkService = TestBed.get(LibFrameworkService);
    expect(service).toBeTruthy();
  });
});

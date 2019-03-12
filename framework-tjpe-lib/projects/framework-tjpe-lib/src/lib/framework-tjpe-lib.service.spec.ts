import { TestBed } from '@angular/core/testing';

import { LibTjpeFrameworkService } from './framework-tjpe-lib.service';

describe('LibTjpeFrameworkService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LibTjpeFrameworkService = TestBed.get(LibTjpeFrameworkService);
    expect(service).toBeTruthy();
  });
});

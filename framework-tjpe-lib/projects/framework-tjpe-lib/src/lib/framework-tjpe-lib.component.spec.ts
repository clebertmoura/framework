import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LibTjpeFrameworkComponent } from './framework-tjpe-lib.component';

describe('LibTjpeFrameworkComponent', () => {
  let component: LibTjpeFrameworkComponent;
  let fixture: ComponentFixture<LibTjpeFrameworkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LibTjpeFrameworkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LibTjpeFrameworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

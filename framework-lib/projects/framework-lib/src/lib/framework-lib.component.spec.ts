import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LibFrameworkComponent } from './framework-lib.component';

describe('LibFrameworkComponent', () => {
  let component: LibFrameworkComponent;
  let fixture: ComponentFixture<LibFrameworkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LibFrameworkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LibFrameworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

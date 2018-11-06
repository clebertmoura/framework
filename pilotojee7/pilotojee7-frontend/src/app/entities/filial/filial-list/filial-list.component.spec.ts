//
// Source code generated by Celerio, a Jaxio product.
// Documentation: http://www.jaxio.com/documentation/celerio/
// Follow us on twitter: @jaxiosoft
// Need commercial support ? Contact us: info@jaxio.com
// Template celerio-pack-frontend:src/app/entities/entity/entity-list/entity-list.component.spec.ts.e.vm
// Template is part of Open Source Project: https://github.com/jaxio/javaee-lab
//
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FilialListComponent } from './filial-list.component';

describe('FilialListComponent', () => {
  let component: FilialListComponent;
  let fixture: ComponentFixture<FilialListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FilialListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilialListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

//
// Source code generated by Celerio, a Jaxio product.
// Documentation: http://www.jaxio.com/documentation/celerio/
// Follow us on twitter: @jaxiosoft
// Need commercial support ? Contact us: info@jaxio.com
// Template celerio-pack-frontend:src/app/entities/entity/entity-line/entity-line.component.spec.ts.e.vm
// Template is part of Open Source Project: https://github.com/jaxio/javaee-lab
//
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuarioLineComponent } from './usuario-line.component';

describe('UsuarioLineComponent', () => {
  let component: UsuarioLineComponent;
  let fixture: ComponentFixture<UsuarioLineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsuarioLineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsuarioLineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

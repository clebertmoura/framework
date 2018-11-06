//
// Source code generated by Celerio, a Jaxio product.
// Documentation: http://www.jaxio.com/documentation/celerio/
// Follow us on twitter: @jaxiosoft
// Need commercial support ? Contact us: info@jaxio.com
// Template celerio-pack-frontend:src/app/entities/entity/entity.service.ts.e.vm
// Template is part of Open Source Project: https://github.com/jaxio/javaee-lab
//
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MessageService } from '../../util/message.service';
import { EntityService } from '../../framework/service/entity.service';
import { Observable, forkJoin } from 'rxjs';
import { Usuario } from './usuario';

@Injectable()
export class UsuarioService extends EntityService<Usuario> {

  constructor(protected http: HttpClient,protected messageService: MessageService) {
    super(http, messageService, 'Usuario');
  }

  public newEntity(response?: any): Usuario {
    return new Usuario(response, 1);
  }

  public newEntityList(response: any): Usuario[] {
    return Usuario.toArray(response, 1);
  }

  protected flatMapEntityRelations(entity: Usuario): Observable<any[]> {
    const observables: Observable<any>[] = [];

    if (observables.length > 0) {
      return forkJoin(observables);
    } else {
      return ;
    }
  }

  protected mapEntityRelations(entity: Usuario, response: any): Usuario {
    return entity;
  }
  
}

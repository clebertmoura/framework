import { BaseEntity } from './baseEntity';

export abstract class BaseEntityAudited extends BaseEntity {

  /**
   * Atributos básicos da entidade auditável.
   */
  creationDate: Date = null;
  lastModificationDate: Date = null;
  creationAuthor: string = null;
  lastModificationAuthor: string = null;
  status: string = null;

  /**
   * Construtor da entidade.
   * @param json Json da entidade.
   * @param depth Profundidade da serialização.
   */
  constructor(json?: any, depth?: number) {
    super(json, depth);
  }

  /**
   * Responsavel por carregar os campos
   * @param json Json da entidade.
   * @param depth Profundidade da serialização.
   */
  protected loadEntityFields(json?: any, depth?: number): void {
    super.loadEntityFields(json, depth);
    if (json !== undefined && json != null) {
      this.creationDate = json.creationDate;
      this.lastModificationDate = json.lastModificationDate;
      this.creationAuthor = json.creationAuthor;
      this.lastModificationAuthor = json.lastModificationAuthor;
      this.status = json.status;
    }
  }

}

export abstract class BaseEntity {
  /**
   * Atributos básicos da entidade.
   */
  id: any = null;
  version: number = null;
  /**
   * JSON original da entidade.
   */
  _JSON: any;

  /**
   * Construtor da entidade.
   * @param json Json da entidade.
   * @param depth Profundidade da serialização.
   */
  constructor(json?: any, depth?: number) {
    if (json !== undefined) {
      this.loadFromJson(json, depth);
    }
  }

  /**
   * Carrega os atributos da entidade a partir do json.
   * @param json Json da entidade.
   * @param depth Profundidade da serialização.
   */
  public loadFromJson(json?: any, depth?: number) {
    if (json != null) {
      this._JSON = json;
      if (json.id !== undefined) {
        this.loadEntityFields(json, depth);
      } else {
        if (json.links && json.links.length > 0) {
          const links = json.links.filter(l => l.rel === 'canonical');
          const index = links[0].href.lastIndexOf('/');
          this.id = links[0].href.substr(index + 1);
        }
      }
    }
  }

  /**
   * Responsavel por carregar os campos
   */
  protected loadEntityFields(json?: any, depth?: number): void {
    if (json !== undefined && json != null) {
      this.id = json.id;
      this.version = json.version;
    }
  }

}

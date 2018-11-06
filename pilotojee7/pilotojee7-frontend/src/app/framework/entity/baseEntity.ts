export abstract class BaseEntity {
    // Raw attributes
    id: any;
    version: number;
    // Json original
    _json: any;

    constructor(json?: any, depth?: number) {
        if (json != null) {
            this._json = json;
            if (json.id) {
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
        this.id = json.id;
        this.version = json.version;
    }

}

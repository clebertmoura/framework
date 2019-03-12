import { BaseEntity } from './baseEntity';

export abstract class BaseEntityAudited extends BaseEntity {
    // Raw attributes
    creationDate: Date;
    lastModificationDate: Date;
    creationAuthor: string;
    lastModificationAuthor: string;
    status: string;

    constructor(json?: any, depth?: number) {
        super(json, depth);
    }

    /**
     * Responsavel por carregar os campos
     */
    protected loadEntityFields(json?: any, depth?: number): void {
        super.loadEntityFields(json, depth);
        this.creationDate = json.creationDate;
        this.lastModificationDate = json.lastModificationDate;
        this.creationAuthor = json.creationAuthor;
        this.lastModificationAuthor = json.lastModificationAuthor;
        this.status = json.status;
    }

}

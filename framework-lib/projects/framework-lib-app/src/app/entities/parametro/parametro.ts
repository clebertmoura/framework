import { BaseEntity } from 'framework-lib';

export class Parametro extends BaseEntity {
    // Raw attributes
    descricao: string;
    valor: string;
    chave: string;
    dataHoraAlteracao: Date;
    tipoParametro: string;

    constructor(json?: any, depth?: number) {
        super(json, depth);
    }

    /**
     * Converter um array de json em um array de objetos.
     */
    static toArray(jsons: any[], depth?: number): Parametro[] {
        const entities: Parametro[] = [];
        if (jsons != null) {
            for (const json of jsons) {
                entities.push(new Parametro(json, depth));
            }
        }
        return entities;
    }

    /**
     * Responsavel por carregar os campos
     */
    protected loadEntityFields(json?: any, depth?: number): void {
        super.loadEntityFields(json);
        this.descricao = json.descricao;
        this.valor = json.valor;
        this.chave = json.chave;
        if (json.dataHoraAlteracao != null) {
            this.dataHoraAlteracao = new Date(json.dataHoraAlteracao);
        }
        this.tipoParametro = json.tipoParametro;
        if (depth && depth-- > 0) {
        }
    }
}

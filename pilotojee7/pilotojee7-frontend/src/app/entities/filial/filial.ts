//
// Source code generated by Celerio, a Jaxio product.
// Documentation: http://www.jaxio.com/documentation/celerio/
// Follow us on twitter: @jaxiosoft
// Need commercial support ? Contact us: info@jaxio.com
// Template celerio-pack-frontend:src/app/entities/entity/entity.ts.e.vm
// Template is part of Open Source Project: https://github.com/jaxio/javaee-lab
//
import { Empresa } from '../empresa/empresa';

import { BaseEntity } from '../../framework/entity/baseEntity';

export class Filial extends BaseEntity {
    // Raw attributes
    versaoEntidade: number;
    dhCriacao: Date;
    dhModificacao: Date;
    flHabilitado: number;
    nmLogin: string;
    codigo: string;
    nomeFantasia: string;
    razaoSocial: string;
    nmLoginAlteracao: string;
    // x-to-one
    idEmpresa: Empresa;

    constructor(json?: any, depth?: number) {
        super(json, depth);
    }

    /**
     * Converter um array de json em um array de objetos.
     */
    static toArray(jsons: any[], depth?: number): Filial[] {
        const entities: Filial[] = [];
        if (jsons != null) {
            for (const json of jsons) {
                entities.push(new Filial(json, depth));
            }
        }
        return entities;
    }

    /**
     * Responsavel por carregar os campos
     */
    protected loadEntityFields(json?: any, depth?: number): void {
        super.loadEntityFields(json);
        this.versaoEntidade = json.versaoEntidade;
        if (json.dhCriacao != null) {
            this.dhCriacao = new Date(json.dhCriacao);
        }
        if (json.dhModificacao != null) {
            this.dhModificacao = new Date(json.dhModificacao);
        }
        this.flHabilitado = json.flHabilitado;
        this.nmLogin = json.nmLogin;
        this.codigo = json.codigo;
        this.nomeFantasia = json.nomeFantasia;
        this.razaoSocial = json.razaoSocial;
        this.nmLoginAlteracao = json.nmLoginAlteracao;
        if (depth && depth-- > 0) {
            if (json.idEmpresa != null) {
                this.idEmpresa = new Empresa(json.idEmpresa, depth);
            }
        }
    }
}

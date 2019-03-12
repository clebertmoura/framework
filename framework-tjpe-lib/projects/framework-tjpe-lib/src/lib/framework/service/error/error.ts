import { ErrorLayer } from './errorlayer';

export class Error {

    timestamp: number;
    layer: ErrorLayer;
    httpStatus?: number;
    message: string;
    errors: Error[];

    businessRuleCode?: number;

    rootBean?: string;
    invalidValue?: any;
    propertyPath?: string;

    _json: any;

    constructor(json?: any) {
        if (json != null) {
            this._json = json;

            this.httpStatus = json.httpStatus;
            this.layer = ErrorLayer[json.layer + ''];
            this.message = json.message;
            this.timestamp = json.timestamp;

            if (json.errors && json.errors.length > 0) {
                this.errors = [];
                for (let i = 0; i < json.errors.length; i++) {
                    this.errors.push(new Error(json.errors[i]));
                }
            }
            this.businessRuleCode = json.businessRuleCode;

            this.rootBean = json.rootBean;
            this.invalidValue = json.invalidValue;
            this.propertyPath = json.propertyPath;
        }
    }

    /**
     * Converter um array de json em um array de objetos.
     * @param jsons array
     */
    static toArray(jsons: any[]): Error[] {
        const items: Error[] = [];
        if (jsons != null) {
            for (const json of jsons) {
                items.push(new Error(json));
            }
        }
        return items;
    }
}

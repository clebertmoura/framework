import { Operator } from './operator';

export class FilterMetadata {

    public value: any | any[];
    public operator: Operator;

    constructor(value?: any | any[], operator?: Operator) {
        this.value = value;
        this.operator = operator;
    }
}

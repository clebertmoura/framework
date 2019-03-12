import { Input } from '@angular/core';
import { ControlValueAccessor } from '@angular/forms';
import { EntityService } from '../service/entity.service';
import { BaseEntity } from '../entity/baseEntity';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export abstract class EntityCompleteComponent<E extends BaseEntity, S extends EntityService<E>>
    implements ControlValueAccessor {

  @Input() disabled = false;
  @Input() required = false;
  @Input() id: string;
  @Input() name: string;
  @Input() label: string;
  @Input() placeholder: string;
  @Input() displayfield: string;
  @Input() sortField: string;
  @Input() sortOrder: string;
  @Input() maxItems = 10;
  @Input() dropdown = false;
  @Input() multiple = false;

  // The internal data model
  private _value: E = null;
  private _values: E[] = [];

  public suggestions: E[] = [];

  // Placeholders for the callbacks
  private _onTouchedCallback: () => void = () => {};
  private _onChangeCallback: (_: any) => void = () => {};

  constructor(
    protected entityService: S
  ) {}

  @Input()
  get value(): any {
    return this._value;
  }

  // set accessor including call the onchange callback
  set value(v: any) {
    if (this._value != null && (v == null || v === '')) {
      this.select(null);
    }
    // nop, see writeValue and select method
  }

  @Input()
  get values(): any {
    return this._values;
  }

  // set accessor including call the onchange callback
  set values(v: any) {
    if (this._values != null && (v == null || v === '')) {
      this.select(null);
    }
    // nop, see writeValue and select method
  }

  // Set touched on blur
  onTouched() {
    this._onTouchedCallback();
  }

  private isMultiple(): boolean {
    return (this.multiple && this.multiple.toString() === 'true');
  }

  // From ControlValueAccessor interface
  writeValue(value: any) {
    if (this.isMultiple()) {
      if (value != null) {
        if (Array.isArray(value)) {
          value.map(item => {
            this._values.push(<E> item);
          });
        } else {
          this._values.push(<E> value);
        }
      }
    } else {
      this._value = <E> value;
    }
  }

  // From ControlValueAccessor interface
  registerOnChange(fn: any) {
    this._onChangeCallback = fn;
  }

  // From ControlValueAccessor interface
  registerOnTouched(fn: any) {
    this._onTouchedCallback = fn;
  }

  // From ControlValueAccessor interface
  setDisabledState(isDisabled: boolean) {}

  complete(event: any) {
    this.entityService
      .findPage(event.query, null, this.sortField, this.sortOrder, 0, this.maxItems)
      .pipe(catchError(error => throwError(error)))
      .subscribe(response => {
        this.suggestions = response.results;
      });
  }

  select(value: any) {
    this.writeValue(value);
    this._onChangeCallback(value);
  }

}

import { Component, OnInit, Input, forwardRef, Injectable } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import * as moment from 'moment';
import {NgbDateAdapter, NgbDateNativeAdapter, NgbDatepickerI18n, NgbDateStruct, NgbTimeStruct} from '@ng-bootstrap/ng-bootstrap';

const DATE_FORMAT = 'DD/MM/YYYY';

const DATETIME_FORMAT = 'DD/MM/YYYY HH:mm';

const I18N_VALUES = {
  'pt': {
    weekdays: ['Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'],
    months: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
  }
};

@Injectable()
export class I18n {
  language = 'pt';
}

@Injectable()
export class CustomDatepickerI18n extends NgbDatepickerI18n {

  constructor(private _i18n: I18n) {
    super();
  }

  getWeekdayShortName(weekday: number): string {
    return I18N_VALUES[this._i18n.language].weekdays[weekday - 1];
  }
  getMonthShortName(month: number): string {
    return I18N_VALUES[this._i18n.language].months[month - 1];
  }
  getMonthFullName(month: number): string {
    return this.getMonthShortName(month);
  }

  getDayAriaLabel(date: NgbDateStruct): string {
    return `${date.day}/${date.month}/${date.year}`;
  }
}

@Component({
  selector: 'app-datetime-picker',
  templateUrl: './datetime-picker.component.html',
  styleUrls: ['./datetime-picker.component.scss'],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => DatetimePickerComponent),
    multi: true
  }, {
    provide: NgbDateAdapter,
    useClass: NgbDateNativeAdapter
  },
  I18n,
  {
    provide: NgbDatepickerI18n,
    useClass: CustomDatepickerI18n}]
})

export class DatetimePickerComponent implements ControlValueAccessor, OnInit {

  @Input() disabled = false;
  @Input() required = false; // TODO
  @Input() id: string;
  @Input() showMaskTyped = false;
  @Input() timePicker = false;
  @Input() placement = 'bottom-left';
  @Input() orientation = 'auto'; // auto | cols | rows

  private _value: Date;
  private _dateString: string;
  private _ngbTime: NgbTimeStruct;
  private _ngbDate: Date;

  set ngbDate(ngbDate: Date) {
    this._ngbDate = ngbDate;
    if (this.timePicker && this._ngbTime != null) {
      this.dateString = moment(this._ngbDate).hour(this._ngbTime.hour).minute(this._ngbTime.minute).format(DATETIME_FORMAT);
    } else {
      this.dateString = moment(this._ngbDate).format(DATE_FORMAT);
    }
  }
  get ngbDate(): Date {
    return this._ngbDate;
  }

  set ngbTime(ngbTime: NgbTimeStruct) {
    this._ngbTime = ngbTime;
    if (this.timePicker && this._value != null) {
      this.dateString = moment(this._value).hour(this._ngbTime.hour).minute(this._ngbTime.minute).format(DATETIME_FORMAT);
    }
  }
  get ngbTime(): NgbTimeStruct {
    return this._ngbTime;
  }

  set dateString(dateString: string) {
    this.writeValue(moment(dateString, this.timePicker ? DATETIME_FORMAT : DATE_FORMAT).toDate());
  }
  get dateString(): string {
    return this._dateString;
  }

  get value(): Date {
    return this._value;
  }

  selectNgbDate(): any {
    return {year: moment(this.ngbDate).year(), month: moment(this.ngbDate).month() + 1};
  }

  onChange = (date: Date) => {};

  onTouched = () => {};

  propagateChange: any = () => {};

  writeValue(value: any): void {
    this._value = value;
    this._ngbDate = this._value;
    if (this.timePicker) {
      this._ngbTime = {
        hour: moment(this._value).hour(),
        minute: moment(this._value).minute(),
        second: 0};
      this._dateString = moment(this._value).hour(this._ngbTime.hour).minute(this._ngbTime.minute).format(DATETIME_FORMAT);
    } else {
      this._dateString = moment(this._value).format(DATE_FORMAT);
    }
    this.onChange(this.value);
  }

  registerOnChange(fn: (date: Date) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  constructor() {}

  ngOnInit() {}

}

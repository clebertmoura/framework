import { Input, OnInit, OnDestroy } from '@angular/core';
import { ControlValueAccessor } from '@angular/forms';
import { MessageService } from '../util/message.service';
import { EntityService } from '../service/entity.service';
import { BaseEntity } from '../entity/baseEntity';
import { throwError, of, Subject } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, tap, switchMap } from 'rxjs/operators';
import { FilterMetadata } from '../service/paging/filtermetadata';
import { HttpErrorResponse } from '@angular/common/http';
import { isArray } from 'util';
import { Error } from '../service/error/error';
import { ErrorLayer } from '../service/error/errorlayer';

export abstract class EntityCompleteComponent<E extends BaseEntity, S extends EntityService<E>>
    implements ControlValueAccessor, OnInit, OnDestroy {

  @Input() disabled = false;
  @Input() required = false;
  @Input() id: string;
  @Input() name: string;
  @Input() label: string;
  @Input() placeholder: string;
  @Input() displayfield: string;
  @Input() sortField: string;
  @Input() sortOrder: string;
  @Input() maxItems = -1;
  @Input() dropdown = false;
  @Input() loadOnInit = true;
  @Input() multiple = false;

  // The internal data model
  private internalValue: E = null;
  private internalValues: E[] = [];
  private internalFilters: { [s: string]: FilterMetadata } = {};

  public suggestions: E[] = [];

  public loading;

  public searchInput$ = new Subject<string>();

  // Placeholders for the callbacks
  private onTouchedCallback: () => void = () => {};
  private onChangeCallback: (_: any) => void = () => {};

  constructor(
    protected entityService: S,
    protected messageService: MessageService
  ) {}

  ngOnInit() {
    if (this.dropdown && this.loadOnInit) {
      this.fetchByFilter();
    }

    this.searchInput$.pipe(
      distinctUntilChanged(),
      debounceTime(200),
      tap(() => this.loading = true),
      switchMap(
        term => this.entityService
        .findPage(term, null, this.sortField, this.sortOrder, 0, this.maxItems).pipe(
            map(response =>  response.results),
            tap(() => this.loading = false),
            catchError(() => {
              this.loading = false;
              return of([]);
            }))
      )
    ).subscribe(items => {
      this.suggestions = items;
    }, (err) => {
      this.suggestions = [];
      this.handleError(err);
    });

  }

  public handleError(httpError: HttpErrorResponse) {
    if (httpError.status === 400) {
        if (httpError.error && isArray(httpError.error)) {
          const errors = Error.toArray(httpError.error);
          for (const e of errors) {
            switch (e.layer) {
              case ErrorLayer.BEAN_VALIDATION:
                  this.messageService.warning(e.propertyPath + ': ' + e.message);
                  break;
              case ErrorLayer.BUSINESS:
                  this.messageService.warning(e.message);
                  break;
              default:
                  this.messageService.error(e.message);
                  break;
            }
          }
        } else {
          this.messageService.error('Erro ao consultar!');
        }
    } else {
      this.messageService.error('Erro ao consultar!');
    }
  }

  ngOnDestroy() {
    this.searchInput$.unsubscribe();
  }

  @Input()
  get value(): any {
    return this.internalValue;
  }

  // set accessor including call the onchange callback
  set value(v: any) {
    if (this.internalValue != null && (v == null || v === '')) {
      this.select(null);
    }
    // nop, see writeValue and select method
  }

  @Input()
  get values(): any {
    return this.internalValues;
  }

  // set accessor including call the onchange callback
  set values(v: any) {
    if (this.internalValues != null && (v == null || v === '')) {
      this.select(null);
    }
    // nop, see writeValue and select method
  }

  @Input()
  get filters(): any {
    return this.internalFilters;
  }

  // set accessor including call the onchange callback
  set filters(filters: any) {
    this.suggestions = [];
    this.internalFilters = filters;
    if (this.internalFilters != null && Object.keys(filters).length > 0) {
      // fetch by filter
      this.fetchByFilter();
    }
    // nop, see writeValue and select method
  }

  // Set touched on blur
  onTouched() {
    this.onTouchedCallback();
  }

  private fetchByFilter(): void {
    this.entityService
      .findPage('', this.internalFilters, this.sortField, this.sortOrder, 0, this.maxItems)
      .pipe(catchError(error => throwError(error)))
      .subscribe(response => {
        this.suggestions = response.results;
      });
  }

  public isMultiple(): boolean {
    return (this.multiple && this.multiple.toString() === 'true');
  }

  // From ControlValueAccessor interface
  writeValue(value: any) {
    if (this.isMultiple()) {
      if (value != null) {
        if (Array.isArray(value)) {
          value.map(item => {
            this.internalValues.push(item as E);
          });
        } else {
          this.internalValues.push(value as E);
        }
      }
    } else {
      this.internalValue = value as E;
    }
  }

  // From ControlValueAccessor interface
  registerOnChange(fn: any) {
    this.onChangeCallback = fn;
  }

  // From ControlValueAccessor interface
  registerOnTouched(fn: any) {
    this.onTouchedCallback = fn;
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
    this.onChangeCallback(value);
  }
}


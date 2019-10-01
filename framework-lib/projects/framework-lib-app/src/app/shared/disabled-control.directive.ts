import { NgControl } from '@angular/forms';
import { Directive, Input, Optional, ElementRef } from '@angular/core';

@Directive({
  // tslint:disable-next-line:directive-selector
  selector: '[disableControl]'
})
export class DisableControlDirective {

  @Input() set disableControl(condition: boolean) {
    this.elr.nativeElement.disabled = condition;
  }

  constructor(@Optional() private elr: ElementRef) {
  }

}

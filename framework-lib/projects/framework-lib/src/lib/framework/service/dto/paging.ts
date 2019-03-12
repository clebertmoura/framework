import { LazyLoadEvent } from './lazyloadevent';
import { SortMeta } from './sortmeta';
import { FilterMetadata } from './filtermetadata';

export class PageResponse<E> {
  constructor(
    public totalPages: number,
    public totalElements: number,
    public items: E[]
  ) {}

  // remove the passed element from the content array.
  remove(element: E) {
    const indexToRemove: number = this.items.indexOf(element);
    this.items = this.items.filter((val, i) => i !== indexToRemove);
    this.totalElements = this.totalElements - 1;
  }
}

export class PageRequestByExample<E> {
  constructor(public example: E, public lazyLoadEvent: LazyLoadEvent) {}
}


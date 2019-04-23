export class PageResponse<E> {
  /**
   * Lista de registros
   */
  results: E[];
  /**
   * Total de registros
   */
  totalRegisters: number;

  // remove the passed element from the content array.
  remove(element: E) {
    const indexToRemove: number = this.results.indexOf(element);
    this.results = this.results.filter((val, i) => i !== indexToRemove);
    this.totalRegisters = this.totalRegisters - 1;
  }


}

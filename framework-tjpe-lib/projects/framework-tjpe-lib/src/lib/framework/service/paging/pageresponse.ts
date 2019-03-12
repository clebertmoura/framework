export class PageResponse<E> {
  /**
   * Lista de registros
   */
  results: E[];
  /**
   * Total de registros
   */
  totalRegisters: number;

}

import { SortMeta } from './sortmeta';
import { FilterMetadata } from './filtermetadata';
import { Order } from './order';

export class PageRequest {
  /**
   * First row offset.
   */
  first: number;
  /**
   * Number of rows per page.
   */
  rows: number;
  /**
   * The sorting field
   */
  sortField?: string;
  /**
   * The sorting order
   */
  sortOrder?: Order;
  /**
   * Lista campos para ordenação.
   */
  multiSortMeta?: SortMeta[];
  /**
   * Indica o operando a ser utilizado nos filtros, por default utiliza o: AND
   */
  andOperand = true;
  /**
   * Lista dos filtros
   */
  filters?: { [s: string]: FilterMetadata };
  /**
   * Filtro geral
   */
  globalFilter?: string;
  /**
   * Nome do EntityGraph a ser utilizado para carregamento
   */
  entityGraph?: string;
  /**
   * A profundidade da serialização dos relacionamentos.
   */
  depth?: number;
}

export class PageResponse<T> {
  size: number;
  totalPages: number;
  results: Array<T>;
}

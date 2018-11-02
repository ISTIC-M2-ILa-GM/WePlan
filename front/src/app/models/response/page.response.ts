export interface PageResponse<T> {
  size: number;
  totalPages: number;
  results: Array<T>;
}

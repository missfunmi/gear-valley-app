import { FetchStatus } from 'types/enums'

export type FetchState<T> = {
  data?: T
  error?: string | Error
  status: FetchStatus
}

export type FetchAction<T> =
  | { type: 'REQUEST' }
  | { type: 'SUCCESS'; payload?: T }
  | { type: 'FAILURE'; error: string }

export function fetchReducer<T>(state: FetchState<T>, action: FetchAction<T>) {
  switch (action.type) {
    case 'REQUEST':
      return {
        status: FetchStatus.Loading,
      }
    case 'SUCCESS':
      return {
        status: FetchStatus.Loaded,
        data: action.payload,
      }
    case 'FAILURE':
      return {
        status: FetchStatus.Error,
        error: action.error,
      }
    default:
      return state
  }
}

export type FetchReducer<T> = (state: FetchState<T>, action: FetchAction<T>) => FetchState<T>

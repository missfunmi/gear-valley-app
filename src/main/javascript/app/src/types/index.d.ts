import { FetchStatus } from './enums'

export interface IImage {
  base64Image: string
  contentType: string
}
export interface IGear {
  title: string
  price: number
  description: string
  url: string
  image: IImage
}
export interface ISearchResult {
  providerId: string
  providerName: string
  providerHomePage: string
  providerLogo: string
  gear: Array<IGear>
}
export interface ISearchResultWrapper {
  keyword: string
  results: Array<ISearchResult>
}
export interface IAddWatchRequest {
  keyword: string
  providerId: string
  gear: IGear
}
export interface IPriceWatch {
  id: string
  created: Date
  lastModified: Date
  watchId: string
  keyword: string
  title: string
  description: string
  providerId: string
  url: string
  lastPriceCheck: Date
  currentPrice: number
  startingPrice: number
  image: string | null
  active: boolean
}
export interface IPriceWatchResult {
  data: Array<IPriceWatch>
}
export interface IServiceResponse<T> {
  data?: T
  error?: string | Error
  status: FetchStatus
}
interface IAction<T> {
  type: 'SUCCESS' | 'REQUEST' | 'FAILURE'
  error?: string | Error
  payload?: T
}

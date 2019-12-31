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
  watchActive: boolean
  watchId?: string
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
  currentPrice: ISpotPrice
  priceHistory: Array<ISpotPrice>
  image: IImage
  active: boolean
}
export interface IPriceWatchResult {
  data: Array<IPriceWatch>
}
export interface ISpotPrice {
  price: number
  dateOfCheck: Date
}

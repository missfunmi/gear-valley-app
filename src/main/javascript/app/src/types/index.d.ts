export interface IImage {
  base64Image: string;
  contentType: string;
};
export interface IGear {
  title: string;
  price: number;
  description: string;
  url: string;
  image: IImage
};
export interface ISearchResult {
  providerId: string;
  providerName: string;
  providerHomePage: string;
  providerLogo: string;
  gear: Array<IGear>
};
export interface ISearchResultWrapper {
  keyword: string;
  results: Array<ISearchResult>
};
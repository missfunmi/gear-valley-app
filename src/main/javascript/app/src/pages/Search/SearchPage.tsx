import React from 'react';
import { List } from 'office-ui-fabric-react/lib/List';
import { SearchBox } from 'office-ui-fabric-react/lib/SearchBox';
import { Spinner } from 'office-ui-fabric-react/lib/Spinner';
import { Stack } from 'office-ui-fabric-react/lib/Stack';
import './SearchPage.css';

interface ISearchPageProps {};
interface ISearchPageState {
  error: string | null;
  loading: boolean;
  searchResult: ISearchResultWrapper | null;
};
interface IGearImage {
  base64Image: string;
  contentType: string;
};
interface IGear {
  title: string;
  price: number;
  description: string;
  url: string;
  image: IGearImage
};
interface ISearchResult {
  providerId: string;
  providerName: string;
  providerHomePage: string;
  providerLogo: string;
  gear: Array<IGear>
};
interface ISearchResultWrapper {
  keyword: string;
  results: Array<ISearchResult>
};
// tslint:disable:jsx-no-lambda
class SearchPage extends React.Component<ISearchPageProps, ISearchPageState> {
  constructor(props: ISearchPageProps) {
    super(props);
    this.state = {
      error: null,
      loading: false,
      searchResult: null
    };
  }

  handleSearch = async (searchTerm: string) => {
    this.setState({loading: true, error: null});
    try {
      const request = { keyword: searchTerm };
      const res = await fetch('api/v1/search', {
        method: 'POST',
        body: JSON.stringify(request),
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        }
      });
      const json = await res.json();
      this.setState({searchResult: json, loading: false});
    } catch(err) {
      this.setState({
        error: err,
        loading: false
      })
    }
  }

  renderLoader() {
    return (
      <div>
        <Spinner label="Searching for your gear..." ariaLive="assertive" labelPosition="top" />
      </div>
    )
  }

  renderResult(item?: ISearchResult) {
    return (item &&
      <div>
        <p><a href={item.providerHomePage}>{item.providerName}</a></p>
        {item.gear.map((gear: IGear) => {
          return (
            <p><img src={`data:${gear.image.contentType};base64,${gear.image.base64Image}`} alt={gear.title}/><a href={gear.url}>{gear.title}</a> ({gear.description}) - ${gear.price}</p>
          )
        })}
      </div>
    )
  }

  public render(): JSX.Element {
    const { error, loading, searchResult } = this.state;
    return (
      <Stack tokens={{ childrenGap: 20, padding: 10 }}>
        <SearchBox
          placeholder="Search"
          onSearch={this.handleSearch}
          onFocus={() => console.log('onFocus called')}
          onBlur={() => console.log('onBlur called')}
          onChange={() => console.log('onChange called')}
        />
        {loading && this.renderLoader()}
        {searchResult && <p>Results for "{searchResult.keyword}"</p>}
        {searchResult && <List items={searchResult.results} onRenderCell={this.renderResult} />}
        {error && <p>{JSON.stringify(error)}</p>}
      </Stack>
    );
  }
}

export default SearchPage;

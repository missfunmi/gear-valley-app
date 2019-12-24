import React from 'react';
import { SearchBox } from 'office-ui-fabric-react/lib/SearchBox';
import { Spinner } from 'office-ui-fabric-react/lib/Spinner';
import { Stack } from 'office-ui-fabric-react/lib/Stack';
import './SearchPage.css';

interface ISearchPageProps {};
interface ISearchPageState {
  error: string | null;
  loading: boolean;
  items: any;
};
// tslint:disable:jsx-no-lambda
class SearchPage extends React.Component<ISearchPageProps, ISearchPageState> {
  constructor(props: ISearchPageProps) {
    super(props);
    this.state = {
      error: null,
      loading: false,
      items: []
    };
  }

  handleSearch = async (searchTerm: string) => {
    this.setState({loading: true, error: null});
    try {
      const request = { keyword: searchTerm };
      const res = await fetch('api/v1/search', { method: 'POST', body: JSON.stringify(request)});
      const json = await res.json();
      this.setState({items: json});
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

  public render(): JSX.Element {
    const { error, loading, items } = this.state;
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
        <p>{JSON.stringify(items)}</p>
        <p>{JSON.stringify(error)}</p>
      </Stack>
    );
  }
}

export default SearchPage;

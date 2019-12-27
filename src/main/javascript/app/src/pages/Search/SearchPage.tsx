import React from 'react';
import { MessageBar, MessageBarType } from 'office-ui-fabric-react/lib/MessageBar';
import { Pivot, PivotItem } from 'office-ui-fabric-react/lib/Pivot';
import { SearchBox, ISearchBoxStyles } from 'office-ui-fabric-react/lib/SearchBox';
import { Spinner } from 'office-ui-fabric-react/lib/Spinner';
import { Stack, IStackTokens } from 'office-ui-fabric-react/lib/Stack';
import { ISearchResult, ISearchResultWrapper } from 'types';
import { SearchResult } from 'components/Search';

interface ISearchPageProps {};
interface ISearchPageState {
  error: string | null;
  loading: boolean;
  searchResult: ISearchResultWrapper | null;
};

const stackTokens: IStackTokens = {
  childrenGap: 5,
  padding: 10
};

const searchInputStyles: ISearchBoxStyles = {
  root: {
    fontSize: 16
  }
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
      if (!res.ok) {
        console.error(json);
        throw new Error('Error fetching results')
      }
      this.setState({searchResult: json, loading: false});
    } catch(err) {
      console.error(err)
      this.setState({
        error: err,
        loading: false
      })
    }
  }

  handleErrorDismiss = () => {
    this.setState({error: null});
  }

  renderLoader() {
    return (
      <div>
        <Spinner label="Searching for your gear..." ariaLive="assertive" labelPosition="top" />
      </div>
    )
  }

  renderItem(result: ISearchResult) {
    return (
      <PivotItem headerText={`${result.providerName} (${result.gear.length})`}>
        <div style={{marginTop: 12}}>
        <SearchResult result={result} />
        </div>
      </PivotItem>
    )
  }

  renderError(error: any) {
    return (
      <MessageBar
        messageBarType={MessageBarType.error}
        isMultiline={false}
        onDismiss={this.handleErrorDismiss}
        dismissButtonAriaLabel="Close"
      >
        Well that's embarrassing. There was an error fetching your result. Try again or contact us.
      </MessageBar>
    )
  }

  public render(): JSX.Element {
    const { error, loading, searchResult } = this.state;
    return (
      <Stack tokens={stackTokens}>
        <SearchBox
          placeholder="Search"
          onSearch={this.handleSearch}
          styles={searchInputStyles}
          onFocus={() => console.log('onFocus called')}
          onBlur={() => console.log('onBlur called')}
          onChange={() => console.log('onChange called')}
        />
        {error && this.renderError(error)}
        {loading && this.renderLoader()}
        {searchResult && <p>Results for "<b>{searchResult.keyword}</b>"</p>}
        {searchResult && (
          <Pivot aria-label="Search Results">
            {searchResult.results.map(this.renderItem)}
          </Pivot>
        )}
      </Stack>
    );
  }
}

export default SearchPage;

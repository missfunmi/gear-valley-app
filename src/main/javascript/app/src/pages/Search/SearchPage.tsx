import React, { useState } from 'react'
import { MessageBar, MessageBarType } from 'office-ui-fabric-react/lib/MessageBar'
import { Pivot, PivotItem } from 'office-ui-fabric-react/lib/Pivot'
import { SearchBox, ISearchBoxStyles } from 'office-ui-fabric-react/lib/SearchBox'
import { Spinner } from 'office-ui-fabric-react/lib/Spinner'
import { Stack, IStackTokens } from 'office-ui-fabric-react/lib/Stack'
import { FetchStatus } from 'types/enums'
import { IAddWatchRequest, IGear, ISearchResult } from 'types'
import { SearchResult } from 'components/Search'
import { useSearchService } from 'services'

const stackTokens: IStackTokens = {
  childrenGap: 5,
  padding: 10,
}

const searchInputStyles: ISearchBoxStyles = {
  root: {
    fontSize: 16,
  },
}

// tslint:disable:jsx-no-lambda
const SearchPage: React.FC = () => {
  const [keyword, setKeyword] = useState<string>('')
  const searchResponse = useSearchService(keyword)
  const [showError, setShowError] = useState<boolean>(true)
  const handleAddWatch = async (result: ISearchResult, gear: IGear) => {
    try {
      const addWatchRequest: IAddWatchRequest = {
        keyword: keyword,
        providerId: result.providerId,
        gear: gear,
      }
      const res = await fetch('api/v1/priceWatches', {
        method: 'POST',
        body: JSON.stringify(addWatchRequest),
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
      })
      const json = await res.json()
      if (!res.ok) {
        console.error(json)
        throw new Error('Error adding price watch')
      }
    } catch (err) {
      console.error(err)
      alert('Error adding price watch')
    }
  }

  const handleErrorDismiss = () => {
    setShowError(false)
  }

  const renderLoader = () => {
    return (
      <div>
        <Spinner label="Searching for your gear..." ariaLive="assertive" labelPosition="top" />
      </div>
    )
  }

  const renderItem = (result: ISearchResult) => {
    return (
      <PivotItem
        headerText={`${result.providerName} (${result.gear.length})`}
        key={result.providerId}
      >
        <div style={{ marginTop: 12 }}>
          <SearchResult result={result} onAddPriceWatch={handleAddWatch} />
        </div>
      </PivotItem>
    )
  }

  const renderError = (error: any) => {
    return (
      <MessageBar
        messageBarType={MessageBarType.error}
        isMultiline={false}
        onDismiss={handleErrorDismiss}
        dismissButtonAriaLabel="Close"
      >
        Well that's embarrassing. There was an error fetching your result. Try again or contact us.
      </MessageBar>
    )
  }

  return (
    <Stack tokens={stackTokens}>
      <SearchBox
        placeholder="Search"
        onSearch={(keyword: string) => setKeyword(keyword)}
        onClear={() => setKeyword('')}
        styles={searchInputStyles}
      />
      {searchResponse.status === FetchStatus.Error &&
        showError &&
        renderError(searchResponse.error)}
      {searchResponse.status === FetchStatus.Loading && renderLoader()}
      {searchResponse.status === FetchStatus.Loaded && searchResponse.data && (
        <>
          <p>
            Results for "<b>{searchResponse.data?.keyword}</b>"
          </p>
          <Pivot aria-label="Search Results">{searchResponse.data?.results?.map(renderItem)}</Pivot>
        </>
      )}
    </Stack>
  )
}

export default SearchPage

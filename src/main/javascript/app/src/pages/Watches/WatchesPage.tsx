import React, { useState } from 'react'
import { useGetPriceWatchesService } from 'services'
import { FetchStatus } from 'types/enums'
import {
  Spinner,
  MessageBar,
  MessageBarType,
  Stack,
  IStackTokens,
  Toggle,
} from 'office-ui-fabric-react'
import { WatchList } from './WatchList'

const stackTokens: IStackTokens = {
  childrenGap: 5,
  padding: 10,
}

// tslint:disable:jsx-no-lambda
const WatchesPage: React.FC = () => {
  const [showError, setShowError] = useState<boolean>(true)
  const [showInactiveWatches, setShowInactiveWatches] = useState<boolean>(false)
  const getPriceWatchesResponse = useGetPriceWatchesService()

  const handleErrorDismiss = () => {
    setShowError(false)
  }

  const handleShowActiveWatchToggle = () => {
    setShowInactiveWatches(!showInactiveWatches)
  }

  const renderError = (error: any) => {
    return (
      <MessageBar
        messageBarType={MessageBarType.error}
        isMultiline={false}
        onDismiss={handleErrorDismiss}
        dismissButtonAriaLabel="Close"
      >
        Well that's embarrassing. There was an error fetching your watches. Try again or contact us.
      </MessageBar>
    )
  }

  return (
    <Stack tokens={stackTokens}>
      <h4>Price Watches</h4>
      {getPriceWatchesResponse.status === FetchStatus.Error &&
        showError &&
        renderError(getPriceWatchesResponse.error)}
      {getPriceWatchesResponse.status === FetchStatus.Loading && (
        <Spinner label="Loading your price watches..." ariaLive="assertive" labelPosition="top" />
      )}
      {getPriceWatchesResponse.status === FetchStatus.Loaded && getPriceWatchesResponse.data && (
        <>
          <Toggle
            label="Show Inactive Watches"
            inlineLabel
            onText="On"
            offText="Off"
            onChange={handleShowActiveWatchToggle}
            checked={showInactiveWatches}
          />
          <WatchList
            priceWatches={getPriceWatchesResponse.data.data.filter(
              pw => (showInactiveWatches && pw.active) || !showInactiveWatches
            )}
          />
        </>
      )}
    </Stack>
  )
}

export default WatchesPage

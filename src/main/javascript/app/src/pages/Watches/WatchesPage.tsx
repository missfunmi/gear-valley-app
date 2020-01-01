import React, { useState } from 'react'
import {
  useGetPriceWatchesService,
  useDeletePriceWatchService,
  useTogglePriceWatchService,
} from 'services'
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
import { ITogglePriceWatchActiveRequest, IPriceWatch } from 'types'

const stackTokens: IStackTokens = {
  childrenGap: 5,
  padding: 10,
}

// tslint:disable:jsx-no-lambda
const WatchesPage: React.FC = () => {
  const [showError, setShowError] = useState<boolean>(true)
  const [showInactiveWatches, setShowInactiveWatches] = useState<boolean>(true)
  const getPriceWatchesResponse = useGetPriceWatchesService()
  const [watches, setWatches] = useState<Array<IPriceWatch> | undefined>()

  const [toggleRequest, setToggleRequest] = React.useState<
    ITogglePriceWatchActiveRequest | undefined
  >()
  const [watchIdToDelete, setWatchIdToDelete] = React.useState<string | undefined>()
  const useDeletePriceWatchResponse = useDeletePriceWatchService(watchIdToDelete)
  const useTogglePriceWatchResponse = useTogglePriceWatchService(
    toggleRequest?.watchId,
    toggleRequest?.active
  )

  let watchesSnapshot = watches || getPriceWatchesResponse?.data?.data || []

  const handleErrorDismiss = () => {
    setShowError(false)
  }

  const handleShowActiveWatchToggle = () => {
    setShowInactiveWatches(!showInactiveWatches)
  }

  const handleDeleteWatch = async (watchId: string) => {
    setWatchIdToDelete(watchId)
    // remove deleted watch from watches in state
    const updatedWatches = watchesSnapshot.filter(w => w.watchId !== watchId) || []
    setWatches(updatedWatches)
  }

  const handleToggleActive = async (toggleRequest: ITogglePriceWatchActiveRequest) => {
    setToggleRequest(toggleRequest)
    const updatedWatches =
      watchesSnapshot.map(w => {
        if (w.watchId === toggleRequest.watchId) {
          return { ...w, active: toggleRequest.active }
        }
        return w
      }) || []
    setWatches(updatedWatches)
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

  let toggleLabel = 'Show Inactive Watches'
  let noDataLabel = showInactiveWatches
    ? 'You have not created any watches'
    : `No Active Watches. Toggle the "${toggleLabel}" button above to show inactive watches`
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
            priceWatches={watchesSnapshot.filter(pw => pw.active || showInactiveWatches)}
            onDeleteWatch={handleDeleteWatch}
            onToggleActive={handleToggleActive}
            noDataLabel={noDataLabel}
          />
        </>
      )}
    </Stack>
  )
}

export default WatchesPage

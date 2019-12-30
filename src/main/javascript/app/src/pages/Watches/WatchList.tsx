import React from 'react'
import { List } from 'office-ui-fabric-react/lib/List'
import { IPriceWatch } from 'types'
import { WatchCard } from 'components/WatchCard'

interface IWatchListProps {
  priceWatches: Array<IPriceWatch>
  noDataLabel?: string
}

// tslint:disable:jsx-no-lambda
export const WatchList: React.FC<IWatchListProps> = ({ priceWatches, noDataLabel }) => {
  const renderItem = (watch?: IPriceWatch): JSX.Element => (
    <div style={{ marginBottom: 12 }}>
      <WatchCard priceWatch={watch!} />
    </div>
  )
  return priceWatches.length === 0 ? (
    <div>{noDataLabel || 'No Data'}</div>
  ) : (
    <div>
      <List items={priceWatches} onRenderCell={renderItem} />
    </div>
  )
}

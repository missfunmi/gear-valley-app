import React from 'react'
import { List } from 'office-ui-fabric-react/lib/List'
import { IPriceWatch } from 'types'
import { WatchCard } from 'components/WatchCard'

interface IWatchListProps {
  priceWatches: Array<IPriceWatch>
}

// tslint:disable:jsx-no-lambda
export const WatchList: React.FC<IWatchListProps> = ({ priceWatches }) => {
  const renderItem = (watch?: IPriceWatch): JSX.Element => (
    <div style={{ marginBottom: 12 }}>
      <WatchCard priceWatch={watch!} />
    </div>
  )
  return (
    priceWatches && (
      <div>
        <List items={priceWatches} onRenderCell={renderItem} />
      </div>
    )
  )
}

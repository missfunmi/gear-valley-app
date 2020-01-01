import React from 'react'
import { List } from 'office-ui-fabric-react/lib/List'
import { Text } from 'office-ui-fabric-react/lib/Text'
import { IPriceWatch, ITogglePriceWatchActiveRequest } from 'types'
import { WatchCard } from 'components/WatchCard'

interface IWatchListProps {
  priceWatches: Array<IPriceWatch>
  noDataLabel?: string
  onToggleActive: (request: ITogglePriceWatchActiveRequest) => Promise<void>
  onDeleteWatch: (watchId: string) => Promise<void>
}

// tslint:disable:jsx-no-lambda
export const WatchList: React.FC<IWatchListProps> = ({
  priceWatches,
  noDataLabel,
  onDeleteWatch,
  onToggleActive,
}) => {
  console.log(priceWatches)

  const renderItem = (watch?: IPriceWatch): JSX.Element => (
    <div style={{ marginBottom: 12 }}>
      <WatchCard
        priceWatch={watch!}
        onDeleteWatch={onDeleteWatch}
        onToggleActive={onToggleActive}
      />
    </div>
  )
  return priceWatches.length === 0 ? (
    <Text>{noDataLabel || 'No Data'}</Text>
  ) : (
    <div>
      <List items={priceWatches} onRenderCell={renderItem} />
    </div>
  )
}

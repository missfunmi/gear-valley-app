import React from 'react'
import { List } from 'office-ui-fabric-react/lib/List'
import { IGear, ISearchResult } from 'types'
import { GearCard } from 'components/GearCard'

interface ISearchResultProps {
  result: ISearchResult | null
  onAddPriceWatch: (result: ISearchResult, gear: IGear) => Promise<void>
}

// tslint:disable:jsx-no-lambda
export const SearchResult: React.FC<ISearchResultProps> = ({ result, onAddPriceWatch }) => {
  const handleAddPriceWatch = (gear: IGear) => {
    return () => {
      onAddPriceWatch(result!, gear)
    }
  }
  const renderItem = (gear?: IGear): JSX.Element => (
    <div style={{ marginBottom: 12 }}>
      <GearCard gear={gear!} onToggleWatch={handleAddPriceWatch(gear!)} />
    </div>
  )
  return (
    result && (
      <div>
        <List items={result.gear} onRenderCell={renderItem} />
      </div>
    )
  )
}

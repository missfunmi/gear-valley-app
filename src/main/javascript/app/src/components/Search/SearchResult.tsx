import React from 'react'
import { List } from 'office-ui-fabric-react/lib/List'
import { IGear, ISearchResult } from 'types'
import { GearCard } from 'components/GearCard'

interface ISearchResultProps {
  result: ISearchResult | null
}

// tslint:disable:jsx-no-lambda
export const SearchResult: React.FC<ISearchResultProps> = ({ result }) => {
  const renderItem = (gear?: IGear): JSX.Element => (
    <div style={{ marginBottom: 12 }}>
      <GearCard gear={gear!} providerId={result?.providerId!} />
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

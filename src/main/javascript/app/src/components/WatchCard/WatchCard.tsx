import React from 'react'
import format from 'date-fns/format'
import formatDistance from 'date-fns/formatDistance'
import { Text, ITextStyles } from 'office-ui-fabric-react/lib/Text'
import { Card, ICardTokens, ICardSectionStyles, ICardSectionTokens } from '@uifabric/react-cards'
import { FontWeights } from '@uifabric/styling'
import { IPriceWatch, ITogglePriceWatchActiveRequest } from 'types'
import { GearCardImage } from 'components/GearCard/GearCardImage'
import {
  Stack,
  IStackTokens,
  IIconStyles,
  IButtonStyles,
  ActionButton,
} from 'office-ui-fabric-react'

interface IWatchCardProps {
  priceWatch: IPriceWatch
  onToggleActive: (request: ITogglePriceWatchActiveRequest) => Promise<void>
  onDeleteWatch: (watchId: string) => Promise<void>
}

const siteTextStyles: ITextStyles = {
  root: {
    color: '#025F52',
    fontWeight: FontWeights.semibold,
  },
}
const descriptionTextStyles: ITextStyles = {
  root: {
    color: '#333333',
    fontWeight: FontWeights.regular,
  },
}
const helpfulTextStyles: ITextStyles = {
  root: {
    color: '#333333',
    fontWeight: FontWeights.regular,
  },
}
const iconStyles: IIconStyles = {
  root: {
    color: '#0078D4',
    fontSize: 18,
    fontWeight: FontWeights.regular,
    margin: 0,
  },
}
const footerCardSectionStyles: ICardSectionStyles = {
  root: {
    alignSelf: 'stretch',
    borderLeft: '1px solid #F3F2F1',
  },
}
const actionButtonStyles: IButtonStyles = {
  root: {
    margin: 0,
    padding: 0,
  },
}
const footerCardSectionTokens: ICardSectionTokens = {
  padding: '0px 0px 0px 12px',
}

const timestampTextStyles: ITextStyles = {
  root: {
    color: '#333333',
    fontWeight: FontWeights.regular,
  },
}

const cardTokens: ICardTokens = { childrenMargin: 12 }

const priceHistoryStackTokens: IStackTokens = { padding: 9 }

// tslint:disable:jsx-no-lambda
export const WatchCard: React.FC<IWatchCardProps> = ({
  priceWatch,
  onDeleteWatch,
  onToggleActive,
}) => {
  const [showPriceHistory, setShowPriceHistory] = React.useState<boolean>(false)

  const handleToggleActive = () => {
    onToggleActive({ watchId: priceWatch.watchId, active: !priceWatch.active })
  }

  const handleDeleteWatch = () => {
    onDeleteWatch(priceWatch.watchId!)
  }

  return (
    <Card aria-label="Clickable horizontal card " horizontal tokens={cardTokens}>
      <Card.Item>
        <GearCardImage image={priceWatch.image} alt={priceWatch.title} />
      </Card.Item>
      <Card.Section grow={1}>
        <Text styles={descriptionTextStyles}>{priceWatch.title}</Text>
        <Text variant="small" styles={helpfulTextStyles}>
          {priceWatch.description}
        </Text>
        <Text variant="small" styles={siteTextStyles}>
          ${priceWatch.currentPrice.price}{' '}
          <Text variant="small" styles={timestampTextStyles}>
            as of <b>{formatDistance(new Date(priceWatch.currentPrice.dateOfCheck), new Date())}</b>{' '}
            ago
          </Text>
        </Text>
        {showPriceHistory && (
          <div style={{ borderTop: '1px solid #F3F2F1' }}>
            <Stack tokens={priceHistoryStackTokens}>
              {priceWatch.priceHistory.map(sp => (
                <Text>
                  ${sp.price} - {format(new Date(priceWatch.currentPrice.dateOfCheck), 'PPPP')}
                </Text>
              ))}
            </Stack>
          </div>
        )}
      </Card.Section>
      <Card.Section styles={footerCardSectionStyles} tokens={footerCardSectionTokens}>
        <ActionButton
          iconProps={{ iconName: priceWatch.active ? 'Hide' : 'RedEye', styles: iconStyles }}
          title={priceWatch.active ? 'Pause Watch' : 'Activate Watch'}
          onClick={handleToggleActive}
          styles={actionButtonStyles}
        />
        <ActionButton
          iconProps={{ iconName: 'Financial', styles: iconStyles }}
          title="Show Price History"
          onClick={() => setShowPriceHistory(!showPriceHistory)}
          styles={actionButtonStyles}
        />
        <Stack.Item grow={1}>
          <span />
        </Stack.Item>
        <ActionButton
          iconProps={{ iconName: 'Delete', styles: iconStyles }}
          title="Remove Watch"
          onClick={handleDeleteWatch}
          styles={actionButtonStyles}
        />
      </Card.Section>
    </Card>
  )
}

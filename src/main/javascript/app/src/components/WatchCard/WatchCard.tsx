import React from 'react'
import format from 'date-fns/format'
import formatDistance from 'date-fns/formatDistance'
import { Text, ITextStyles } from 'office-ui-fabric-react/lib/Text'
import { Card, ICardTokens } from '@uifabric/react-cards'
import { FontWeights } from '@uifabric/styling'
import { IPriceWatch } from 'types'
import { GearCardImage } from 'components/GearCard/GearCardImage'
import { IIconProps, Stack, CommandButton, IStackTokens } from 'office-ui-fabric-react'

interface IWatchCardProps {
  priceWatch: IPriceWatch
}

// tslint:disable:jsx-no-lambda
export const WatchCard: React.FC<IWatchCardProps> = ({ priceWatch }) => {
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

  const timestampTextStyles: ITextStyles = {
    root: {
      color: '#333333',
      fontWeight: FontWeights.regular,
    },
  }

  const cardTokens: ICardTokens = { childrenMargin: 12 }
  const priceHistoryStackTokens: IStackTokens = { padding: 9 }

  // const hideIcon: IIconProps = { iconName: 'Hide' }
  // const showIcon: IIconProps = { iconName: 'RedEye' }

  const [showPriceHistory, setShowPriceHistory] = React.useState<boolean>(false)
  const onClick = () => setShowPriceHistory(!showPriceHistory)

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
        <div style={{ borderTop: '1px solid #F3F2F1' }}>
          <CommandButton
            toggle
            text={showPriceHistory ? 'Hide Price History' : 'Show Price History'}
            // iconProps={showPriceHistory ? hideIcon : showIcon}
            onClick={onClick}
          />
          {showPriceHistory && (
            <Stack tokens={priceHistoryStackTokens}>
              {priceWatch.priceHistory.map(sp => (
                <Text>
                  ${sp.price} - {format(new Date(priceWatch.currentPrice.dateOfCheck), 'PPPP')}
                </Text>
              ))}
            </Stack>
          )}
        </div>
      </Card.Section>
    </Card>
  )
}

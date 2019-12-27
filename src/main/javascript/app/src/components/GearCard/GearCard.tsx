import React from 'react'
import { ActionButton, IButtonStyles } from 'office-ui-fabric-react/lib/Button'
import { Icon, IIconStyles } from 'office-ui-fabric-react/lib/Icon'
import { Stack } from 'office-ui-fabric-react/lib/Stack'
import { Text, ITextStyles } from 'office-ui-fabric-react/lib/Text'
import { Card, ICardTokens, ICardSectionStyles, ICardSectionTokens } from '@uifabric/react-cards'
import { FontWeights } from '@uifabric/styling'
import { IGear } from 'types'
import { GearCardImage } from './GearCardImage'

interface IGearCardProps {
  gear: IGear
  onToggleWatch: () => void
}

// tslint:disable:jsx-no-lambda
export const GearCard: React.FC<IGearCardProps> = ({ gear, onToggleWatch }) => {
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
      fontSize: 16,
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

  const cardTokens: ICardTokens = { childrenMargin: 12 }
  const footerCardSectionTokens: ICardSectionTokens = {
    padding: '0px 0px 0px 12px',
  }

  return (
    <Card aria-label="Clickable horizontal card " horizontal tokens={cardTokens}>
      <Card.Item>
        <GearCardImage image={gear.image} alt={gear.title} />
      </Card.Item>
      <Card.Section grow={1}>
        <Text styles={descriptionTextStyles}>{gear.title}</Text>
        <Text variant="small" styles={helpfulTextStyles}>
          {gear.description}
        </Text>
        <Text variant="small" styles={siteTextStyles}>
          ${gear.price}
        </Text>
      </Card.Section>
      <Card.Section styles={footerCardSectionStyles} tokens={footerCardSectionTokens}>
        {/*<Icon iconName="RedEye" styles={iconStyles} />*/}
        <ActionButton
          iconProps={{ iconName: 'SingleBookmark', styles: iconStyles }}
          title="Add Watch"
          checked={false}
          onClick={onToggleWatch}
          styles={actionButtonStyles}
        />
        <Stack.Item grow={1}>
          <span />
        </Stack.Item>
        <Icon iconName="MoreVertical" styles={iconStyles} />
      </Card.Section>
    </Card>
  )
}

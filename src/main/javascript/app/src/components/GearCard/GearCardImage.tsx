import React from 'react'
import { IImageProps, Image, ImageFit } from 'office-ui-fabric-react/lib/Image'
import { IImage } from 'types'

interface IGearCardImageProps {
  image: IImage
  alt?: string
}

const imageProps: Partial<IImageProps> = {
  imageFit: ImageFit.centerContain,
  width: 96,
  height: 96,
}

// tslint:disable:jsx-no-lambda
export const GearCardImage: React.FC<IGearCardImageProps> = ({ image, alt }) => {
  return (
    <Image
      {...imageProps}
      src={`data:${image.contentType};base64,${image.base64Image}`}
      alt={alt}
    />
  )
}

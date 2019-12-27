import React from 'react'
import { useGetPriceWatchesService } from 'services'
import { IPriceWatch } from 'types'

interface IWatchesPageProps {}

interface IWatchesPageState {
  error: string | null
  loading: boolean
  watches: Array<IPriceWatch>
}

// tslint:disable:jsx-no-lambda
const WatchesPage: React.FC = () => {
  const getPriceWatchesResponse = useGetPriceWatchesService()

  return <pre>{JSON.stringify(getPriceWatchesResponse, null, 1)}</pre>
}

export default WatchesPage

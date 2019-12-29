import { useEffect, useReducer } from 'react'
import { FetchStatus } from 'types/enums'
import { IPriceWatchResult } from 'types'
import { fetchReducer, FetchState, FetchReducer } from './fetchReducer'

const initialState: FetchState<IPriceWatchResult> = {
  status: FetchStatus.Loaded,
  error: undefined,
  data: undefined,
}

const useGetPriceWatches = () => {
  const searchReducer: FetchReducer<IPriceWatchResult> = fetchReducer
  const [state, dispatch] = useReducer(searchReducer, initialState)

  useEffect(() => {
    ;(async () => {
      try {
        dispatch({ type: 'REQUEST' })
        const res = await fetch('api/v1/priceWatches', {
          method: 'GET',
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
          },
        })
        const json = await res.json()
        if (!res.ok) {
          throw new Error('Error fetching results')
        }
        dispatch({ type: 'SUCCESS', payload: json })
      } catch (err) {
        dispatch({ type: 'FAILURE', error: err })
      }
    })()
  }, [])

  return state
}

export default useGetPriceWatches

import { useEffect, useReducer } from 'react'
import { FetchStatus } from 'types/enums'
import { IPriceWatchResult } from 'types'
import { fetchReducer, FetchState, FetchReducer } from './fetchReducer'

const initialState: FetchState<IPriceWatchResult> = {
  status: FetchStatus.Empty,
}

const useGetPriceWatches = () => {
  const reducer: FetchReducer<IPriceWatchResult> = fetchReducer
  const [state, dispatch] = useReducer(reducer, initialState)

  useEffect(() => {
    ;(async () => {
      try {
        dispatch({ type: 'REQUEST' })
        const res = await fetch('api/v1/priceWatches', {
          method: 'GET',
          headers: {
            Accept: 'application/json',
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

import { useEffect, useReducer } from 'react'
import { FetchStatus } from 'types/enums'
import { ISearchResultWrapper, IAddWatchRequest } from 'types'
import { fetchReducer, FetchState, FetchReducer } from './fetchReducer'

const initialState: FetchState<ISearchResultWrapper> = {
  status: FetchStatus.Loaded,
}

const useAddPriceWatch = (addWatchRequest: IAddWatchRequest | undefined) => {
  const searchReducer: FetchReducer<ISearchResultWrapper> = fetchReducer
  const [state, dispatch] = useReducer(searchReducer, initialState)

  useEffect(() => {
    if (!addWatchRequest) return
    ;(async () => {
      try {
        dispatch({ type: 'REQUEST' })
        const res = await fetch('api/v1/priceWatches', {
          method: 'POST',
          body: JSON.stringify(addWatchRequest),
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
          },
        })
        const json = await res.json()
        if (!res.ok) {
          console.error(json)
          throw new Error('Error adding price watch')
        }
        dispatch({ type: 'SUCCESS' })
      } catch (err) {
        dispatch({ type: 'FAILURE', error: err })
      }
    })()
  }, [addWatchRequest])

  return state
}

export default useAddPriceWatch

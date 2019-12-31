import { useEffect, useReducer } from 'react'
import { FetchStatus } from 'types/enums'
import { ISearchResultWrapper } from 'types'
import { fetchReducer, FetchState, FetchReducer } from './fetchReducer'

const initialState: FetchState<ISearchResultWrapper> = {
  status: FetchStatus.Empty,
}

const useDeletePriceWatch = (watchId: string | undefined) => {
  const reducer: FetchReducer<ISearchResultWrapper> = fetchReducer
  const [state, dispatch] = useReducer(reducer, initialState)

  useEffect(() => {
    ;(async () => {
      try {
        if (!watchId) {
          dispatch({ type: 'CLEAR' })
          return
        }
        dispatch({ type: 'REQUEST' })
        const res = await fetch(`api/v1/priceWatches/${watchId}`, {
          method: 'DELETE',
          headers: {
            Accept: 'application/json',
          },
        })
        if (!res.ok) {
          throw new Error('Error deleting price watch')
        }
        dispatch({ type: 'SUCCESS' })
      } catch (err) {
        dispatch({ type: 'FAILURE', error: err })
      }
    })()
  }, [watchId])

  return state
}

export default useDeletePriceWatch

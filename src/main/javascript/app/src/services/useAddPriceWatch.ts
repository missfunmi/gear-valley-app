import { useEffect, useReducer } from 'react'
import { FetchStatus } from 'types/enums'
import { IPriceWatch, IAddWatchRequest } from 'types'
import { fetchReducer, FetchState, FetchReducer } from './fetchReducer'

const initialState: FetchState<IPriceWatch> = {
  status: FetchStatus.Empty,
}

const useAddPriceWatch = (addWatchRequest: IAddWatchRequest | undefined) => {
  const reducer: FetchReducer<IPriceWatch> = fetchReducer
  const [state, dispatch] = useReducer(reducer, initialState)

  useEffect(() => {
    ;(async () => {
      try {
        if (!addWatchRequest) {
          dispatch({ type: 'CLEAR' })
          return
        }
        dispatch({ type: 'REQUEST' })
        const res = await fetch('api/v1/priceWatches', {
          method: 'POST',
          body: JSON.stringify(addWatchRequest),
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
          },
        })
        if (!res.ok) {
          throw new Error('Error adding price watch')
        }
        const data = await res.json()
        dispatch({ type: 'SUCCESS', payload: data })
      } catch (err) {
        dispatch({ type: 'FAILURE', error: err })
      }
    })()
  }, [addWatchRequest])

  return state
}

export default useAddPriceWatch

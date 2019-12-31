import { useEffect, useReducer } from 'react'
import { FetchStatus } from 'types/enums'
import { IPriceWatch } from 'types'
import { fetchReducer, FetchState, FetchReducer } from './fetchReducer'

const initialState: FetchState<IPriceWatch> = {
  status: FetchStatus.Empty,
}

const useTogglePriceWatch = (watchId: string | undefined, active?: boolean) => {
  const reducer: FetchReducer<IPriceWatch> = fetchReducer
  const [state, dispatch] = useReducer(reducer, initialState)

  useEffect(() => {
    ;(async () => {
      try {
        if (!watchId) {
          dispatch({ type: 'CLEAR' })
          return
        }
        dispatch({ type: 'REQUEST' })
        const res = await fetch(
          `api/v1/priceWatches/${watchId}/${active ? 'activate' : 'deactivate'}`,
          {
            method: 'PUT',
            headers: {
              Accept: 'application/json',
            },
          }
        )
        if (!res.ok) {
          throw new Error('Error deleting price watch')
        }
        dispatch({ type: 'SUCCESS' })
      } catch (err) {
        dispatch({ type: 'FAILURE', error: err })
      }
    })()
  }, [watchId, active])

  return state
}

export default useTogglePriceWatch

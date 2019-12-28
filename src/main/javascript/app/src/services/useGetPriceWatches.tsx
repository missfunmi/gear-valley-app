import { useEffect, useReducer } from 'react'
import { FetchStatus } from 'types/enums'
import { IAction, IPriceWatchResult, IServiceResponse } from 'types'

const initialState: IServiceResponse<IPriceWatchResult> = {
  status: FetchStatus.Loaded,
  error: undefined,
  data: undefined,
}

const reducer = (
  state: IServiceResponse<IPriceWatchResult>,
  action: IAction<IPriceWatchResult>
) => {
  switch (action.type) {
    case 'REQUEST':
      return {
        ...state,
        status: FetchStatus.Loading,
        error: undefined,
      }
    case 'SUCCESS':
      return {
        ...state,
        status: FetchStatus.Loaded,
        data: action.payload,
      }
    case 'FAILURE':
      return {
        ...state,
        status: FetchStatus.Error,
        error: action.error,
      }
    default:
      return state
  }
}

const useGetPriceWatches = () => {
  const [state, dispatch] = useReducer(reducer, initialState)

  useEffect(() => {
    ;(async () => {
      try {
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

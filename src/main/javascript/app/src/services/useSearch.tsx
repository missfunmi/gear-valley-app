import { useEffect, useReducer } from 'react'
import { FetchStatus } from 'types/enums'
import { IAction, IServiceResponse, ISearchResultWrapper } from 'types'

const initialState: IServiceResponse<ISearchResultWrapper> = {
  status: FetchStatus.Loaded,
  error: undefined,
  data: undefined,
}

const reducer = (
  state: IServiceResponse<ISearchResultWrapper>,
  action: IAction<ISearchResultWrapper>
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

const useSearch = (keyword: string) => {
  const [state, dispatch] = useReducer(reducer, initialState)

  useEffect(() => {
    if (!keyword) {
      dispatch({ type: 'SUCCESS' })
      return
    }
    ;(async () => {
      try {
        const request = { keyword }
        const res = await fetch('api/v1/search', {
          method: 'POST',
          body: JSON.stringify(request),
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
  }, [keyword])

  return state
}

export default useSearch

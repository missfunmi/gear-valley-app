import { useEffect, useReducer } from 'react'
import { FetchStatus } from 'types/enums'
import { ISearchResultWrapper } from 'types'
import { fetchReducer, FetchState, FetchReducer } from './fetchReducer'

const initialState: FetchState<ISearchResultWrapper> = {
  status: FetchStatus.Loaded,
}

const useSearch = (keyword: string) => {
  const searchReducer: FetchReducer<ISearchResultWrapper> = fetchReducer
  const [state, dispatch] = useReducer(searchReducer, initialState)

  useEffect(() => {
    if (!keyword) return
    ;(async () => {
      try {
        dispatch({ type: 'REQUEST' })
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

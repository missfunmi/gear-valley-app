import React, { MouseEvent } from 'react'
import { FontWeights } from '@uifabric/styling'
import { FontSizes } from '@uifabric/fluent-theme/lib/fluent/FluentType'
import { Link as FabricLink } from 'office-ui-fabric-react/lib/Link'
import { Stack, IStackTokens } from 'office-ui-fabric-react/lib/Stack'
import { Text, ITextStyles } from 'office-ui-fabric-react/lib/Text'
import { BrowserRouter as Router, Switch, Route, Link, useHistory } from 'react-router-dom'
import { SearchPage } from './pages/Search'
import { WatchesPage } from './pages/Watches'

const logoTextStyles: ITextStyles = {
  root: {
    fontSize: FontSizes.size18,
    fontWeight: FontWeights.bold,
    textTransform: 'uppercase',
  },
}
const logoLinkStyle = {
  textDecoration: 'none',
  color: '#000000',
}
const stackTokens: IStackTokens = {
  childrenGap: 5,
  padding: 10,
}
const Nav: React.FC = () => {
  const history = useHistory()
  // manually handling navigation since there is no straight forward way to preserve
  // fabric theme on links when using react-router Link
  const handleNavigation = (target: string) => (event: MouseEvent<HTMLAnchorElement>) => {
    event.preventDefault()
    history.push(target)
  }
  return (
    <Stack tokens={stackTokens} horizontal horizontalAlign="space-between">
      <Link to="/" style={logoLinkStyle}>
        <Text styles={logoTextStyles}>Gear Valley.</Text>
      </Link>
      <FabricLink onClick={handleNavigation('/watches')}>Price Watches</FabricLink>
    </Stack>
  )
}

const App: React.FC = () => {
  return (
    <Router>
      <div className="App">
        <Nav />
        <Switch>
          <Route path="/watches">
            <WatchesPage />
          </Route>
          <Route path="/">
            <SearchPage />
          </Route>
        </Switch>
      </div>
    </Router>
  )
}

export default App

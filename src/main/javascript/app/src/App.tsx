import React from 'react';
import { FontWeights } from '@uifabric/styling';
import { FontSizes } from '@uifabric/fluent-theme/lib/fluent/FluentType';
import { Text, ITextStyles } from 'office-ui-fabric-react/lib/Text';
import { Stack, IStackTokens } from 'office-ui-fabric-react/lib/Stack';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from 'react-router-dom';
import { SearchPage } from './pages/Search'

const logoTextStyles: ITextStyles = {
  root: {
    fontSize: FontSizes.size18,
    fontWeight: FontWeights.bold,
    textTransform: 'uppercase'
  }
};
const logoLinkStyle = {
  textDecoration: 'none',
  color: '#000000'
};
const stackTokens: IStackTokens = {
  childrenGap: 5,
  padding: 10
};
const App: React.FC = () => {
  return (
    <Router>
      <div className="App">
        <nav>
          <Stack tokens={stackTokens}>
            <Link to="/" style={logoLinkStyle}><Text styles={logoTextStyles}>Gear Valley.</Text></Link>
          </Stack>
        </nav>
        <Switch>
          <Route path="/">
            <SearchPage/>
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;

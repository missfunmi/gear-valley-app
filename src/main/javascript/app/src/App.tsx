import React from 'react';
import { FontWeights } from '@uifabric/styling';
import { FontSizes } from '@uifabric/fluent-theme/lib/fluent/FluentType';
import { Text, ITextStyles } from 'office-ui-fabric-react/lib/Text';
import { Stack, IStackTokens } from 'office-ui-fabric-react/lib/Stack';
import { SearchPage } from './pages/Search'

const logoTextStyles: ITextStyles = {
  root: {
    fontSize: FontSizes.size24,
    fontWeight: FontWeights.bold
  }
};
const stackTokens: IStackTokens = {
  childrenGap: 5,
  padding: 10
};
const App: React.FC = () => {
  return (
    <div className="App">
      <header>
        <Stack tokens={stackTokens}>
          <Text styles={logoTextStyles}>Gear Valley</Text>
        </Stack>
      </header>
      <SearchPage/>
    </div>
  );
}

export default App;

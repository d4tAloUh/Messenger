import { createStore, compose } from 'redux';
import rootReducer from './reducers/index';

declare global {
  interface Window { // eslint-disable-line
    // eslint-disable-next-line no-undef
    __REDUX_DEVTOOLS_EXTENSION_COMPOSE__?: typeof compose;
  }
}

export const store = createStore(rootReducer);

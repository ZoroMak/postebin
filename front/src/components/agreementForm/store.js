import { createStore, combineReducers } from 'redux';
import { checkboxReducer } from './reducers'; //  Adjust path as needed

export const createAppStore = (initialState) =>
    createStore(
        combineReducers({
            checkboxes: checkboxReducer,
        }),
        initialState
    );

export default createAppStore();
import { combineReducers, Reducer } from "redux";

import authReducer from "./components/auth/reducer";
import featuresReducer from "./components/features/reducer";

const rootReducer: Reducer<any, any> = combineReducers({
  auth: authReducer,
  features: featuresReducer,
});

export default rootReducer;

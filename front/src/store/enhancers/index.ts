import { applyMiddleware, StoreEnhancer } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";
import thunkMiddleware from "redux-thunk";

import monitorReducerEnhancer from "./monitor";
import loggerMiddleware from "../middlewares/logger";

const middlewareEnhancer: StoreEnhancer = applyMiddleware(
  thunkMiddleware,
  loggerMiddleware,
);

const composedEnhancers: StoreEnhancer = composeWithDevTools(
  middlewareEnhancer,
  monitorReducerEnhancer,
);

export default composedEnhancers;

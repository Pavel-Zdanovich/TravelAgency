import { createStore, Store } from "redux";
import reducer from "./reducer";
import state from "./state";
import enhancer from "./enhancers";

const store: Store = createStore(reducer, state, enhancer);

export default store;

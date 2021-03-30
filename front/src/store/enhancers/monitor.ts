import { StoreEnhancer, StoreEnhancerStoreCreator } from "redux";

const monitorReducerEnhancer: StoreEnhancer = (
  createStore: StoreEnhancerStoreCreator<any, any>,
) => (reducer, preloadedState) => {
  const monitoredReducer: typeof reducer = (state, action) => {
    const start: Date = new Date();
    console.log(`%cstart reduce: ${start.toISOString()}`, "color: aqua");
    const newState = reducer(state, action);
    const end: Date = new Date();
    console.log(`%cend reduce: ${end.toISOString()}`, "color: aqua");
    console.log(
      `%creduce process time: ${end.getTime() - start.getTime()}`,
      "color: aqua",
    );
    return newState;
  };

  return createStore(monitoredReducer, preloadedState);
};

export default monitorReducerEnhancer;

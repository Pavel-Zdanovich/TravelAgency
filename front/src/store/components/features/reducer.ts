import { Reducer } from "redux";

import { IFeatureAction } from "./actions";
import initialState, { IFeatureState } from "./state";
import { FeatureActionTypes } from "./types";

import compose from "../reducer";

const reducer: Reducer<IFeatureState, IFeatureAction> = (
  state = initialState,
  action,
) => {
  console.log("features");
  switch (action.type) {
    case FeatureActionTypes.FEATURES_READ_ALL_FEATURES: {
      return {
        ...state,
        payload: action.payload,
      };
    }
    default: {
      return state;
    }
  }
};

export default compose(reducer);

import { Reducer } from "redux";

import { IAuthAction } from "./actions";
import initialState, { IAuthState } from "./state";
import { AuthActionTypes } from "./types";

import compose from "../reducer";

const reducer: Reducer<IAuthState, IAuthAction> = (
  state = initialState,
  action,
) => {
  switch (action.type) {
    case AuthActionTypes.AUTH_SIGN_IN: {
      return {
        ...state,
        payload: action.payload,
      };
    }
    case AuthActionTypes.AUTH_SIGN_UP: {
      return {
        ...state,
        payload: action.payload,
      };
    }
    case AuthActionTypes.AUTH_SIGN_OUT: {
      return {
        ...state,
        payload: null,
      };
    }
    default: {
      return state;
    }
  }
};

export default compose(reducer);

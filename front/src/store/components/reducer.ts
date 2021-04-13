import { Reducer } from "redux";

import { IState, StatusType } from "./state";
import { RequestAction } from "./actions";
import { RequestActionTypes } from "./types";

const requestReducer = <S extends IState<any>, A extends RequestAction<any>>(
  state: S,
  action: A,
): S => {
  switch (action.type.replace(state.subType + "/", "")) {
    case RequestActionTypes.REQUEST_IDLE: {
      return {
        ...state,
        status: StatusType.IDLE,
        request: null,
        payload: null,
        error: null,
      };
    }
    case RequestActionTypes.REQUEST_LOADING: {
      return {
        ...state,
        status: StatusType.LOADING,
        request: action.request,
      };
    }
    case RequestActionTypes.REQUEST_SUCCEEDED: {
      return {
        ...state,
        status: StatusType.SUCCEEDED,
        payload: action.payload,
      };
    }
    case RequestActionTypes.REQUEST_FAILED: {
      return {
        ...state,
        status: StatusType.FAILED,
        error: action.error,
      };
    }
    default: {
      return state;
    }
  }
};

const compose = <S extends IState<any>, A extends RequestAction<any>>(
  reducer: Reducer<S, A>,
): Reducer<S, A> => {
  return (state, action) => {
    if (state && action.type.startsWith(state.subType)) {
      const newState = requestReducer(state, action);
      return reducer(newState, action);
    }
    return reducer(state, action);
  };
};

export default compose;

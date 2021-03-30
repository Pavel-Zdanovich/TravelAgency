import { Action } from "redux";
import {
  ErrorType,
  PayloadType,
  RequestActionTypes,
  RequestType,
  SubType,
} from "./types";

export interface RequestAction<E> extends Action {
  request: RequestType;
  payload: PayloadType<E>;
  error: ErrorType;
}

export const requestIdleActionCreator = <E>(
  subType: SubType,
): RequestAction<E> => {
  return {
    type: subType + "/" + RequestActionTypes.REQUEST_IDLE,
    request: null,
    payload: null,
    error: null,
  };
};

export const requestLoadingActionCreator = <E>(
  subType: SubType,
  request: RequestType,
): RequestAction<E> => {
  return {
    type: subType + "/" + RequestActionTypes.REQUEST_LOADING,
    request: request,
    payload: null,
    error: null,
  };
};

export const requestSucceededActionCreator = <E>(
  subType: SubType,
  payload: PayloadType<E> = null,
): RequestAction<E> => {
  return {
    type: subType + "/" + RequestActionTypes.REQUEST_SUCCEEDED,
    request: null,
    payload: payload,
    error: null,
  };
};

export const requestFailedActionCreator = <E>(
  subType: SubType,
  error: ErrorType,
): RequestAction<E> => {
  return {
    type: subType + "/" + RequestActionTypes.REQUEST_FAILED,
    request: null,
    payload: null,
    error: error,
  };
};

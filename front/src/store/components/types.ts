import { IRequest } from "../../utils/API";

export enum RequestActionTypes {
  REQUEST_IDLE = "idle",
  REQUEST_LOADING = "loading",
  REQUEST_SUCCEEDED = "succeeded",
  REQUEST_FAILED = "failed",
}

export enum SubType {
  AUTH = "auth",
  FEATURES = "features",
}

export type RequestType = IRequest | (RequestInfo & RequestInit) | null;
export type PayloadType<E> = E | null;
export type ErrorType = IResponse | Response | Error | string | null;

export type IResponse = {
  timestamp: number;
  status: number;
  error: string;
  message: string;
  path: string;
};

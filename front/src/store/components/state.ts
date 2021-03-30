import { ErrorType, PayloadType, RequestType, SubType } from "./types";

export enum StatusType {
  IDLE = "idle",
  LOADING = "loading",
  SUCCEEDED = "succeeded",
  FAILED = "failed",
}

export interface IState<E> {
  subType: SubType;
  status: StatusType;
  request: RequestType;
  payload: PayloadType<E>;
  error: ErrorType;
}

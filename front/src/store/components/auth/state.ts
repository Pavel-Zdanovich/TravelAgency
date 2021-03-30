import { IAuth } from "./types";
import { IState, StatusType } from "../state";
import { SubType } from "../types";

export type IAuthState = IState<IAuth>;

const initialState: IAuthState = {
  subType: SubType.AUTH,
  status: StatusType.IDLE,
  request: null,
  payload: null,
  error: null,
};

export default initialState;

import { IFeatures } from "./types";
import { IState, StatusType } from "../state";
import { SubType } from "../types";

export type IFeatureState = IState<IFeatures>;

const initialState: IFeatureState = {
  subType: SubType.FEATURES,
  status: StatusType.IDLE,
  request: null,
  payload: null,
  error: null,
};

export default initialState;

import {
  batch,
  connect,
  ConnectedProps,
  MapDispatchToPropsFunction,
  MapStateToProps,
} from "react-redux";
import { Action, ActionCreator, bindActionCreators } from "redux";
import { ThunkAction, ThunkDispatch } from "redux-thunk";

import { FeatureActionTypes, IFeatures } from "./types";
import { ErrorType, RequestType, SubType } from "../types";
import {
  requestFailedActionCreator,
  requestLoadingActionCreator,
  requestSucceededActionCreator,
} from "../actions";
import { State } from "../../state";
import API, { IRequest } from "../../../utils/API";

export interface IFeatureAction extends Action {
  request: RequestType;
  payload: IFeatures | null;
  error: ErrorType;
}

export const featuresReadAllFeatures: ActionCreator<IFeatureAction> = (
  features: IFeatures,
): IFeatureAction => {
  return {
    type: FeatureActionTypes.FEATURES_READ_ALL_FEATURES,
    request: null,
    payload: features,
    error: null,
  };
};

export const readAllFeatures: ActionCreator<ThunkAction<any, any, any, any>> = (
  token?: string,
) => {
  return (dispatch) => {
    batch(() => {
      const headers: HeadersInit = token
        ? {
            "Json-Web-Token": token,
          }
        : {};

      const request: IRequest = {
        method: "GET",
        path: "features",
        headers: headers,
      };

      console.log("request"); //TODO dispatch fetch started
      dispatch(requestLoadingActionCreator(SubType.FEATURES, request));

      API.request(request)
        .then(async (value) => {
          if (value.ok) {
            const features: IFeatures = await value.json();
            console.log("succeeded");
            dispatch(requestSucceededActionCreator(SubType.FEATURES, features));
            dispatch(featuresReadAllFeatures(features)); //TODO dispatch fetch succeeded
          } else {
            const error = await value.json();
            console.log("failed", error); //TODO dispatch fetch failed
            dispatch(requestFailedActionCreator(SubType.FEATURES, error));
          }
        })
        .catch((reason) => {
          console.log("failed", reason); //TODO dispatch fetch failed
          dispatch(requestFailedActionCreator(SubType.FEATURES, reason));
        });
    });
  };
};

const mapStateToProps: MapStateToProps<any, any, State> = (state) => ({
  features: state.features,
});

const mapDispatchToProps: MapDispatchToPropsFunction<any, any> = (
  dispatch: ThunkDispatch<any, any, any>,
) => bindActionCreators({ readAllFeatures }, dispatch);

export const connector = connect(mapStateToProps, mapDispatchToProps);

export type IFeaturesConnectedProps = ConnectedProps<typeof connector>;

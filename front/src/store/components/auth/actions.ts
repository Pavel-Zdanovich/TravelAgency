import {
  batch,
  connect,
  ConnectedProps,
  MapDispatchToPropsFunction,
  MapStateToProps,
} from "react-redux";
import { Action, ActionCreator, bindActionCreators } from "redux";
import { ThunkAction, ThunkDispatch } from "redux-thunk";

import { AuthActionTypes, IAuth, IAuthentication, IUser } from "./types";
import { ErrorType, RequestType, SubType } from "../types";
import { State } from "../../state";
import API, { IRequest } from "../../../utils/API";
import {
  requestFailedActionCreator,
  requestLoadingActionCreator,
  requestSucceededActionCreator,
} from "../actions";

export interface IAuthAction extends Action {
  request: RequestType;
  payload: IAuth | null;
  error: ErrorType;
}

export const authSignUp: ActionCreator<IAuthAction> = (
  auth: IAuth,
): IAuthAction => {
  return {
    type: AuthActionTypes.AUTH_SIGN_IN,
    request: null,
    payload: auth,
    error: null,
  };
};

export const authSignIn: ActionCreator<IAuthAction> = (
  auth: IAuth,
): IAuthAction => {
  return {
    type: AuthActionTypes.AUTH_SIGN_IN,
    request: null,
    payload: auth,
    error: null,
  };
};

export const authSignOut: ActionCreator<IAuthAction> = (): IAuthAction => {
  return {
    type: AuthActionTypes.AUTH_SIGN_OUT,
    request: null,
    payload: null,
    error: null,
  };
};

export const signUp: ActionCreator<ThunkAction<any, any, any, any>> = (
  user: IUser,
) => {
  return (dispatch) => {
    batch(() => {
      const request: IRequest = {
        method: "POST",
        path: "auth/signup",
        body: JSON.stringify(user),
      };

      console.log("request"); //TODO dispatch fetch started
      dispatch(requestLoadingActionCreator(SubType.AUTH, request));

      API.request(request)
        .then(async (value) => {
          if (value.ok) {
            const auth: IAuthentication = await value.json();
            console.log("succeeded");
            dispatch(requestSucceededActionCreator(SubType.AUTH, auth));
            dispatch(authSignUp(auth)); //TODO dispatch fetch succeeded
          } else {
            const error = await value.json();
            console.log("failed", error); //TODO dispatch fetch failed
            dispatch(requestFailedActionCreator(SubType.AUTH, error));
          }
        })
        .catch((reason) => {
          console.log("failed", reason); //TODO dispatch fetch failed
          dispatch(requestFailedActionCreator(SubType.AUTH, reason));
        });
    });
  };
};

export const signIn: ActionCreator<ThunkAction<any, any, any, any>> = (
  user: IUser,
) => {
  return (dispatch) => {
    batch(() => {
      const request: IRequest = {
        method: "POST",
        path: "auth/signin",
        body: JSON.stringify(user),
      };

      console.log("request"); //TODO dispatch fetch started
      dispatch(requestLoadingActionCreator(SubType.AUTH, request));

      API.request(request)
        .then(async (value) => {
          if (value.ok) {
            const auth: IAuthentication = await value.json();
            console.log("succeeded");
            dispatch(requestSucceededActionCreator(SubType.AUTH, auth));
            dispatch(authSignIn(auth)); //TODO dispatch fetch succeeded
          } else {
            const error = await value.json();
            console.log("failed", error); //TODO dispatch fetch failed
            dispatch(requestFailedActionCreator(SubType.AUTH, error));
          }
        })
        .catch((reason) => {
          console.log("failed", reason); //TODO dispatch fetch failed
          dispatch(requestFailedActionCreator(SubType.AUTH, reason));
        });
    });
  };
};

export const signOut: ActionCreator<ThunkAction<any, any, any, any>> = (
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
        method: "POST",
        path: "auth/signout",
        headers: headers,
      };

      console.log("request"); //TODO dispatch fetch started
      dispatch(requestLoadingActionCreator(SubType.AUTH, request));

      API.request(request)
        .then(async (value) => {
          if (value.ok) {
            console.log("succeeded");
            dispatch(requestSucceededActionCreator(SubType.AUTH, null));
            dispatch(authSignOut()); //TODO dispatch fetch succeeded
          } else {
            const error = await value.json();
            console.log("failed", error); //TODO dispatch fetch failed
            dispatch(requestFailedActionCreator(SubType.AUTH, error));
          }
        })
        .catch((reason) => {
          console.log("failed", reason); //TODO dispatch fetch failed
          dispatch(requestFailedActionCreator(SubType.AUTH, reason));
        });
    });
  };
};

const mapStateToProps: MapStateToProps<any, any, State> = (state) => ({
  auth: state.auth,
});

const mapDispatchToProps: MapDispatchToPropsFunction<any, any> = (
  dispatch: ThunkDispatch<any, any, any>,
) => bindActionCreators({ signIn, signUp, signOut }, dispatch);

export const connector = connect(mapStateToProps, mapDispatchToProps);

export type IAuthConnectedProps = ConnectedProps<typeof connector>;

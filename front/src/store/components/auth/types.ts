export enum AuthActionTypes {
  AUTH_SIGN_IN = "auth/sign_in",
  AUTH_SIGN_UP = "auth/sign_up",
  AUTH_SIGN_OUT = "auth/sign_out",
}

export interface IUser {
  username: string;
  password: string;
  with?: string;
  remember?: string;
}

export interface IAuthentication {
  principal: string;
  credentials: string;
  token: string;
  name: string;
  authenticated: boolean;
  authorities: [
    {
      authority: string;
    },
  ];
  details: {
    remoteAddress: string;
    sessionId: string;
  };
}

export type IAuth = IAuthentication | null;

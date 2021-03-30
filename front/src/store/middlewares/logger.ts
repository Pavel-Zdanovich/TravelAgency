import { Action, Dispatch, Middleware, MiddlewareAPI } from "redux";

const logger: Middleware<unknown, any, Dispatch<Action>> = (
  api: MiddlewareAPI<Dispatch<Action>>,
) => (dispatch: Dispatch) => (action: Action) => {
  console.log("%cmiddleware", "color: yellow");
  console.groupCollapsed("%cprev state: ", "color: yellow");
  console.log(api.getState());
  console.groupEnd();
  const result = dispatch(action);
  console.groupCollapsed("%cnext state: ", "color: lime");
  console.log(api.getState());
  console.groupEnd();
  return result;
};

export default logger;

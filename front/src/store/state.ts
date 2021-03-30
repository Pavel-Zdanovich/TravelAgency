import rootReducer from "./reducer";
import auth from "./components/auth/state";
import features from "./components/features/state";

export type State = ReturnType<typeof rootReducer>;

const state: State = {
  auth: auth,
  features: features,
};

export default state;

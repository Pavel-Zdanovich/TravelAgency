import React from "react";
import { Route, useHistory, useLocation } from "react-router-dom";

import { StatusType } from "../../../../store/components/state";
import {
  connector,
  IAuthConnectedProps,
} from "../../../../store/components/auth/actions";
import { IUser } from "../../../../store/components/auth/types";

import Form from "./form/Form";

import styles from "./Auth.module.css";

type IAuthProps = IAuthConnectedProps;

const Auth: React.FC<IAuthProps> = ({ ...props }: IAuthProps) => {
  const signInUp = "SIGN IN/UP";
  const signOut = "SIGN OUT";

  const history = useHistory();
  const location = useLocation();

  const [name, setName] = React.useState<string>(signInUp);

  const openForm = () => {
    history.push("/auth");
  };
  const closeForm = () => {
    history.push("/");
  };

  const handleClick = () => {
    switch (name) {
      case signInUp: {
        openForm();
        break;
      }
      case signOut: {
        props.signOut(props.auth.token);
        history.push("/auth/sign_out");
        break;
      }
      default: {
        console.log(`Unknown auth name: ${name}`);
      }
    }
  };

  const authorize = (signType: string, user: IUser) => {
    switch (signType) {
      case "sign_up": {
        props.signUp(user);
        history.push("/auth/sign_up");
        break;
      }
      case "sign_in": {
        props.signIn(user);
        history.push("/auth/sign_in");
        break;
      }
      default: {
        console.log(`Unknown sign type: ${signType}`);
      }
    }
  };

  const renderModal = () => {
    const form = <Form close={closeForm} authorize={authorize} />;
    const spinner = <div className={styles.spinner}>SPINNER</div>;
    const authStatus: StatusType = props.auth.status;
    switch (authStatus) {
      case StatusType.IDLE: {
        return location.pathname == "/auth" ? form : null;
      }
      case StatusType.LOADING: {
        if (
          location.pathname == "/auth/sign_in" ||
          location.pathname == "/auth/sign_up"
        ) {
          return (
            <>
              {form}
              {spinner}
            </>
          );
        } else {
          return spinner;
        }
      }
      case StatusType.SUCCEEDED: {
        if (location.pathname == "/auth") {
          return form;
        } else {
          return null;
        }
      }
      case StatusType.FAILED: {
        if (
          location.pathname == "/auth/sign_in" ||
          location.pathname == "/auth/sign_up"
        ) {
          return (
            <Form
              close={closeForm}
              authorize={authorize}
              error={props.auth.error}
            />
          );
        } else {
          console.error(props.auth.error);
          return null;
        }
      }
      default: {
        console.log(`Unknown auth status: ${authStatus}`);
        return null;
      }
    }
  };

  React.useEffect(() => {
    const authStatus: StatusType = props.auth.status;
    switch (authStatus) {
      case StatusType.IDLE: {
        break;
      }
      case StatusType.LOADING: {
        break;
      }
      case StatusType.SUCCEEDED: {
        history.push("/");
        if (props.auth.payload != null) {
          setName(signOut);
        } else {
          setName(signInUp);
        }
        break;
      }
      case StatusType.FAILED: {
        break;
      }
      default: {
        console.log(`Unknown auth status: ${authStatus}`);
      }
    }
  }, [props.auth, history]);

  return (
    <>
      <div className={styles.auth} onClick={handleClick}>
        {name}
      </div>
      <Route path="/auth" render={renderModal} />
    </>
  );
};

export default connector(Auth);

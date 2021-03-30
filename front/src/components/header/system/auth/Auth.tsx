import React from "react";

import { StatusType } from "../../../../store/components/state";
import {
  connector,
  IAuthConnectedProps,
} from "../../../../store/components/auth/actions";
import { IUser } from "../../../../store/components/auth/types";

import Form from "./form/Form";

import styles from "./Auth.module.css";

type IAuthProps = IAuthConnectedProps;

enum FormStatus {
  OPEN = "OPEN",
  CLOSE = "CLOSE",
}

const Auth: React.FC<IAuthProps> = ({ ...props }: IAuthProps) => {
  const signInUp = "SIGN IN/UP";
  const signOut = "SIGN OUT";

  const [formStatus, setFormStatus] = React.useState<FormStatus>(
    FormStatus.CLOSE,
  );
  const [name, setName] = React.useState<string>(signInUp);

  const openForm = () => {
    setFormStatus(FormStatus.OPEN);
  };
  const closeForm = () => {
    setFormStatus(FormStatus.CLOSE);
  };

  const handleClick = () => {
    switch (name) {
      case signInUp: {
        openForm();
        break;
      }
      case signOut: {
        props.signOut();
        closeForm();
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
        break;
      }
      case "sign_in": {
        props.signIn(user);
        break;
      }
      default: {
        console.log(`Unknown sign type: ${signType}`);
      }
    }
  };

  const form = <Form close={closeForm} authorize={authorize} />;
  const spinner = <div className={styles.spinner}>SPINNER</div>;

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
        if (props.auth.payload != null && formStatus == FormStatus.OPEN) {
          setFormStatus(FormStatus.CLOSE);
          setName(signOut);
        }
        if (props.auth.payload == null && formStatus == FormStatus.CLOSE) {
          setName(signInUp);
        }
        break;
      }
      case StatusType.FAILED: {
        break;
      }
      default: {
        break;
      }
    }
  }, [props.auth]);

  const renderModal = () => {
    const authStatus: StatusType = props.auth.status;
    switch (authStatus) {
      case StatusType.IDLE: {
        return formStatus == FormStatus.OPEN ? form : null;
      }
      case StatusType.LOADING: {
        return formStatus == FormStatus.OPEN ? (
          <>
            <>{form}</>
            <>{spinner}</>
          </>
        ) : (
          spinner
        );
      }
      case StatusType.SUCCEEDED: {
        return formStatus == FormStatus.OPEN ? form : null;
      }
      case StatusType.FAILED: {
        return (
          <Form
            close={closeForm}
            authorize={authorize}
            error={props.auth.error}
          />
        );
      }
      default: {
        console.log(`Unknown auth status: ${authStatus}`);
        return null;
      }
    }
  };

  return (
    <>
      <div className={styles.auth} onClick={handleClick}>
        {name}
      </div>
      {renderModal()}
    </>
  );
};

export default connector(Auth);

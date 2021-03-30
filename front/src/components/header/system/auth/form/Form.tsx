import React from "react";
import Select, { OptionsType, OptionTypeBase } from "react-select";

import { IUser } from "../../../../../store/components/auth/types";
import { ErrorType } from "../../../../../store/components/types";

import crossIcon from "assets/cross-icon.svg";

import styles from "./Form.module.css";

interface IFormProps {
  close: () => void;
  authorize: (signType: string, user: IUser) => void;
  error?: ErrorType;
}

export type { IFormProps };

const Form: React.FC<IFormProps> = ({
  close,
  authorize,
  error,
}: IFormProps) => {
  const secret = "->[]";
  const authorization = "AUTHORIZATION";
  const cross = <img src={crossIcon} alt={"X"} />;
  const username = "USERNAME";
  const password = "PASSWORD";
  const signIn = "SIGN IN";
  const signUp = "SIGN UP WITH";
  const signUpWith = "WITH";
  const defaultValue: OptionTypeBase = { value: "email", label: "EMAIL" };
  const withOptions: OptionsType<OptionTypeBase> = [
    defaultValue,
    { value: "google", label: "Google" },
    { value: "facebook", label: "Facebook" },
    { value: "vk", label: "VK" },
  ];
  const remember = "REMEMBER ME";
  const forgot = "FORGOT A PASSWORD";

  const [signType, setSignType] = React.useState<string>("sign_in");

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);

    const user: IUser = {
      username: formData.get("username") as string,
      password: formData.get("password") as string,
      with: formData.get("with") as string,
      remember: formData.get("remember") as string,
    };

    authorize(signType, user);
  };

  return (
    <div className={styles.modal}>
      <form className={styles.form} noValidate={true} onSubmit={handleSubmit}>
        <div
          className={styles.secret}
          onClick={() =>
            authorize("sign_in", {
              username: "PavelZdanovich",
              password: "Password",
            })
          }
        >
          {secret}
        </div>
        <div className={styles.heading}>{authorization}</div>
        <button className={styles.close} type={"button"} onClick={close}>
          {cross}
        </button>
        <label
          className={styles.username_label}
          htmlFor={username.toLowerCase()}
        >
          {username}
        </label>
        <input
          className={styles.username_input}
          type={"email"}
          id={username.toLowerCase()}
          name={username.toLowerCase()}
        />
        <label
          className={styles.password_label}
          htmlFor={password.toLowerCase()}
        >
          {password}
        </label>
        <input
          className={styles.password_input}
          type={"password"}
          id={password.toLowerCase()}
          name={password.toLowerCase()}
        />
        <button
          className={styles.sign_in}
          type={"submit"}
          id={"sign_in"}
          name={"sign_in"}
          onClick={() => {
            setSignType("sign_in");
          }}
        >
          {signIn}
        </button>
        <button
          className={styles.sign_up}
          type={"submit"}
          id={"sign_up"}
          name={"sign_up"}
          onClick={() => {
            setSignType("sign_up");
          }}
        >
          {signUp}
        </button>
        <Select
          className={styles.with}
          classNamePrefix={"with"}
          components={{
            IndicatorsContainer: () => null,
          }}
          name={signUpWith.toLowerCase()}
          options={withOptions}
          defaultValue={defaultValue}
        />
        <div className={styles.remember}>
          <input
            className={styles.remember_input}
            type={"checkbox"}
            id={"remember"}
            name={"remember"}
          />
          <label className={styles.remember_label} htmlFor={"remember"}>
            {remember}
          </label>
        </div>
        <a className={styles.forgot} href={"#"}>
          {forgot}
        </a>
      </form>
      {error}
    </div>
  );
};

export default Form;

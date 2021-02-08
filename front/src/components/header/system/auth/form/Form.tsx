import React from "react";

import Select, { OptionsType, OptionTypeBase } from "react-select";

import cross from "assets/cross-icon.svg";

import styles from "./Form.module.css";

interface IForm {
  handleClose: () => void;
}

export type { IForm };

const Form: React.FC<IForm> = ({ handleClose }: IForm) => {
  const secret = "->[]";
  const authorize = "AUTHORIZE";
  const close = <img src={cross} alt={"X"} />;
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

  return (
    <div className={styles.modal}>
      <form className={styles.form}>
        <div className={styles.secret}>{secret}</div>
        <div className={styles.heading}>{authorize}</div>
        <button className={styles.close} type={"button"} onClick={handleClose}>
          {close}
        </button>
        <label className={styles.username_label} htmlFor={"username"}>
          {username}
        </label>
        <input
          className={styles.username_input}
          type={"email"}
          id={"username"}
        />
        <label className={styles.password_label} htmlFor={"password"}>
          {password}
        </label>
        <input
          className={styles.password_input}
          type={"password"}
          id={"password"}
        />
        <button className={styles.sign_in} type={"submit"}>
          {signIn}
        </button>
        <button className={styles.sign_up} type={"submit"}>
          {signUp}
        </button>
        <Select
          className={styles.with}
          classNamePrefix={"with"}
          components={{
            IndicatorsContainer: () => null,
          }}
          name={signUpWith}
          //value={multiValue}
          options={withOptions}
          //onChange={handleChange}
          defaultValue={defaultValue}
        />
        <div className={styles.remember}>
          <input
            className={styles.remember_input}
            type={"checkbox"}
            id={"remember"}
          />
          <label className={styles.remember_label} htmlFor={"remember"}>
            {remember}
          </label>
        </div>
        <a className={styles.forgot} href={"#"}>
          {forgot}
        </a>
      </form>
    </div>
  );
};

export default Form;

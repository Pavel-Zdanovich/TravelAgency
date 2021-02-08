import React from "react";

import Form from "./form/Form";

import styles from "./Auth.module.css";

const Auth: React.FC = () => {
  const name = "AUTH";

  const [activeAuth, setActiveAuth] = React.useState<any>();

  const handleAuth = () => {
    setActiveAuth(<Form handleClose={() => setActiveAuth(undefined)} />);
  };

  return (
    <>
      <div className={styles.auth} onClick={handleAuth}>
        {name}
      </div>
      {activeAuth}
    </>
  );
};

export default Auth;

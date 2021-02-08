import React from "react";

import Label from "./label/Label";
import Search from "./search/Search";
import System from "./system/System";

import styles from "./Header.module.css";

const Header: React.FC = () => {
  return (
    <header className={styles.header}>
      <Label />
      <Search />
      <System />
    </header>
  );
};

export default Header;

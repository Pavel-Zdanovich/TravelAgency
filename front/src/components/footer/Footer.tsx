import React from "react";

import Contact from "./contact/Contact";
import Author from "./author/Author";
import Media from "./media/Media";

import styles from "./Footer.module.css";

const Footer: React.FC = () => {
  return (
    <footer className={styles.footer}>
      <Contact />
      <Author />
      <Media />
    </footer>
  );
};

export default Footer;

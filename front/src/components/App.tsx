import * as React from "react";
import { hot } from "react-hot-loader";

import Header from "./header/Header";
import LeftBar from "./leftBar/LeftBar";
import Map from "./map/Map";
import RightBar from "./rightBar/RightBar";
import Footer from "./footer/Footer";

import styles from "./App.module.css";

const App: React.FC = () => {
  return (
    <div className={styles.app}>
      <Header />
      <LeftBar />
      <Map />
      <RightBar />
      <Footer />
    </div>
  );
};

export default hot(module)(App);

import * as React from "react";

import Header from "./components/header/Header";
import LeftBar from "./components/leftBar/LeftBar";
import Map from "./components/map/Map";
import RightBar from "./components/rightBar/RightBar";
import Footer from "./components/footer/Footer";

import styles from "./App.module.css";

export default function App() {
    return(
        <div className={styles.app}>
            <Header className={styles.header} name={"TravelAgency"}/>
            <LeftBar className={styles.leftBar}/>
            <Map className={styles.map}/>
            <RightBar className={styles.rightBar}/>
            <Footer className={styles.footer}/>
        </div>
    );
}

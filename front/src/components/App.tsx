import * as React from "react";

import Header from "./header/Header";
import LeftBar from "./leftBar/LeftBar";
import Map from "./map/Map";
import RightBar from "./rightBar/RightBar";
import Footer from "./footer/Footer";

import styles from "./App.module.css";

export default function App() {
    return (
        <div className={styles.app}>
            <Header label={"TravelAgency"} search={"SEARCH"} system={{lang: "LANG", prof: "PROF", auth: "AUTH"}}/>
            <LeftBar/>
            <Map src={require("../../assets/map.jpg").default} name={"MAP"}/>
            <RightBar name={"House #1"} src={require("../../assets/house.png").default}/>
            <Footer author={"AUTHOR"} contact={"CONTACT"} media={"MEDIA"}/>
        </div>
    );
}

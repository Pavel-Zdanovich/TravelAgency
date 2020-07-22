import React from 'react';
import './App.css';
import Header from "./component/header/Header";
import LeftBar from "./component/leftbar/LeftBar";
import Map from "./component/map/Map";
import RightBar from "./component/rightbar/RightBar";
import Footer from "./component/footer/Footer";

function App() {
  return (
    <div className="App">
      <Header name={"Header"}/>
      <LeftBar name={"LeftBar"}/>
      <Map name={"Map"}/>
      <RightBar name={"RightBar"}/>
      <Footer name={"Footer"}/>
    </div>
  );
}

export default App;
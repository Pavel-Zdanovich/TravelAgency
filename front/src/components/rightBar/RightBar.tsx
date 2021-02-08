import React from "react";

import Label from "./label/Label";
import Gallery from "./gallery/Gallery";
import Review from "./review/Review";

import houseImage from "assets/house.png";

import styles from "./RightBar.module.css";

const RightBar: React.FC = () => {
  const house = {
    label: "House #1",
    src: houseImage,
  };

  return (
    <aside className={styles.aside}>
      <Label name={house.label} />
      <Gallery src={house.src} alt={house.label} />
      <Review name={house.label} />
    </aside>
  );
};

export default RightBar;

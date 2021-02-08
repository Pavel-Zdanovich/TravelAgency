import React from "react";

import mapImage from "assets/map.jpg";

import styles from "./Map.module.css";

const Map: React.FC = () => {
  const name = "MAP";

  return (
    <div className={styles.map}>
      <img className={styles.image} src={mapImage} alt={name} />
    </div>
  );
};

export default Map;

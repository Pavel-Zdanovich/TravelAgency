import React from "react";

import Label from "./label/Label";
import Gallery from "./gallery/Gallery";
import Review from "./review/Review";

import house1Image from "assets/house1.png";
import house2Image from "assets/house2.png";
import house3Image from "assets/house3.png";

import styles from "./RightBar.module.css";

const RightBar: React.FC = () => {
  const label = "House #1";
  const defaultImages = [house1Image, house2Image, house3Image];
  const [images, setImages] = React.useState<Array<string>>(defaultImages);

  const loadImage = () => {
    fetch("https://aws.random.cat/meow")
      .then(async (response) => {
        console.log("response", response);
        const json = await response.json();
        setImages(defaultImages.concat(json.file));
      })
      .catch((err) => {
        console.error(err);
      });
  };

  return (
    <aside className={styles.aside}>
      <Label name={label} />
      <Gallery name={label} images={images} load={loadImage} />
      <Review name={label} />
    </aside>
  );
};

export default RightBar;

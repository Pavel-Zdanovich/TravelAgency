import React from "react";

import styles from "./Gallery.module.css";

interface IGalleryProps {
  name: string;
  images: [string];
  load: () => void;
}

export type { IGalleryProps };

const Gallery: React.FC<IGalleryProps> = ({
  name,
  images,
  load,
}: IGalleryProps) => {
  const [index, setIndex] = React.useState<number>(1);

  const decrement = () => {
    setIndex(index == 1 ? images.length : index - 1);
  };

  const increment = () => {
    setIndex(index == images.length ? 1 : index + 1);
  };

  const renderImages = () => {
    const result = [];
    for (let i = 1; i <= images.length; i++) {
      if (i == index) {
        result.push(
          <img
            className={styles.image + " active"}
            src={images[i - 1]}
            alt={name}
          />,
        );
        break;
      }
      result.push(
        <img className={styles.image} src={images[i - 1]} alt={name} />,
      );
    }
    return result;
  };

  return (
    <div className={styles.gallery} onClick={load}>
      <div className={styles.angle + " left"} onClick={decrement}>
        &#10094;
      </div>
      {renderImages()}
      <div className={styles.angle + " right"} onClick={increment}>
        &#10095;
      </div>
    </div>
  );
};

export default Gallery;

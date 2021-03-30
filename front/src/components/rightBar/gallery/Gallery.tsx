import React from "react";

//import ReactImageGallery from "react-image-gallery";
import styles from "./Gallery.module.css";

interface IGalleryProps {
  src: string;
  alt: string;
}

export type { IGalleryProps };

const Gallery: React.FC<IGalleryProps> = ({ src, alt }: IGalleryProps) => {
  return (
    <div className={styles.gallery}>
      <img className={styles.image} src={src} alt={alt} />
    </div>
  );
};

export default Gallery;

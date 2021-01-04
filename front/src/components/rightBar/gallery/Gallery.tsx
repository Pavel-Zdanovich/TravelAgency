import React from 'react';

//import ReactImageGallery from "react-image-gallery";

import styles from './Gallery.module.css';

interface IGallery {
    src: string,
    alt: string
}

export type {IGallery};

const Gallery: React.FC<IGallery> = ({src, alt}: IGallery) => {
    return (
        <div className={styles.gallery}>
            <img className={styles.image} src={src} alt={alt}/>
        </div>
    );
}

export default Gallery;

import React from 'react';

import Contact from "./contact/Contact";
import Author from "./author/Author";
import Media from "./media/Media";

import styles from './Footer.module.css';

interface IFooter {
    contact: string,
    author: string,
    media: string
}

export type {IFooter};

const Footer: React.FC<IFooter> = ({contact, author, media}: IFooter) => {
    return (
        <footer className={styles.footer}>
            <Contact name={contact}/>
            <Author name={author}/>
            <Media name={media}/>
        </footer>
    );
}

export default Footer;

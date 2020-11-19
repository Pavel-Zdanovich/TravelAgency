import React from 'react';
import styles from './Footer.css';

const Footer = (props) => {
    return (
        <footer className={styles.footer}>
            <div className={styles.contact}>CONTACT</div>
            <div className={styles.author}>AUTHOR</div>
            <div className={styles.media}>MEDIA</div>
        </footer>
    );
}

export default Footer;
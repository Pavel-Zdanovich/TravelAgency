// For CSS
declare module "*.module.css" {
    const styles: { [key: string]: string };
    export default styles;
}

// For LESS
declare module "*.module.less" {
    const styles: { [key: string]: string };
    export default styles;
}

// For SASS
declare module "*.module.sass" {
    const styles: { [key: string]: string };
    export default styles;
}

// For SCSS
declare module "*.module.scss" {
    const styles: { [key: string]: string };
    export default styles;
}

// For GIF
declare module "*.gif" {
    const value: any;
    export = value;
}

// For JPG
declare module "*.jpg" {
    const value: any;
    export = value;
}

// For JPEG
declare module "*.jpeg" {
    const value: any;
    export = value;
}

// For PNG
declare module "*.png" {
    const value: any;
    export = value;
}

// For SVG
declare module "*.svg" {
    const value: any;
    export = value;
}

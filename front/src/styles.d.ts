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

// For SCSS
declare module "*.module.scss" {
    const styles: { [key: string]: string };
    export default styles;
}

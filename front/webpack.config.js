const path = require("path");
const webpack = require("webpack");
const autoprefixer = require("autoprefixer");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const {CleanWebpackPlugin} = require("clean-webpack-plugin");

const settings = {
    distPath: path.join(__dirname, "dist"),
    srcPath: path.join(__dirname, "src"),
};

const TypeCSSModuleLoader = {
    loader: require.resolve("typings-for-css-modules-loader"),
    options: {
        modules: true,
        namedExport: true,
        sourceMap: false, // turned off as causes delay
    }
}

const CSSModuleLoader = {
    loader: require.resolve("css-loader"),
    options: {
        modules: true,
        localIdentName: "[name]_[local]_[hash:base64:5]",
        importLoaders: 2,
        camelCase: true,
        sourceMap: false, // turned off as causes delay
    }
}

const CSSLoader = {
    loader: require.resolve("css-loader"),
    options: {
        modules: "global",
        importLoaders: 2,
        camelCase: true,
        sourceMap: false, // turned off as causes delay
    }
}

const PostCSSLoader = {
    loader: require.resolve("postcss-loader"),
    options: {
        ident: "postcss",
        sourceMap: false, // turned off as causes delay
        plugins: () => [
            autoprefixer(
                /*{
                    browsers: [">1%", "last 4 versions", "Firefox ESR", "not ie < 9"]
                }*/
            )
        ]
    }
}

module.exports = (env, options) => {

    const isDevMode = options.mode === "development";
    const devTool = isDevMode ? "source-map" : false;
    const styleLoader = isDevMode ? "style-loader" : MiniCssExtractPlugin.loader;

    return {
        entry: {
            main: path.join(settings.srcPath, "index.tsx"),
        },
        output: {
            path: settings.distPath,
            filename: "[name].bundle.js",
        },
        devServer: {
            contentBase: settings.distPath,
            filename: "[name].bundle.js",
            port: 9000,
            compress: true,
            historyApiFallback: true,
            watchContentBase: true,
            progress: true,
            open: true,
            hot: true,
        },
        devtool: devTool,
        /*resolve: {
            extensions: [ '.tsx', '.ts', '.js' ],
        },*/
        module: {
            rules: [
                {
                    test: /\.html$/,
                    use: [
                        {
                            loader: require.resolve("html-loader"),
                        },
                    ],
                },
                {
                    test: /\.(ts|js)x?$/,
                    exclude: /node_modules/,
                    use: [
                        {
                            loader: require.resolve("ts-loader"),
                        },
                        {
                            loader: require.resolve("babel-loader"),
                            options: {
                                presets: ["@babel/preset-env", "@babel/preset-react"]
                            },
                        },
                    ],
                },
                {
                    test: /\.(sa|sc|c)ss$/,
                    exclude: /\.module\.(sa|sc|c)ss$/,
                    use: [styleLoader, CSSLoader, PostCSSLoader, "sass-loader"],
                    /*use: [
                        {
                            loader: require.resolve("style-loader"),
                        },
                        {
                            loader: require.resolve("css-loader"),
                            options: {
                                sourceMap: isDevMode,
                                modules: true
                            },
                        },
                        {
                            loader: require.resolve("postcss-loader"),
                            options: {
                                sourceMap: isDevMode,
                            },
                        },
                        {
                            loader: require.resolve("sass-loader"),
                            options: {
                                sourceMap: isDevMode,
                            },
                        },
                    ],*/
                },
                {
                    test: /\.module\.(sa|sc|c)ss$/,
                    use: [styleLoader, TypeCSSModuleLoader, CSSModuleLoader, PostCSSLoader, "sass-loader"]
                },
                {
                    test: /\.(ttf|eot|woff|woff2)$/,
                    use: {
                        loader: "file-loader",
                        options: {
                            name: "fonts/[name].[ext]",
                        },
                    },
                },
                {
                    test: /\.(jpe?g|png|gif|svg|ico)$/i,
                    use: [
                        {
                            loader: require.resolve("file-loader"),
                            options: {
                                outputPath: "assets/"
                            },
                        },
                    ],
                },
            ]
        },
        plugins: [
            new webpack.HotModuleReplacementPlugin(),
            new HtmlWebpackPlugin(
                {
                    template: path.join(settings.srcPath, "index.html"),
                    filename: "index.html",
                },
            ),
            new CleanWebpackPlugin(
                {
                    verbose: true,
                }
            )
        ]
    }
};

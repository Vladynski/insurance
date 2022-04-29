const path = require('path')
const {merge} = require('webpack-merge')
const common = require('./webpack.common')
const {CleanWebpackPlugin} = require('clean-webpack-plugin')

module.exports = merge(common, {
    plugins: [
        new CleanWebpackPlugin()
    ],
    mode: 'production',
    output: {
        filename: 'short-main.js',
        path: path.resolve(__dirname, 'src', 'main', 'resources', 'public', 'js')
    }
});
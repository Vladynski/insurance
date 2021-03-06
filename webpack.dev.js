const {merge} = require('webpack-merge')
const common = require('./webpack.common')

module.exports = merge(common, {
    mode: 'development',
    devtool: 'source-map',
    devServer: {
        compress: true,
        port: 8001,
        allowedHosts: [
            'localhost:8080'
        ]
    },
});
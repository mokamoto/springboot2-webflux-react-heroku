"use strict";
const path = require('path');
 
module.exports = {
  entry: path.join(__dirname, 'app/app.jsx'),
  output: {
    path: path.resolve(__dirname, '../../../build/resources/main/static'),
    filename: 'bundle.js'
  },
  module: {
    loaders: [
      {
        test: /\.js[x]?$/,
        loader: 'babel-loader',
        exclude: /node_modules/,
        query: {
          presets: ['es2015', 'react']
        }
      }
    ]
  },
  resolve: {
    extensions: ['.js', '.jsx']
  }
}
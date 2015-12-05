'use strict';

// bridge to all the buildConfigField set in build.gradle and exported via ReactConfig
var React = require('react-native');
module.exports = React.NativeModules.ReactConfig;

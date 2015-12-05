/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var React  = require('react-native');
var Config = require('react-native-android-config');

var {
  AppRegistry,
  StyleSheet,
  Text,
  View,
} = React;

var Example = React.createClass({
  render: function() {
    return (
      <View style={styles.container}>
        <Text style={styles.text}>
          FOO={Config.FOO}
        </Text>
      </View>
    );
  }
});

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  text: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  }
});

AppRegistry.registerComponent('Example', () => Example);

# Config variables for React Native apps in Android

Module to expose config variables set in Gradle to your javascript code in React Native.

Bringing some [12 factor](http://12factor.net/config) love to your mobile apps.


## Usage

Declare config variables in Gradle, under `android/app/build.gradle`:

```
android {
    defaultConfig {
        buildConfigField "String",  "API_URL",     '"https://myapi.com"'
        buildConfigField "Boolean", "SHOW_ERRORS", "true"
        ...
```

Then access those from javascript:

```js
var Config = require('react-native-android-config');

Config.API_URL     // "https://myapi.com"
Config.SHOW_ERRORS // true
```

Gradle sets some variables by default:

- `VERSION_NAME` and `VERSION_CODE`, both coming from the build settings. Keep in mind the code is a number
- `APPLICATION_ID`: Your package name, eg: `com.Example`
- `DEBUG`: set to `true` when running the app locally
- `BUILD_TYPE` and `FLAVOR`: more build settings


## More on Gradle, config variables and 12 factor

In case you're wondering how to keep secrets outside your source code: Create `.env` with:

```
API_URL=https://:secret@myapi.com
```

Then read that env var from `build.gradle` like:

```
defaultConfig {
    buildConfigField "String", "API_URL", "\"$System.env.API_URL\""
}
```

Now use [Foreman](https://github.com/ddollar/foreman) or a similar tool to run the app so the environment vars are available to Gradle:

```
$ foreman run react-native run-android --stacktrace --debug
```

You can also use this to apply variables in `AndroidManifest.xml`. For instance, if you have `GOOGLE_MAPS_API_KEY` set in the environment, then read it in Gradle like:

```
defaultConfig {
    resValue "string", "GOOGLE_MAPS_API_KEY", "\"$System.env.GOOGLE_MAPS_API_KEY\""
}
```

And then you can use it in your manifest:

```xml
<meta-data
  android:name="com.google.android.geo.API_KEY"
  android:value="@string/GOOGLE_MAPS_API_KEY" />
```

Keep in mind the env var `APP_NAME` is reserved/set by Gradle though, so avoid that one.


## Setup

1. Include this module in `android/settings.gradle`:
  
  ```
  include ':react-native-android-config'
  include ':app'

  project(':react-native-android-config').projectDir = new File(rootProject.projectDir,
    '../node_modules/react-native-android-config/android')
  ```
2. Add a dependency to your app build in `android/app/build.gradle`:
  
  ```
  dependencies {
      ...
      compile project(':react-native-android-config')
  }
  ```
3. Change your main activity to add a new package, in `android/app/src/main/.../MainActivity.java`:
  
  ```java
  import com.lugg.ReactConfig.ReactConfigPackage; // add import

  public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

      private ReactInstanceManager mReactInstanceManager;
      private ReactRootView mReactRootView;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          mReactRootView = new ReactRootView(this);

          mReactInstanceManager = ReactInstanceManager.builder()
                  .setApplication(getApplication())
                  .setBundleAssetName("index.android.bundle")
                  .setJSMainModuleName("index.android")
                  .addPackage(new MainReactPackage())
                  .addPackage(new ReactConfigPackage()) // add the package here
  ```


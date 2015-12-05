package com.lugg.ReactConfig;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

import com.facebook.react.common.ReactConstants;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.lang.ClassNotFoundException;
import java.lang.IllegalAccessException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;

public class ReactConfigModule extends ReactContextBaseJavaModule {
  public ReactConfigModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "ReactConfig";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();

    try {
      Class clazz = Class.forName(getReactApplicationContext().getPackageName() + ".BuildConfig");
      Field[] fields = clazz.getDeclaredFields();
      for(Field f: fields) {
        try {
          constants.put(f.getName(), f.get(null));
        }
        catch (IllegalAccessException e) {
          Log.d("ReactNative", "ReactConfig: Could not access BuildConfig field " + f.getName());
        }
      }
    }
    catch (ClassNotFoundException e) {
      Log.d("ReactNative", "ReactConfig: Could not find BuildConfig class");
    }

    return constants;
  }
}

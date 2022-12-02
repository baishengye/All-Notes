package com.jinxin.navigation;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class SecondFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private SecondFragmentArgs() {
  }

  private SecondFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static SecondFragmentArgs fromBundle(@NonNull Bundle bundle) {
    SecondFragmentArgs __result = new SecondFragmentArgs();
    bundle.setClassLoader(SecondFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("userName")) {
      String userName;
      userName = bundle.getString("userName");
      if (userName == null) {
        throw new IllegalArgumentException("Argument \"userName\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("userName", userName);
    } else {
      __result.arguments.put("userName", "\"unknown");
    }
    if (bundle.containsKey("age")) {
      int age;
      age = bundle.getInt("age");
      __result.arguments.put("age", age);
    } else {
      __result.arguments.put("age", 0);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getUserName() {
    return (String) arguments.get("userName");
  }

  @SuppressWarnings("unchecked")
  public int getAge() {
    return (int) arguments.get("age");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("userName")) {
      String userName = (String) arguments.get("userName");
      __result.putString("userName", userName);
    } else {
      __result.putString("userName", "\"unknown");
    }
    if (arguments.containsKey("age")) {
      int age = (int) arguments.get("age");
      __result.putInt("age", age);
    } else {
      __result.putInt("age", 0);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    SecondFragmentArgs that = (SecondFragmentArgs) object;
    if (arguments.containsKey("userName") != that.arguments.containsKey("userName")) {
      return false;
    }
    if (getUserName() != null ? !getUserName().equals(that.getUserName()) : that.getUserName() != null) {
      return false;
    }
    if (arguments.containsKey("age") != that.arguments.containsKey("age")) {
      return false;
    }
    if (getAge() != that.getAge()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
    result = 31 * result + getAge();
    return result;
  }

  @Override
  public String toString() {
    return "SecondFragmentArgs{"
        + "userName=" + getUserName()
        + ", age=" + getAge()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(SecondFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder() {
    }

    @NonNull
    public SecondFragmentArgs build() {
      SecondFragmentArgs result = new SecondFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setUserName(@NonNull String userName) {
      if (userName == null) {
        throw new IllegalArgumentException("Argument \"userName\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userName", userName);
      return this;
    }

    @NonNull
    public Builder setAge(int age) {
      this.arguments.put("age", age);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getUserName() {
      return (String) arguments.get("userName");
    }

    @SuppressWarnings("unchecked")
    public int getAge() {
      return (int) arguments.get("age");
    }
  }
}

package com.jinxin.navigation;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class MainFragmentDirections {
  private MainFragmentDirections() {
  }

  @NonNull
  public static ActionMainFragmentToSecondFragment actionMainFragmentToSecondFragment() {
    return new ActionMainFragmentToSecondFragment();
  }

  public static class ActionMainFragmentToSecondFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionMainFragmentToSecondFragment() {
    }

    @NonNull
    public ActionMainFragmentToSecondFragment setUserName(@NonNull String userName) {
      if (userName == null) {
        throw new IllegalArgumentException("Argument \"userName\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userName", userName);
      return this;
    }

    @NonNull
    public ActionMainFragmentToSecondFragment setAge(int age) {
      this.arguments.put("age", age);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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
    public int getActionId() {
      return R.id.action_mainFragment_to_secondFragment;
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

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionMainFragmentToSecondFragment that = (ActionMainFragmentToSecondFragment) object;
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
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
      result = 31 * result + getAge();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionMainFragmentToSecondFragment(actionId=" + getActionId() + "){"
          + "userName=" + getUserName()
          + ", age=" + getAge()
          + "}";
    }
  }
}

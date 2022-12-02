package com.jinxin.jetpacktest;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.jinxin.jetpacktest.databinding.ActivityDataBindingTestBindingImpl;
import com.jinxin.jetpacktest.databinding.ActivityRecyclerViewBindingBindingImpl;
import com.jinxin.jetpacktest.databinding.ActivityTowWayBindingBindingImpl;
import com.jinxin.jetpacktest.databinding.ItemRecyclerViewBindingBindingImpl;
import com.jinxin.jetpacktest.databinding.LayoutContentBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYDATABINDINGTEST = 1;

  private static final int LAYOUT_ACTIVITYRECYCLERVIEWBINDING = 2;

  private static final int LAYOUT_ACTIVITYTOWWAYBINDING = 3;

  private static final int LAYOUT_ITEMRECYCLERVIEWBINDING = 4;

  private static final int LAYOUT_LAYOUTCONTENT = 5;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(5);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.jinxin.jetpacktest.R.layout.activity_data_binding_test, LAYOUT_ACTIVITYDATABINDINGTEST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.jinxin.jetpacktest.R.layout.activity_recycler_view_binding, LAYOUT_ACTIVITYRECYCLERVIEWBINDING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.jinxin.jetpacktest.R.layout.activity_tow_way_binding, LAYOUT_ACTIVITYTOWWAYBINDING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.jinxin.jetpacktest.R.layout.item_recycler_view_binding, LAYOUT_ITEMRECYCLERVIEWBINDING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.jinxin.jetpacktest.R.layout.layout_content, LAYOUT_LAYOUTCONTENT);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYDATABINDINGTEST: {
          if ("layout/activity_data_binding_test_0".equals(tag)) {
            return new ActivityDataBindingTestBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_data_binding_test is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYRECYCLERVIEWBINDING: {
          if ("layout/activity_recycler_view_binding_0".equals(tag)) {
            return new ActivityRecyclerViewBindingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_recycler_view_binding is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYTOWWAYBINDING: {
          if ("layout/activity_tow_way_binding_0".equals(tag)) {
            return new ActivityTowWayBindingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_tow_way_binding is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMRECYCLERVIEWBINDING: {
          if ("layout/item_recycler_view_binding_0".equals(tag)) {
            return new ItemRecyclerViewBindingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_recycler_view_binding is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTCONTENT: {
          if ("layout/layout_content_0".equals(tag)) {
            return new LayoutContentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_content is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(2);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    result.add(new com.jinxin.mvvm.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(11);

    static {
      sKeys.put(1, "ClickHandler");
      sKeys.put(2, "EventHandler");
      sKeys.put(0, "_all");
      sKeys.put(3, "book");
      sKeys.put(4, "imagePadding");
      sKeys.put(5, "localImage");
      sKeys.put(6, "networkImage");
      sKeys.put(7, "simpleViewModel");
      sKeys.put(8, "user");
      sKeys.put(9, "userName");
      sKeys.put(10, "viewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(5);

    static {
      sKeys.put("layout/activity_data_binding_test_0", com.jinxin.jetpacktest.R.layout.activity_data_binding_test);
      sKeys.put("layout/activity_recycler_view_binding_0", com.jinxin.jetpacktest.R.layout.activity_recycler_view_binding);
      sKeys.put("layout/activity_tow_way_binding_0", com.jinxin.jetpacktest.R.layout.activity_tow_way_binding);
      sKeys.put("layout/item_recycler_view_binding_0", com.jinxin.jetpacktest.R.layout.item_recycler_view_binding);
      sKeys.put("layout/layout_content_0", com.jinxin.jetpacktest.R.layout.layout_content);
    }
  }
}

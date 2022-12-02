package com.jinxin.jetpacktest.databinding;
import com.jinxin.jetpacktest.R;
import com.jinxin.jetpacktest.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityTowWayBindingBindingImpl extends ActivityTowWayBindingBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.EditText mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener mboundView1androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of simpleViewModel.userName
            //         is simpleViewModel.setUserName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView1);
            // localize variables for thread safety
            // simpleViewModel
            com.jinxin.jetpacktest.databinding.TowWaySimpleBindingViewModel simpleViewModel = mSimpleViewModel;
            // simpleViewModel != null
            boolean simpleViewModelJavaLangObjectNull = false;
            // simpleViewModel.userName
            java.lang.String simpleViewModelUserName = null;



            simpleViewModelJavaLangObjectNull = (simpleViewModel) != (null);
            if (simpleViewModelJavaLangObjectNull) {




                simpleViewModel.setUserName(((java.lang.String) (callbackArg_0)));
            }
        }
    };

    public ActivityTowWayBindingBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds));
    }
    private ActivityTowWayBindingBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.EditText) bindings[1];
        this.mboundView1.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.simpleViewModel == variableId) {
            setSimpleViewModel((com.jinxin.jetpacktest.databinding.TowWaySimpleBindingViewModel) variable);
        }
        else if (BR.viewModel == variableId) {
            setViewModel((com.jinxin.jetpacktest.databinding.TowWayBindingViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setSimpleViewModel(@Nullable com.jinxin.jetpacktest.databinding.TowWaySimpleBindingViewModel SimpleViewModel) {
        this.mSimpleViewModel = SimpleViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.simpleViewModel);
        super.requestRebind();
    }
    public void setViewModel(@Nullable com.jinxin.jetpacktest.databinding.TowWayBindingViewModel ViewModel) {
        this.mViewModel = ViewModel;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModel((com.jinxin.jetpacktest.databinding.TowWayBindingViewModel) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModel(com.jinxin.jetpacktest.databinding.TowWayBindingViewModel ViewModel, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.jinxin.jetpacktest.databinding.TowWaySimpleBindingViewModel simpleViewModel = mSimpleViewModel;
        java.lang.String simpleViewModelUserName = null;

        if ((dirtyFlags & 0x6L) != 0) {



                if (simpleViewModel != null) {
                    // read simpleViewModel.userName
                    simpleViewModelUserName = simpleViewModel.getUserName();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, simpleViewModelUserName);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView1, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView1androidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): simpleViewModel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}
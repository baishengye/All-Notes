package com.jinxin.mvvm.databinding;
import com.jinxin.mvvm.R;
import com.jinxin.mvvm.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMvvmMainBindingImpl extends ActivityMvvmMainBinding  {

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
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView3;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.TextView mboundView5;
    @NonNull
    private final android.widget.TextView mboundView6;
    @NonNull
    private final android.widget.TextView mboundView7;
    @NonNull
    private final android.widget.TextView mboundView8;
    @NonNull
    private final android.widget.TextView mboundView9;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMvvmMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private ActivityMvvmMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (androidx.swiperefreshlayout.widget.SwipeRefreshLayout) bindings[0]
            );
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView7 = (android.widget.TextView) bindings[7];
        this.mboundView7.setTag(null);
        this.mboundView8 = (android.widget.TextView) bindings[8];
        this.mboundView8.setTag(null);
        this.mboundView9 = (android.widget.TextView) bindings[9];
        this.mboundView9.setTag(null);
        this.profileName.setTag(null);
        this.refresh.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
        if (BR.user == variableId) {
            setUser((com.jinxin.mvvm.model.User) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setUser(@Nullable com.jinxin.mvvm.model.User User) {
        this.mUser = User;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.user);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
        java.lang.String userBio = null;
        java.lang.String integerToStringUserFollowing = null;
        com.jinxin.mvvm.model.User user = mUser;
        java.lang.String userCompany = null;
        java.lang.String mboundView5AndroidStringLocationUserLocation = null;
        java.lang.String mboundView6AndroidStringFollowersIntegerToStringUserFollowers = null;
        int userFollowers = 0;
        java.lang.String userBlog = null;
        java.lang.String userName = null;
        java.lang.String integerToStringUserFollowers = null;
        java.lang.String userAvatar = null;
        java.lang.String userLocation = null;
        java.lang.String userHtmlUrl = null;
        int userFollowing = 0;
        java.lang.String mboundView7AndroidStringFollowingIntegerToStringUserFollowing = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (user != null) {
                    // read user.bio
                    userBio = user.bio;
                    // read user.company
                    userCompany = user.company;
                    // read user.followers
                    userFollowers = user.followers;
                    // read user.blog
                    userBlog = user.blog;
                    // read user.name
                    userName = user.name;
                    // read user.avatar
                    userAvatar = user.avatar;
                    // read user.location
                    userLocation = user.location;
                    // read user.htmlUrl
                    userHtmlUrl = user.htmlUrl;
                    // read user.following
                    userFollowing = user.following;
                }


                // read Integer.toString(user.followers)
                integerToStringUserFollowers = java.lang.Integer.toString(userFollowers);
                // read @android:string/location
                mboundView5AndroidStringLocationUserLocation = mboundView5.getResources().getString(R.string.location, userLocation);
                // read Integer.toString(user.following)
                integerToStringUserFollowing = java.lang.Integer.toString(userFollowing);


                // read @android:string/followers
                mboundView6AndroidStringFollowersIntegerToStringUserFollowers = mboundView6.getResources().getString(R.string.followers, integerToStringUserFollowers);
                // read @android:string/following
                mboundView7AndroidStringFollowingIntegerToStringUserFollowing = mboundView7.getResources().getString(R.string.following, integerToStringUserFollowing);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, userName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, userBio);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, userCompany);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView5, mboundView5AndroidStringLocationUserLocation);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView6, mboundView6AndroidStringFollowersIntegerToStringUserFollowers);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView7, mboundView7AndroidStringFollowingIntegerToStringUserFollowing);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView8, userHtmlUrl);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView9, userBlog);
            com.jinxin.mvvm.bindingadapter.ImageViewBindingAdapter.setImage(this.profileName, userAvatar, (int)0);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): user
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}
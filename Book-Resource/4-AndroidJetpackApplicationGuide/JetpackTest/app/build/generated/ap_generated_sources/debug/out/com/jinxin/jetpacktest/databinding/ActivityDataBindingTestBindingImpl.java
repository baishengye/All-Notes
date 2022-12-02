package com.jinxin.jetpacktest.databinding;
import com.jinxin.jetpacktest.R;
import com.jinxin.jetpacktest.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityDataBindingTestBindingImpl extends ActivityDataBindingTestBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(11);
        sIncludes.setIncludes(1, 
            new String[] {"layout_content"},
            new int[] {10},
            new int[] {com.jinxin.jetpacktest.R.layout.layout_content});
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    @Nullable
    private final com.jinxin.jetpacktest.databinding.LayoutContentBinding mboundView11;
    @NonNull
    private final android.widget.Button mboundView5;
    @NonNull
    private final android.widget.ImageView mboundView6;
    @NonNull
    private final android.widget.ImageView mboundView7;
    @NonNull
    private final android.widget.ImageView mboundView8;
    @NonNull
    private final android.widget.Button mboundView9;
    // variables
    // values
    private int mOldImagePadding;
    // listeners
    private OnClickListenerImpl mEventHandlerOnButtonClickedAndroidViewViewOnClickListener;
    private OnClickListenerImpl1 mClickHandlerOnClickAndroidViewViewOnClickListener;
    // Inverse Binding Event Handlers

    public ActivityDataBindingTestBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private ActivityDataBindingTestBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[2]
            );
        this.mboundView0 = (androidx.core.widget.NestedScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView11 = (com.jinxin.jetpacktest.databinding.LayoutContentBinding) bindings[10];
        setContainedBinding(this.mboundView11);
        this.mboundView5 = (android.widget.Button) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.ImageView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView7 = (android.widget.ImageView) bindings[7];
        this.mboundView7.setTag(null);
        this.mboundView8 = (android.widget.ImageView) bindings[8];
        this.mboundView8.setTag(null);
        this.mboundView9 = (android.widget.Button) bindings[9];
        this.mboundView9.setTag(null);
        this.tvAuthor.setTag(null);
        this.tvRating.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x40L;
        }
        mboundView11.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (mboundView11.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.networkImage == variableId) {
            setNetworkImage((java.lang.String) variable);
        }
        else if (BR.imagePadding == variableId) {
            setImagePadding((int) variable);
        }
        else if (BR.book == variableId) {
            setBook((com.jinxin.jetpacktest.databinding.Book) variable);
        }
        else if (BR.localImage == variableId) {
            setLocalImage((int) variable);
        }
        else if (BR.ClickHandler == variableId) {
            setClickHandler((com.jinxin.jetpacktest.databinding.DataBindingTestActivity.ClickHandle) variable);
        }
        else if (BR.EventHandler == variableId) {
            setEventHandler((com.jinxin.jetpacktest.databinding.EventHandleListener) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setNetworkImage(@Nullable java.lang.String NetworkImage) {
        this.mNetworkImage = NetworkImage;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.networkImage);
        super.requestRebind();
    }
    public void setImagePadding(int ImagePadding) {
        this.mImagePadding = ImagePadding;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.imagePadding);
        super.requestRebind();
    }
    public void setBook(@Nullable com.jinxin.jetpacktest.databinding.Book Book) {
        this.mBook = Book;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.book);
        super.requestRebind();
    }
    public void setLocalImage(int LocalImage) {
        this.mLocalImage = LocalImage;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.localImage);
        super.requestRebind();
    }
    public void setClickHandler(@Nullable com.jinxin.jetpacktest.databinding.DataBindingTestActivity.ClickHandle ClickHandler) {
        this.mClickHandler = ClickHandler;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.ClickHandler);
        super.requestRebind();
    }
    public void setEventHandler(@Nullable com.jinxin.jetpacktest.databinding.EventHandleListener EventHandler) {
        this.mEventHandler = EventHandler;
        synchronized(this) {
            mDirtyFlags |= 0x20L;
        }
        notifyPropertyChanged(BR.EventHandler);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        mboundView11.setLifecycleOwner(lifecycleOwner);
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
        android.view.View.OnClickListener eventHandlerOnButtonClickedAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener clickHandlerOnClickAndroidViewViewOnClickListener = null;
        int bookRating = 0;
        java.lang.String networkImage = mNetworkImage;
        int imagePadding = mImagePadding;
        java.lang.String bookTitle = null;
        com.jinxin.jetpacktest.databinding.Book book = mBook;
        int localImage = mLocalImage;
        com.jinxin.jetpacktest.databinding.DataBindingTestActivity.ClickHandle clickHandler = mClickHandler;
        java.lang.String bookRatingUtilGetRatingStringBookRating = null;
        com.jinxin.jetpacktest.databinding.EventHandleListener eventHandler = mEventHandler;
        java.lang.String bookAuthor = null;

        if ((dirtyFlags & 0x49L) != 0) {


            if ((dirtyFlags & 0x41L) != 0) {
            }
        }
        if ((dirtyFlags & 0x42L) != 0) {
        }
        if ((dirtyFlags & 0x44L) != 0) {



                if (book != null) {
                    // read book.rating
                    bookRating = book.getRating();
                    // read book.title
                    bookTitle = book.getTitle();
                    // read book.author
                    bookAuthor = book.getAuthor();
                }


                // read BookRatingUtil.getRatingString(book.rating)
                bookRatingUtilGetRatingStringBookRating = com.jinxin.jetpacktest.databinding.BookRatingUtil.getRatingString(bookRating);
        }
        if ((dirtyFlags & 0x50L) != 0) {



                if (clickHandler != null) {
                    // read ClickHandler::onClick
                    clickHandlerOnClickAndroidViewViewOnClickListener = (((mClickHandlerOnClickAndroidViewViewOnClickListener == null) ? (mClickHandlerOnClickAndroidViewViewOnClickListener = new OnClickListenerImpl1()) : mClickHandlerOnClickAndroidViewViewOnClickListener).setValue(clickHandler));
                }
        }
        if ((dirtyFlags & 0x60L) != 0) {



                if (eventHandler != null) {
                    // read EventHandler::onButtonClicked
                    eventHandlerOnButtonClickedAndroidViewViewOnClickListener = (((mEventHandlerOnButtonClickedAndroidViewViewOnClickListener == null) ? (mEventHandlerOnButtonClickedAndroidViewViewOnClickListener = new OnClickListenerImpl()) : mEventHandlerOnButtonClickedAndroidViewViewOnClickListener).setValue(eventHandler));
                }
        }
        // batch finished
        if ((dirtyFlags & 0x44L) != 0) {
            // api target 1

            this.mboundView11.setBook(book);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvAuthor, bookAuthor);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvRating, bookRatingUtilGetRatingStringBookRating);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTitle, bookTitle);
        }
        if ((dirtyFlags & 0x60L) != 0) {
            // api target 1

            this.mboundView5.setOnClickListener(eventHandlerOnButtonClickedAndroidViewViewOnClickListener);
        }
        if ((dirtyFlags & 0x41L) != 0) {
            // api target 1

            com.jinxin.jetpacktest.databinding.ImageViewBindingAdapter.setImage(this.mboundView6, networkImage, (int)0);
        }
        if ((dirtyFlags & 0x48L) != 0) {
            // api target 1

            com.jinxin.jetpacktest.databinding.ImageViewBindingAdapter.setImage(this.mboundView7, localImage);
        }
        if ((dirtyFlags & 0x42L) != 0) {
            // api target 1

            com.jinxin.jetpacktest.databinding.ImageViewBindingAdapter.setPadding(this.mboundView8, this.mOldImagePadding, imagePadding);
        }
        if ((dirtyFlags & 0x49L) != 0) {
            // api target 1

            com.jinxin.jetpacktest.databinding.ImageViewBindingAdapter.setImage(this.mboundView8, networkImage, localImage);
        }
        if ((dirtyFlags & 0x50L) != 0) {
            // api target 1

            this.mboundView9.setOnClickListener(clickHandlerOnClickAndroidViewViewOnClickListener);
        }
        if ((dirtyFlags & 0x42L) != 0) {
            this.mOldImagePadding = imagePadding;
        }
        executeBindingsOn(mboundView11);
    }
    // Listener Stub Implementations
    public static class OnClickListenerImpl implements android.view.View.OnClickListener{
        private com.jinxin.jetpacktest.databinding.EventHandleListener value;
        public OnClickListenerImpl setValue(com.jinxin.jetpacktest.databinding.EventHandleListener value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.onButtonClicked(arg0); 
        }
    }
    public static class OnClickListenerImpl1 implements android.view.View.OnClickListener{
        private com.jinxin.jetpacktest.databinding.DataBindingTestActivity.ClickHandle value;
        public OnClickListenerImpl1 setValue(com.jinxin.jetpacktest.databinding.DataBindingTestActivity.ClickHandle value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.onClick(arg0); 
        }
    }
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): networkImage
        flag 1 (0x2L): imagePadding
        flag 2 (0x3L): book
        flag 3 (0x4L): localImage
        flag 4 (0x5L): ClickHandler
        flag 5 (0x6L): EventHandler
        flag 6 (0x7L): null
    flag mapping end*/
    //end
}
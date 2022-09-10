## ViewGroup是如何向View分发事件的

### ViewGroup
```java
public class MyViewGroup extends MyView{
    List<MyView> childViewList=new ArrayList<MyView>();
    
    public MyViewGroup() {
    }

    public MyViewGroup(int left, int right, int top, int bottom) {
        super(left, right, top, bottom);
    }

    /**
     * 添加View
     * @param view
     */
    public void addView(MyView view){
        if(view==null){
            return;
        }

        childViewList.add(view);

        mChildren=(MyView[]) childViewList.toArray(new MyView[childViewList.size()]);
    }


    private MyView[] mChildren=new MyView[0];
    private boolean handled;
    private TouchTarget mFirstTouchTarget;

    private static final class TouchTarget{
        //单链表实现
        public MyView view;
        public TouchTarget next;

        //链表头
        private static TouchTarget sRecycleBin;

        private static int sRecycleCount;

        private static final Object sRecycleLock=new Object[0];

        public static TouchTarget obtain(MyView v){
            TouchTarget target;
            synchronized (sRecycleBin){
                if(sRecycleBin==null){
                    target=new TouchTarget();
                }else{
                    target=sRecycleBin;
                }
                sRecycleBin=target.next;

                sRecycleCount--;
                target.next=null;
            }
            target.view=v;
            return target;
        }
    }

    /**
     * 事件分发
     * @param ev
     */
    public boolean dispatchTouchEvent(MyMotionEvent ev) {
        System.out.println("MyViewGroup========dispatchTouchEvent===========");

        //事件是否在这一层被拦截
        final boolean intercepted;
        int actionMasked=ev.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN||mFirstTouchTarget != null) {
            final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
            if (!disallowIntercept) {
                intercepted = onInterceptTouchEvent(ev);
                ev.setAction(action); // restore action in case it was changed
            } else {
                intercepted = false;
            }
        } else {
            // There are no touch targets and this action is not an initial down
            // so this view group continues to intercept touches.
            intercepted = true;
        }
        //如果是手指按下并且本View不拦截事件，就会把事件往子View分发
        if(actionMasked == MotionEvent.ACTION_DOWN&&!intercepted){
            final MyView[] children=mChildren;
            //由于摆放View时，重叠的View中位于上层的View会优先接收到事件，而上层的View更晚进入List，应更接近List的尾端
            // 所以此处遍历分发时逆序
            for (int i = children.length-1; i >= 0 ; i--) {
                MyView child = children[i];
                if(!child.isContainer(ev.getX(),ev.getY())){
                    //接收不到
                    continue;
                }

                //往子View分发，如果子View消费了事件
                if(dispatchTransformTouchEvent(ev,child)){
                    //子View消费掉了
                    handled =true;

                    mFirstTouchTarget =TouchTarget.obtain(child);
                }
            }
        }

        //如果遍历完了子View事件都没有被消耗,就传给父类处理
        if(mFirstTouchTarget==null){
            dispatchTransformTouchEvent(ev,null);
        }else {
            TouchTarget target=mFirstTouchTarget;
            while (target!=null){
                TouchTarget next=target.next;
                if (dispatchTransformTouchEvent(ev,target.view)){
                    handled=true;
                }
                target=next;
            }
        }

        return handled;
    }

    //当触摸可以被子View接收的时候调用
    private boolean dispatchTransformTouchEvent(MyMotionEvent ev,MyView child){
        boolean handled=false;
        if(child!=null){
            //子View是否消费了事件
            handled=child.dispatchTouchEvent(ev);
        }else {
            super.dispatchTouchEvent(ev);
        }
        return handled;
    }

    public boolean onInterceptTouchEvent(MyMotionEvent ev){
        return false;
    }
}
```

### View
```java
public class MyView {

    private static final String TAG = "MyView";

    private OnTouchListener mOnTouchListener;
    private OnClickListener mOnClickListener;

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    protected int left;
    protected int right;
    protected int top;
    protected int bottom;

    private String name;

    public MyView() {
    }

    public MyView(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
    /**
     * 触摸的位置是否在View之内
     * @param x
     * @param y
     * @return
     */
    protected boolean isContainer(int x, int y) {
        if(x>=top&&x<=bottom&&y>=left&&y<=right){
            return true;
        }
        return false;
    }

    /**
     * 事件分发
     */
    protected boolean dispatchTouchEvent(MyMotionEvent ev) {
        boolean result=false;

        System.out.println(TAG+"======dispatchTouchEvent=====");

        //优先处理onTouchListener
        if(mOnTouchListener!=null&&mOnTouchListener.OnTouch(this,ev)){
            result=true;
        }

        //如果onTouchListener没有把事件消费掉，才轮得到onTouchEvent
        if(!result&&onTouchEvent(ev)){
            //如果onTouchEvent把事件消费掉了
            result=true;
        }

        if(!result&&mOnClickListener!=null&&mOnClickListener.onClick(this,ev)){
            result=true;
        }

        return result;
    }

    private boolean onTouchEvent(MyMotionEvent ev){
        System.out.println("MyView==========onTouchEvent============");
        return true;
    }

}

```

Activity为什么要有事件分发？
dialog和popupWindow的事件分发怎么处理?

RecylerView
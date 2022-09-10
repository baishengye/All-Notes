```java
public class MyScrollView extends ScrollView {

    private float lastY;
    //里外容器的滑动方向是一致的，这种情况的主流解决方法
    // 向上滑动就ListView先滑动,ListView滑动不了再滑动ScrollView
    //向下滑动就ScrollView先滑动,ScrollView滑动不了再滑动ListView
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted=false;
        int actionMasked=ev.getActionMasked();
        switch (actionMasked){
            case MotionEvent.ACTION_DOWN:{}
            case MotionEvent.ACTION_UP:{}
            case MotionEvent.ACTION_CANCEL:{
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                LinearLayout layout = (LinearLayout) getChildAt(0);
                ListView listView = (ListView) layout.getChildAt(1);
                if(isUp(ev.getY())){
                    //如果是向上滑动
                    if(isListViewCanNotUp(listView)){
                        //如果listView无法向上滑动了
                        intercepted=true;
                    }else {
                        //如果listView可以向上滑动了
                    }
                }else {
                    //如果是向下滑动
                    if(isScrollViewCanDown()){
                        //如果ScrollView能向下滑动了
                        intercepted=true;
                    }else {
                        //如果ScrollView不能向下滑动了
                    }
                }
                break;
            }
        }
        lastY = ev.getY();
        super.onInterceptTouchEvent(ev);
        return intercepted;
    }

    /**
     * ScrollView能向下滑动
     * @return
     */
    private boolean isScrollViewCanDown() {
        return getScrollY()>0;
    }

    /**
     * listView是否不能向上滑动
     * @param listView
     * @return
     */
    private boolean isListViewCanNotUp(ListView listView) {
        boolean result=false;
        int pos=listView.getLastVisiblePosition();
        int cnt=listView.getCount();
        if (pos == (cnt - 1)) {
            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result= (listView.getHeight()>=bottomChildView.getBottom());
        }
        return  result;
    }

    /**
     * listView是否不能向下滑动
     * @param listView
     * @return
     */
    private boolean isListViewCanNotDown(ListView listView){
        boolean result=false;
        if(listView.getFirstVisiblePosition()==0){
            final View topChildView = listView.getChildAt(0);
            result=topChildView.getTop()==0;
        }
        return result ;
    }

    /**
     * 当前是否是向上滑动
     * @param y
     * @return
     */
    private boolean isUp(float y){
        if(y-lastY<0){
            return true;
        }
        return false;
    }
}

```
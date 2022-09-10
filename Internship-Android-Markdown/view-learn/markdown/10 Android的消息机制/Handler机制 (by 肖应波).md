## Handler机制

### Handler的大概用法
```java
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ActivityMainBinding binding;

    //主线程会主动创建Looper.loop();
    private Handler mHandlerMainAndSon =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if(msg.what==0){
                Log.d(TAG, (String) msg.obj);
            }

            return false;//类似事件分发,如果false就继续消息分发,true就不分发
        }
    });

    private Handler mHandlerSonAndSon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandlerMainAndSon.post(new Runnable() {
                    @Override
                    public void run() {
                        //这里的逻辑实际是在主线程中执行的
                        binding.tv.setText("handler.post修改UI");
                    }
                });
            }
        });

        binding.btnPostAtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandlerMainAndSon.postAtTime(new Runnable() {
                    @Override
                    public void run() {
                        binding.tv.setText("handler.postAtTime修改UI");
                    }
                    //在2秒内完成逻辑
                },2000);
            }
        });

        binding.btnPostDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandlerMainAndSon.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.tv.setText("handler.postDelay修改UI");
                    }
                    //2秒后执行逻辑
                },2000);
            }
        });

        binding.btnSendMessage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //用obtain获取一个消息，会从消息池中取出一个消息（消息池不为null），
                        // 消息池的消息减1。如果消息池为空，则调用new Message新建一个
                        Message message = mHandlerMainAndSon.obtainMessage(0);
                        //从消息队列中取出一个消息,没有就会建一个
                        message.obj="主线程和子线程通信";
                        message.sendToTarget();
                    }
                }).start();
            }
        });

        binding.btnSendMessage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message message = Message.obtain();
                        //从消息队列中取出一个消息,没有就会建一个
                        message.obj = "子线程和子线程通信";
                        message.sendToTarget();
                    }
                });

                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        mHandlerSonAndSon=new Handler(new Handler.Callback() {
                            @Override
                            public boolean handleMessage(@NonNull Message msg) {
                                if(msg.what==1){
                                    Log.d(TAG, (String) msg.obj);
                                }
                                return false;
                            }
                        });
                        //子线程需要手动创建
                        Looper.loop();
                    }
                });

                thread1.start();
                thread2.start();
            }
        });
    }
}
```


### 消息机制原理
**带着问题去学习**
- 消息发送到哪里去了？
- 消息是如何处理的
- 消息是怎么被维护的？被谁维护的？

**看源码的步骤**
由大到小


#### sendMessage()如何把Message发送到消息队列中的?
```java
mHandlerSonAndSon.sendMessage(message);
=>  public final boolean sendMessage(@NonNull Message msg) {
        return sendMessageDelayed(msg, 0);
    }


=>  public final boolean sendMessageDelayed(@NonNull Message msg, long delayMillis) {
        ...
        return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
    }

    
=>  public boolean sendMessageAtTime(@NonNull Message msg, long uptimeMillis) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessageAtTime() called with no mQueue");
            Log.w("Looper", e.getMessage(), e);
            return false;
        }
        return enqueueMessage(queue, msg, uptimeMillis);
    }


=>  private boolean enqueueMessage(@NonNull MessageQueue queue, @NonNull Message msg,
            long uptimeMillis) {
        msg.target = this;
        msg.workSourceUid = ThreadLocalWorkSource.getUid();

        if (mAsynchronous) {
            msg.setAsynchronous(true);
        }
        return queue.enqueueMessage(msg, uptimeMillis);
    }

=>  boolean enqueueMessage(Message msg, long when) {
        if (msg.target == null) {
            throw new IllegalArgumentException("Message must have a target.");
        }

        synchronized (this) {
            if (msg.isInUse()) {
                throw new IllegalStateException(msg + " This message is already in use.");
            }

            if (mQuitting) {
                IllegalStateException e = new IllegalStateException(
                        msg.target + " sending message to a Handler on a dead thread");
                Log.w(TAG, e.getMessage(), e);
                msg.recycle();
                return false;
            }

            msg.markInUse();
            msg.when = when;
            Message p = mMessages;
            boolean needWake;
            if (p == null || when == 0 || when < p.when) {
                // New head, wake up the event queue if blocked.
                msg.next = p;
                mMessages = msg;
                needWake = mBlocked;
            } else {
                // Inserted within the middle of the queue.  Usually we don't have to wake
                // up the event queue unless there is a barrier at the head of the queue
                // and the message is the earliest asynchronous message in the queue.
                needWake = mBlocked && p.target == null && msg.isAsynchronous();
                Message prev;
                for (;;) {
                    prev = p;
                    p = p.next;
                    if (p == null || when < p.when) {
                        break;
                    }
                    if (needWake && p.isAsynchronous()) {
                        needWake = false;
                    }
                }
                msg.next = p; // invariant: p == prev.next
                prev.next = msg;
            }

            // We can assume mPtr != 0 because mQuitting is false.
            if (needWake) {
                nativeWake(mPtr);
            }
        }
        return true;
    }
```
**至此，message就发送到了MessageQueue中了.**

#### 理解enqueueMessage()
message发送到了MessageQueue中,但是我们去看MessageQueue类中会发现并且有一个容器去存放Message,那么Message是怎么存的呢?而我们去看Message.class的时候发现Message有一个next元素,所以答案是Message的用链表存放的

**为什么用链表而不用数组**
数组要求连续空间，初始容量不足还要扩容，空间利用率不高，而且扩容很耗时


#### Loop是如何处理Message的?
**在一个Handler创建出来之后，必须要调用Looper.loop()开始循环处理消息**
- 主线程中不需要手动调用Looper.loop()，因为ActvityThread本身就会调用Looper.loop()
- 子线程需要手动调用Looper.loop()开始循环.

##### 看看如何处理
```java
Looper.loop()

=>  public static void loop() {
        final Looper me = myLooper();//获取这个线程的Looper
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        ...

        // Make sure the identity of this thread is that of the local process,
        // and keep track of what that identity token actually is.
        Binder.clearCallingIdentity();
        final long ident = Binder.clearCallingIdentity();

        // Allow overriding a threshold with a system prop. e.g.
        // adb shell 'setprop log.looper.1000.main.slow 1 && stop && start'
        final int thresholdOverride =
                SystemProperties.getInt("log.looper."
                        + Process.myUid() + "."
                        + Thread.currentThread().getName()
                        + ".slow", 0);

        me.mSlowDeliveryDetected = false;

        for (;;) {//开始循环
            if (!loopOnce(me, ident, thresholdOverride)) {//主线的函数
                return;
            }
        }
    }


=>  private static boolean loopOnce(final Looper me,
            final long ident, final int thresholdOverride) {
        Message msg = me.mQueue.next(); // 主线的函数
        if (msg == null) {
            // No message indicates that the message queue is quitting.
            return false;
        }

        ...
        // Make sure the observer won't change while processing a transaction.
        final Observer observer = sObserver;

        ...

        if (traceTag != 0 && Trace.isTagEnabled(traceTag)) {
            Trace.traceBegin(traceTag, msg.target.getTraceName(msg));
        }

        final long dispatchStart = needStartTime ? SystemClock.uptimeMillis() : 0;
        final long dispatchEnd;
        Object token = null;
        if (observer != null) {
            token = observer.messageDispatchStarting();
        }
        long origWorkSource = ThreadLocalWorkSource.setUid(msg.workSourceUid);
        try {
            msg.target.dispatchMessage(msg);//主线的函数
            if (observer != null) {
                observer.messageDispatched(token, msg);
            }
            dispatchEnd = needEndTime ? SystemClock.uptimeMillis() : 0;
        } catch (Exception exception) {
            if (observer != null) {
                observer.dispatchingThrewException(token, msg, exception);
            }
            throw exception;
        } finally {
            ThreadLocalWorkSource.restore(origWorkSource);
            if (traceTag != 0) {
                Trace.traceEnd(traceTag);
            }
        }
        if (logSlowDelivery) {
            if (me.mSlowDeliveryDetected) {
                if ((dispatchStart - msg.when) <= 10) {
                    Slog.w(TAG, "Drained");
                    me.mSlowDeliveryDetected = false;
                }
            } else {
                if (showSlowLog(slowDeliveryThresholdMs, msg.when, dispatchStart, "delivery",
                        msg)) {
                    // Once we write a slow delivery log, suppress until the queue drains.
                    me.mSlowDeliveryDetected = true;
                }
            }
        }
        if (logSlowDispatch) {
            showSlowLog(slowDispatchThresholdMs, dispatchStart, dispatchEnd, "dispatch", msg);
        }

        if (logging != null) {
            logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
        }

        // Make sure that during the course of dispatching the
        // identity of the thread wasn't corrupted.
        final long newIdent = Binder.clearCallingIdentity();
        if (ident != newIdent) {
            Log.wtf(TAG, "Thread identity changed from 0x"
                    + Long.toHexString(ident) + " to 0x"
                    + Long.toHexString(newIdent) + " while dispatching to "
                    + msg.target.getClass().getName() + " "
                    + msg.callback + " what=" + msg.what);
        }

        msg.recycleUnchecked();

        return true;
    }

=>  Message next() {
        ...
        for (;;) {
            ...

            synchronized (this) {
                // Try to retrieve the next message.  Return if found.
                ...
                Message prevMsg = null;
                Message msg = mMessages;
                if (msg != null && msg.target == null) {
                    // Stalled by a barrier.  Find the next asynchronous message in the queue.
                    do {
                        prevMsg = msg;
                        msg = msg.next;
                    } while (msg != null && !msg.isAsynchronous());
                }
                if (msg != null) {
                    if (now < msg.when) {
                        ...
                    } else {//获取到消息并且返回
                        // Got a message.
                        mBlocked = false;
                        if (prevMsg != null) {
                            prevMsg.next = msg.next;
                        } else {
                            mMessages = msg.next;
                        }
                        msg.next = null;
                        ...
                        msg.markInUse();
                        return msg;
                    }
                } else {
                    // No more messages.
                    nextPollTimeoutMillis = -1;
                }

                // Process the quit message now that all pending messages have been handled.
                if (mQuitting) {
                    dispose();
                    return null;
                }

                ...
            }

            ...
        }
    }


=>  public void dispatchMessage(@NonNull Message msg) {
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            handleMessage(msg);//处理Message
        }
    }
```
**Looper.loop()开始循环从MessageQueue中取出Message,然后交给Message的target(目标Handler)处理(通过handlerMessage())**

#### Message是怎么维护的

##### Handler的创建时机
```java
以子线程中的Handler创建为例

new Thread(new Runnable() {
    @Override
    public void run() {
        Looper.prepare();
        mHandlerSonAndSon=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==1){
                    Log.d(TAG, (String) msg.obj);
                }
                return false;
            }
        });
        Looper.loop();
    }
}).start();
```

##### Looper的创建时机
```java
Looper.prepare();

=>  public static void prepare() {
        prepare(true);
    }

=>  private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(quitAllowed));//创建一个Looper了
    }
```

##### MessageQueue的创建时机
```java
创建Looper的时候会把对应的MessageQueue创建出来
private Looper(boolean quitAllowed) {
    mQueue = new MessageQueue(quitAllowed);
    mThread = Thread.currentThread();
}
```

##### Message对象为什么要通过obtain进行创建?为什么要使用复用池?
- `new Message`会有非常多,会占用非常多内存
- 使用复用池可以减少对象的创建过程

##### 其他
- MessageQueue是通过同步锁来保证线程安全的
- 一个线程只能有一个Looper和MessageQueue(使用ThreadLocal来确保)

##### 为什么Handler会导致内存泄露?
- Handler是Actiivity中的非静态内部类或者匿名内部类,所以Handler会持有Activity的引用
- Message会持有Handler的引用
- Message会在MessageQueue中循环使用
- MessageQueue和Looper一起创建，Looper会持有MessageQueue
- Looper和UI线程绑定了
- UI线程和Application的生命周期绑定了
**所以Activity的生命周期和Application的生命周期绑定了**
当Activity被finish()之后，GC会去回收Activity，但是Activity和Application绑定了，无法被回收，然后就产生了内存泄漏

##### 解决方案

###### 静态内部类 + 弱引用
```java
切断Activity和Handler的联系
   private static class MyHandler extends Handler {
        //弱引用，在垃圾回收时，activity可被回收
        private WeakReference<MainActivity> mWeakReference;

        public MyHandler(MainActivity activity) {
            this.mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    }
```

###### 如果将Handler声明成可能导致内存泄漏的情况，在Activity销毁时，可清空Handler中未执行或正在执行的Callback以及Message
```java
切断Handler和Message的联系
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空handler管道和队列
        mHandler.removeCallbacksAndMessages(null);
    }
```

###### 非静态内部类 + 弱引用，在activity要回收时清除引用（麻烦，不推荐）
```java
切断Activity和Handler的联系
    private class MyHandler extends Handler {
        //弱引用，在垃圾回收时，activity可被回收
        private WeakReference<MainActivity> mWeakReference;

        public MyHandler() {
            this.mWeakReference = new WeakReference<>(MainActivity.this);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    }
    
    private MyHandler mHandler = new MyHandler();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //手动清除应用
        mHandler.mWeakReference.clear();
    }
```
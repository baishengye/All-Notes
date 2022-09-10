package com.bo.testthread;

import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;
//=============================================================================================================================
/**
 * 阻塞队列
 * BlockingQueue
 * 生产者-消费者模式
 * Object.wait()和Object.notify()和非阻塞队列(优先队列)
 */
class TestUnBlockingQueue{
    private final int queueSize=10;
    private final PriorityQueue<Integer> queue=new PriorityQueue<Integer>(queueSize);

    /**
     * 消费者线程
     */
    class ConsumerThread extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (queue){
                    while (queue.size()==0){
                        try{
                            System.out.println("队列空，等待数据");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    //每次移走队列元素
                    queue.poll();
                    queue.notify();
                }
            }
        }
    }

    /**
     * 生产者
     */
    class ProducerThread extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (queue){
                    while (queue.size()==queueSize){
                        try {
                            System.out.println("队列满，等待空余空间");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    //每次插入一个元素
                    queue.offer(1);
                    queue.notify();
                }
            }
        }
    }

    public static void main(String[] args) {
        TestUnBlockingQueue test = new TestUnBlockingQueue();
        ConsumerThread consumer = test.new ConsumerThread();
        ProducerThread producer = test.new ProducerThread();

        producer.start();
        consumer.start();
    }
}


//=============================================================================================================================
/**
 * 阻塞队列
 * BlockingQueue
 * 生产者-消费者模式
 * 阻塞队列
 */
class TestBlockingQueue{
    private final int queueSize=10;
    private final ArrayBlockingQueue<Integer> queue=new ArrayBlockingQueue<Integer>(queueSize);

    /**
     * 消费者线程
     */
    class ConsumerThread extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    Integer take = queue.take();
                    if(take!=null)
                        System.out.println("队列没空");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生产者
     */
    class ProducerThread extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    queue.put(1);
                    System.out.println("队列没满");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        TestBlockingQueue test = new TestBlockingQueue();
        ConsumerThread consumer = test.new ConsumerThread();
        ProducerThread producer = test.new ProducerThread();

        producer.start();
        consumer.start();
    }
}

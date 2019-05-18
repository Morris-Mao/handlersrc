package com.mao.handler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {
    int takeIndex = 0;
    int putIndex=0;
    Message[] items = new Message[10];
    Object wait = new Object();
    private Lock lock;
    private Condition notEmpty;
    private Condition notFull;
    int count = 0;

    public MessageQueue() {
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    public void enqueueMessage(Message msg) {
        try{
            lock.lock();
            while (count == items.length) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            items[putIndex] = msg;
            putIndex = (++putIndex == items.length)?0:putIndex;
            count++;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public Message next() {
        Message msg = null;
        try {
            lock.lock();
            while(count== 0) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            takeIndex = (++takeIndex == items.length)?0: takeIndex;
            msg = items[takeIndex];
            count--;
            notFull.signalAll();
        }  finally {
            lock.unlock();
        }

        return msg;
    }
}

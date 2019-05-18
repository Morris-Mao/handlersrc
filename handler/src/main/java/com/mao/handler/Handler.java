package com.mao.handler;

public class Handler {
    private MessageQueue mQueue;
    private Looper mLooper;

    public Handler() {
        mLooper = Looper.myLooper();
        this.mQueue = mLooper.mQueue;
    }

    public void sendMesage(Message msg) {
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }

    public void handleMessage(Message msg) {

    }
}

package com.mao.handler;

public class Message {
    Handler target;
    public int what;
    public Object obj;

    @Override
    public String toString() {
        return "Message{" +
                "obj=" + obj +
                '}';
    }
}

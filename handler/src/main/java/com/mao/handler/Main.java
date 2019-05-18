package com.mao.handler;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        // 轮询器初始化
        Looper.prepare();
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                System.out.println("Receive:" + msg.obj);
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    while (true) {
                        Message msg = new Message();
                        msg.what = 1;
                        synchronized (UUID.class) {
                            msg.obj = Thread.currentThread().getName()+",Send Message:" + UUID.randomUUID();
                        }
                        System.out.println(msg);
                        handler.sendMesage(msg);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        Looper.loop();
    }


}

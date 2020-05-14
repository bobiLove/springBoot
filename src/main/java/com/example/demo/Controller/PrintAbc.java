package com.example.demo.Controller;

public class PrintAbc implements Runnable {

    private String name;
    private Object prev;
    private Object self;

    private PrintAbc(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name);
                    count--;

                    self.notifyAll();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        PrintAbc pa = new PrintAbc("A", c, a);
        PrintAbc pb = new PrintAbc("B", a, b);
        PrintAbc pc = new PrintAbc("C", b, c);


        new Thread(pa).start();
        Thread.sleep(100);
        new Thread(pb).start();
        Thread.sleep(100);
        new Thread(pc).start();
        Thread.sleep(100);
    }
}
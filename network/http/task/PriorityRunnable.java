package com.sobot.network.http.task;

/* loaded from: source-8303388-dex2jar.jar:com/sobot/network/http/task/PriorityRunnable.class */
public class PriorityRunnable extends PriorityObject<Runnable> implements Runnable {
    public PriorityRunnable(int i, Runnable runnable) {
        super(i, runnable);
    }

    @Override // java.lang.Runnable
    public void run() {
        ((Runnable) this.obj).run();
    }
}

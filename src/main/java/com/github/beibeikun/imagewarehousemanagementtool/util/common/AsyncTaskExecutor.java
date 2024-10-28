package com.github.beibeikun.imagewarehousemanagementtool.util.common;

import javax.swing.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class AsyncTaskExecutor {

    /**
     * 在后台执行一个任务，并在Swing事件分发线程中处理结果。
     * @param task 需要在后台执行的任务。
     */
    public static <T> void executeInBackground(Callable<T> task) {
        SwingWorker<T, Void> worker = new SwingWorker<T, Void>() {
            @Override
            protected T doInBackground() throws Exception {
                return task.call();  // 调用传入的任务
            }

            @Override
            protected void done() {
                try {
                    get();// 尝试获取背景任务的结果
                } catch (InterruptedException | ExecutionException ignored) {
                }
            }
        };
        worker.execute();  // 执行SwingWorker
    }
}

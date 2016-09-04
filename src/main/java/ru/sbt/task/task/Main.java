package ru.sbt.task.task;

/**
 * Created by i.viktor on 04/09/16.
 */
public class Main {
    public static void main(String[] args) {
        Task<String> task = new Task<>(() -> {
            Thread.sleep(1000);
            return "Test";
        });
        Runnable getCalcValue = () -> {
            System.out.println(task.get());
        };
        createThread(getCalcValue);

        Task<String> task2 = new Task<>(() -> {
            Thread.sleep(1000);
            throw new RuntimeException("Error during process");
        });
        Runnable getCalcValue2 = () -> {
            System.out.println(task2.get());
        };
        createThread(getCalcValue2);

    }

    private static void createThread(Runnable runnable) {
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }
}

package ru.naumen.collection.task4;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */

//общая сложность алгоритма О(1)

public class ConcurrentCalculationManager<T> {
    //используется потому что, гарантирует выполнение операций в том порядке, в котором задачи добавлялись
    //вставка и извлечение выполняются за O(1)
    private final BlockingQueue<Future<T>> blockingQueue = new LinkedBlockingQueue<>();
    // ExecutorService с использованием Executors.newCachedThreadPool() позволяет создавать пул потоков
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    public void addTask(Supplier<T> task) {

        //используем submit() для асинхронного выполнения задачи
        //сложность О(1) для добавления задачи в очередь blockingQueue, O(1) для отправки задачи в ExecutorService
        blockingQueue.add(executorService.submit(task::get));
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() throws InterruptedException{
        try {
            return blockingQueue.take().get();//О(1)

        } catch (ExecutionException e) {

            throw new RuntimeException(e);
        }
    }
}
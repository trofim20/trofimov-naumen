package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace {

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");
    private static final int limit = 10;

    //Общая сложность алгоритма O(nlogn)

    public static void main(String[] args) {
        // использую LinkedHashMap, чтобы сохранять порядок вставки и удобный доступ к элементам. Вставка и доступ выполняются за O(1)
        Map<String, Integer> wordCount = new LinkedHashMap<>();


        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> wordCount.put(word, wordCount.getOrDefault(word, 0) + 1));

        List<Map.Entry<String, Integer>> topWords = getlastWords(wordCount, Map.Entry.comparingByValue());
        List<Map.Entry<String, Integer>> lastWords = getlastWords(wordCount, Map.Entry.<String, Integer>comparingByValue().reversed());

        // вывод
        printWords(topWords, "TOP 10 наиболее используемыех слов");
        printWords(lastWords, "LAST 10 наименее используемыех слов");
    }

    private static List<Map.Entry<String, Integer>> getlastWords(Map<String, Integer> words, Comparator<Map.Entry<String, Integer>> comparator) {
        // использую PriorityQueue для быстрого отбора 10 нужных слов. Добавление O(log(n)), удаление и получение O(1)
        PriorityQueue<Map.Entry<String, Integer>> tenWords = new PriorityQueue<>(limit + 1, comparator);


        for (Map.Entry<String, Integer> entry : words.entrySet()) {//O(n)
            if (tenWords.size() == limit && comparator.compare(tenWords.peek(), entry) > 0) {
                continue; // пропускаем, если элемент не подходит
            }
            tenWords.add(entry); // добавляем элемент O(log(n))
            if (tenWords.size() > limit) {
                tenWords.poll(); // Удаляем наименьший элемент, если размер очереди превышает лимит O(1)
            }
        }

        return reverseQueue(tenWords); // Возвращаем результат в виде списка.
    }

    private static List<Map.Entry<String, Integer>> reverseQueue(Queue<Map.Entry<String, Integer>> queue) {
        // Создаем список для результата и добавляем туда элементы из очереди.
        List<Map.Entry<String, Integer>> res = new ArrayList<>(limit);
        while (!queue.isEmpty()) {//O(10)
            res.add(queue.poll());//O(1)
        }

        Collections.reverse(res);//O(10)
        return res;
    }

    private static void printWords(List<Map.Entry<String, Integer>> words, String message) {
        System.out.println("\n" + message);
        for (Map.Entry<String, Integer> entry : words) {
            System.out.println(entry.getKey() + " - " + entry.getValue()+ " раз(а)");
        }

    }
}

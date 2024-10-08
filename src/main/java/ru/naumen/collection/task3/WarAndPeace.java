package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

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
public class WarAndPeace
{

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void main(String[] args) {
        Map<String, Integer> wordCount = new HashMap<>();

        // Используем WordParser для обработки слов
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);//добавляем слово и обнавляем его количество
                });

        System.out.println("TOP 10 наиболее используемыех слов");
        wordCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())//сортируем количество появлений слов в порядке убывания
                .limit(10)// до первых 10 слов
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));//выводим значения

        System.out.println("\nLAST 10 наименее используемыех слов");
        wordCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())//сортируем количество появлений слов в порядке возрастания
                .limit(10)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}

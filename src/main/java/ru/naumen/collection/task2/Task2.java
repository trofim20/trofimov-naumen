package ru.naumen.collection.task2;

import java.util.*;

/**
 * Дано:
 * <pre>
 * public class User {
 *     private String username;
 *     private String email;
 *     private byte[] passwordHash;
 *     …
 * }
 * </pre>
 * Нужно реализовать метод
 * <pre>
 * public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB);
 * </pre>
 * <p>который возвращает дубликаты пользователей, которые есть в обеих коллекциях.</p>
 * <p>Одинаковыми считаем пользователей, у которых совпадают все 3 поля: username,
 * email, passwordHash. Дубликаты внутри коллекций collA, collB можно не учитывать.</p>
 * <p>Метод должен быть оптимален по производительности.</p>
 * <p>Пользоваться можно только стандартными классами Java SE.
 * Коллекции collA, collB изменять запрещено.</p>
 *
 * См. {@link User}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task2
{

    /**
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     */
    //Общая сложность O(n)
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        //используется, потому что оно обеспечивает быстрый доступ для проверки наличия элементов
        //преобразование в HashSet произходит за O(n)
        Set<User> setA = new HashSet<>(collA);
        List<User> duplicates = new ArrayList<>();//сохраняем дубликаты, используем так как вывод List

        for (User user : collB) {
            if (setA.contains(user)) {//Сложность O(1) проверяем, если пользователь из collB присутствует в setA
                duplicates.add(user);//Сложность O(1)
            }
        }

        return duplicates;
    }
}

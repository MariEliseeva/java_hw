package spbau.eliseeva.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SecondPartTasks {

    private SecondPartTasks() {}

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {
        return paths.stream().flatMap(s -> {
            try {
                return Files.lines(Paths.get(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(s->s.contains(sequence)).collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        Random random = new Random();
        int result = Stream.iterate(0, integer -> {
            double newX = random.nextDouble() - 0.5;
            double newY = random.nextDouble() - 0.5;
            if (newX * newX + newY * newY <= 0.25) {
                return integer + 1;
            } else {
                return integer;
            }
        }).limit(100000).skip(99999).findFirst().orElse(0);
        return ((double) result) / 100000;
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        return compositions.entrySet().stream().max(Comparator.comparingInt(e->e.getValue().stream()
                .collect(Collectors.joining()).length())).get().getKey();
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders.stream().flatMap(s->s.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)));
    }
}
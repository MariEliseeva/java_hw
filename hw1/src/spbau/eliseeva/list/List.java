package spbau.eliseeva.list;

/** Список.
 * Состоит из элементов - Node, каждый из которых хранит данные
 * (ключ и значение), ссылку на следующий элемент и на предыдущий.
 * Сам список - это указатели на начало и на конец.
 * Используется внутри хеш-таблицы.
 */
public class List {
    /** Внутренний класс, представляющий собой один элемент списка. */
    private class Node {
        private Node next = this;
        private Node previous = this;
        private String key;
        private String value;

        /**
         * Конструктор элемента по ключу и значению.
         * @param newKey ключ.
         * @param newValue значение.
         */
        private Node(String newKey, String newValue) {
            key = newKey;
            value = newValue;
        }

        /** Конструктор без параметров. */
        private Node() {
        }
    }

    /**
     * Поле "голова".
     * Является указателем на начало списка, значения внутри себя не хранит.
     */
    private Node head;

    /**
     * Поле "хвост".
     * Является указателем на конец списка, значения внутри себя не хранит.
     */
    private Node tail;

    /**
     * Конструктор.
     * В пустом списке есть только голова и хвост, которые ссылаются друг на друга.
     */
    public List() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.previous = head;
    }

    /** Добавление элемента в конец списка. */
    public void pushBack(String newKey, String newValue) {
        Node node = new Node(newKey, newValue);
        node.previous = tail.previous;
        node.next = tail;
        node.previous.next = node;
        node.next.previous = node;
    }

    /** Удаляет первый элемент списка. */
    public void removeFront() {
        head.next.next.previous = head;
        head.next = head.next.next;
    }

    /**
     * @return ключ первого элемента списка.
     */
    public String getFrontKey() {
       return head.next.key;
    }

    /**
     * @return значение первого элемента списка.
     */
    public String getFrontValue() {
        return head.next.value;
    }

    /**
     * Ищет элемент списка по ключу.
     * @param findKey ключ элемента, который требуется найти.
     * @return Значение найденного элемента или null если элемента нет.
     */
    public String find(String findKey) {
        for (Node node = head.next; node != tail; node = node.next) {
            if (node.key.equals(findKey)) {
                return node.value;
            }
        }
        return null;
    }

    /**
     * Удаление элемента из списка.
     * @param key ключ элемента, который нужно удалить.
     */
    public void remove(String key) {
        for (Node node = head.next; node != tail; node = node.next) {
            if (node.key.equals(key)) {
                node.next.previous = node.previous;
                node.previous.next = node.next;
            }
        }
    }

    /** Удаляет все элементы списка. */
    public void clear() {
        head.next = tail;
        tail.previous = head;
    }
}

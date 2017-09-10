package spbau.eliseeva.list;

/** Список.
 * Используется внутри хеш-таблицы.
 * @autor Елисеева Мария
 */

public class List {
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

    /**
     * Добавление элемента в конец списка.
     */
    public void pushBack(String newKey, String newValue) {
        Node node = new Node(newKey, newValue);
        node.previous= tail.previous;
        node.next = tail;
        node.previous.next = node;
        node.next.previous = node;
    }

    /**
     * Удаляет первый элемент списка.
     * @return удалённый элемент или null в случае пустого списка.
     */
    public Node getFront() {
        Node result = head.next;
        if (result == tail) return null;
        head.next.next.previous = head;
        head.next = head.next.next;
        return result;
    }

    /**
     * Ищет элемент списка по ключу.
     * @param findKey ключ элемента, который требуется.
     * @return найденный элемент или null при его отсутствии.
     */
    public Node find(String findKey) {
        for(Node node = head.next; node != tail; node = node.next) {
            if (node.key == findKey) return node;
        }
        return null;
    }

    /**
     * Удаление элемента из списка.
     * @param node элемент, который нужно удалить.
     */
    public void delete(Node node) {
        node.previous.next = node.next;
        node.next.previous = node.previous;
    }

    /**
     * Удаляет все элементы списка.
     */
    public void clear() {
        head.next = tail;
        tail.previous = head;
    }
}

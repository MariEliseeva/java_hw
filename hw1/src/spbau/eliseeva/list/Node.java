package spbau.eliseeva.list;

/** Один элемент списка.
 * @autor Елисеева Мария
 */

public class Node {
    /**
     * Ссылка на следующий элемент.
     */
    public Node next;

    /**
     * Ссылка на предыдущий элемент
     */
    public Node previous;

    /**
     * Ключ.
     */
    public String key;

    /**
     * Значение.
     */
    public String value;

    /**
     * Конструктор элемента по ключу и значению.
     * @param newKey ключ.
     * @param newValue значение.
     */
    public Node(String newKey, String newValue) {
        key = newKey;
        value = newValue;
        next = this;
        previous = this;
    }

    /**
     * Конструктор без параметров.
     */
    public Node() {
        next = this;
        previous = this;
    }
}
package spbau.eliseeva.hashmap;

import spbau.eliseeva.list.List;
import spbau.eliseeva.list.Node;

/**
 * Хеш-таблица.
 * @author Елисеева Мария
 */
public class HashMap {
    /**
     * Массив из списков, тут хранятся данные.
     */
    private List [] data;

    /**
     * Количество элементов в хеш-таблице.
     */
    private int _size;

    /**
     * Количество списков внутри хеш-таблицы.
     */
    private int _capacity;

    /**
     * Количество элементов в хеш-таблице.
     * @return поле _size
     */
    public int size() {
        return _size;
    }

    /**
     * Конструктор.
     */
    public HashMap() {
        _size = 0;
        _capacity = 1;
        data = new List[1];
        data[0] = new List();
    }

    /**
     * Удваивает количество списков.
     * (Вызвается когда элементов становится больше, чем списков).
     */
    private void rebuild() {
        List [] newData = new List[_capacity * 2];
        for (int i = 0; i < _capacity * 2; i++) {
            newData[i] = new List();
        }
        Node node;
        for(int i = 0; i < _capacity; i++) {
            node = data[i].getFront();
            while(node != null) {
                newData[node.key.hashCode() % (_capacity * 2)].pushBack(node.key, node.value);
            }
        }
        _capacity *= 2;
    }

    /**
     * Проверяет наличие элемента в хеш-таблице.
     * @param key ключ проверяемого элемента.
     * @return true если есть ли элемент с таким ключом, false иначе.
     */
    public boolean contains(String key) {
        for(int i = 0; i < _capacity; i++) {
            if (data[i].find(key) != null) return true;
        }
        return false;
    }

    /**
     * Возвращает значение по ключу.
     * @param key ключ.
     * @return значение.
     */
    public String get(String key) {
        Node node = new Node();
        for(int i = 0; i < _capacity; i++) {
            node = data[i].find(key);
            if (node != null) return node.value;
        }
        return null;
    }

    /**
     * Удаляет элемент по ключу.
     * @param key ключ.
     * @return Значение удалённого элемента или null если элемента не было.
     */
    public String remove(String key) {
        Node node = new Node();
        for(int i = 0; i < _capacity; i++) {
            node = data[i].find(key);
            if (node != null) {
                String result = node.value;
                data[i].delete(node);
                _size--;
                return result;
            }
        }
        return null;
    }

    /**
     * Добавляет элемент в список или заменяет значение по ключу.
     * @param key ключ.
     * @param value значение.
     * @return предыдущее значение элемента с таким ключом или null если элемента не было.
     */
    public String put(String key, String value) {
        String oldValue = null;//remove(key);
        if (oldValue == null) _size++;
        data[key.hashCode() % _capacity].pushBack(key, value);
        //if (_size > _capacity) rebuild();
        return oldValue;
    }

    /**
     * Удаляяет все элементы из хеш-таблицы.
     */
    public void clear() {
        for(int i = 0; i < _capacity; i++) {
            data[i].clear();
        }
        _size = 0;
    }
}

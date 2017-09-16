package spbau.eliseeva.hashmap;

import spbau.eliseeva.list.List;

/**
 * Хеш-таблица на списках.
 * Внутри таблицы хранится массив списков. Для ключа каждого элемента при
 * помощи хеш-функции вычисляется номер списка, в котором элемент хранится.
 * При совпадении индексов элементы просто хранятся в одном списке и ищутся
 * проходом по списку за его длину. Это может оказаться неэффективным по
 * времени, поэтому в момент, когда количество элементов и количество
 * списков становятся равными, списков становится в два раза больше:
 * перевыделяется память и все элементы копируются из старой памяти в новую.
 */
public class HashMap {
    /** Массив из списков, тут хранятся данные. */
    private List [] data;

    /** Количество элементов в хеш-таблице. */
    private int size = 0;

    /** Количество списков внутри хеш-таблицы. */
    private int capacity = 17;

    /** Вычисляет хеш для ключа. Используется функция hashCode(),
     *  а также остаток от деления на количесво списков.
     */
    private int hash(String key) {
        int hash = key.hashCode() % capacity;
        if (hash < 0) hash += capacity;
        return hash;
    }

    /**
     * Количество элементов в хеш-таблице.
     * @return поле size
     */
    public int size() {
        return size;
    }

    /** Конструктор. Инициализирует массив пустыми списками. */
    public HashMap() {
        data = new List[capacity];
        for (int i = 0; i < capacity; i++) {
            data[i] = new List();
        }
    }

    /**
     * Удваивает количество списков - все элементы перемещаются
     * в новый массив большего размера.
     * Вызвается когда элементов становится больше, чем списков.
     */
    private void rebuild() {
        capacity *= 2;
        List [] newData = new List[capacity];
        for (int i = 0; i < capacity; i++) {
            newData[i] = new List();
        }
        String key, value;
        for (int i = 0; i < capacity / 2; i++) {
            key = data[i].getFrontKey();
            value = data[i].getFrontValue();
            while (key != null) {
                newData[hash(key)].pushBack(key, value);
                data[i].removeFront();
            }
        }
        data = newData;
    }

    /**
     * Проверяет наличие элемента в хеш-таблице.
     * @param key ключ проверяемого элемента.
     * @return true если есть ли элемент с таким ключом, false иначе.
     */
    public boolean contains(String key) {
        if (data[hash(key)].find(key) != null) {
            return true;
        }
        return false;
    }

    /**
     * Возвращает значение по ключу.
     * @param key ключ.
     * @return значение.
     */
    public String get(String key) {
        return data[hash(key)].find(key);
    }

    /**
     * Удаляет элемент по ключу.
     * @param key ключ.
     * @return Значение удалённого элемента или null если элемента не было.
     */
    public String remove(String key) {
        String oldValue = data[hash(key)].find(key);
        data[hash(key)].remove(key);
        if (oldValue != null) {
            size--;
        }
        return oldValue;
    }

    /**
     * Добавляет элемент в список или заменяет значение по ключу.
     * @param key ключ.
     * @param value значение.
     * @return предыдущее значение элемента с таким ключом или null если элемента не было.
     */
    public String put(String key, String value) {
        String oldValue = remove(key);
        size++;
        data[hash(key)].pushBack(key, value);
        if (size > capacity) {
            rebuild();
        }
        return oldValue;
    }

    /** Удаляяет все элементы из хеш-таблицы. */
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            data[i].clear();
        }
        size = 0;
    }
}

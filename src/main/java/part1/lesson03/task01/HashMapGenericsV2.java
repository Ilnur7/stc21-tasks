package part1.lesson03.task01;

import java.util.*;

public class HashMapGenericsV2<K, V> implements Map<K, V> {
    private Node<K, V>[] hashTable;
    private int size = 0;
    private float threshold;

    public HashMapGenericsV2(){
        hashTable = new Node[16];
        threshold = hashTable.length * 0.75f;
    }

    /**
     * Метод получения индекса в массиве hashtable.
     */
    private int hash(K key) {
        int hash = 31;
        hash = hash * 17 + key.hashCode();
        return hash % hashTable.length;
    }

    /**
     * Метод добавления пары ключ-значение
     */
    @Override
    public V put(K key, V value) {
        if (key == null) throw new NullPointerException("Нельзя положить элемент по ключу null");
        if ((size + 1) >= threshold){
            threshold *= 2;
            resize();
        }

        int index = hash(key);
        Node<K, V> newNode = new Node<K, V>(key, value);
        if (hashTable[index] == null){
            hashTable[index] = newNode;
            size++;
            return newNode.getValue();
        }else {
            while (hashTable[index] != null){
                if (hashTable[index].getKey().equals(newNode.getKey())){
                    hashTable[index] = newNode;
                    return newNode.getValue();
                }
                ++index;
                index %= hashTable.length;
            }
            hashTable[index] = newNode;
            size++;
            return newNode.getValue();
        }
    }

    /**
     * Метод удаления элемента по ключу
     */
    @Override
    public V remove(Object key) {
        int index = hash((K) key);
        int counter = 0;
        while(!hashTable[index].getKey().equals(key)) {
            ++index;
            index %= hashTable.length;
            if (counter++ == hashTable.length) throw new NullPointerException("Нет корзины по такому ключу");
        }
        Node<K, V> node = hashTable[index];
        hashTable[index] = null;
        size--;
        return node.getValue();
    }

    /**
     * Метод получения элемента по ключу
     */
    @Override
    public V get(Object key) {
        int index = hash((K) key);
        while(hashTable[index] != null){ // Пока не будет найдена пустая ячейка
            if (hashTable[index].getKey() == key) return hashTable[index].getValue();
            ++index;
            index %= hashTable.length; // При достижении конца таблицы происходит возврат к началу
        }
        return null;
    }

    /**
     * Метод добавления всех элементов одной мапы в текущую
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            put(key, value);
        }
    }

    /**
     * Метод очистки всех элементов в мапе
     */
    @Override
    public void clear() {
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = null;
        }
        size=0;
    }

    /**
     * Метод получения множества ключей
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] == null) continue;
            set.add(hashTable[i].getKey());
        }
        return set;
    }

    /**
     * Метод получения списка значений
     */
    @Override
    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] == null) continue;
            list.add(hashTable[i].getValue());
        }
        return list;
    }

    /**
     * Метод получения множества пар ключ-значение
     */
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] == null) continue;
            Map.Entry<K,V> entry = new Entry<>(hashTable[i].getKey(), hashTable[i].getValue());
            set.add(entry);
        }
        return set;
    }

    /**
     * Метод получения размера мапы
     */
    public int size() {
        return size;
    }

    /**
     * Метод проверки, что мапа является пустой
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Метод проверки содержания данного ключа в мапе
     */
    @Override
    public boolean containsKey(Object key) {
        return keySet().contains(key);
    }

    /**
     * Метод проверки содержания данного значения в мапе
     */
    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    /**
     * Метод увеличения мапы при заполнении на 3/4
     */
    private void resize() {
        Node<K,V>[] oldHashTable = hashTable;
        hashTable = new Node[oldHashTable.length * 2];
        size = 0;
        for (Node<K,V> node: oldHashTable){
            if (node != null){
                put(node.getKey(), node.getValue());
            }
        }
    }

    private class Entry<K,V> implements Map.Entry {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            this.value = (V) value;
            return value;
        }

    }

    private class Node<K, V> {

        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<?, ?> node = (Node<?, ?>) o;

            if (key != null ? !key.equals(node.key) : node.key != null) return false;
            return value != null ? value.equals(node.value) : node.value == null;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + (key != null ? key.hashCode() : 0);
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }

}

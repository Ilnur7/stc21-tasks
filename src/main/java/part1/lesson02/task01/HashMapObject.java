package part1.lesson02.task01;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashMapObject {
    private Node [] hashTable; //массив из бакетов
    private int size = 0;
    private float threshold;

    public HashMapObject(){
        hashTable = new Node[16];
        threshold = hashTable.length * 0.75f;
    }

    /**
     * Метод получения индекса в массиве hashtable.
     */
    private int hash(Object key) {
        int hash = 31;
        hash = hash * 17 + key.hashCode();
        return hash % hashTable.length;
    }

    /**
     * Метод добавления пары ключ-значение
     */
    public boolean put(Object key, Object value) throws ExceptionObject {
        if (key == null) throw new ExceptionObject("Нельзя положить элемент в мапу с ключом null ");
        if ((size + 1) >= threshold){
            threshold *= 2;
            arrayDoubling();
        }

        Node newNode = new Node(key, value);
        int index = hash(key);

        if (hashTable[index] == null) {
            return addNewNode(index, newNode);
        }

        List<Node> nodeList = hashTable[index].getNodes();

        //TODO разделить в другие методы
        for (Node node: nodeList) {
            if (newNode.getKey().equals(node.getKey()) && !newNode.getValue().equals(node.getValue())){
                node.setValue(value);
                return true;
            }
            if (node.hashCode() == newNode.hashCode() && !newNode.key.equals(node.key) && !newNode.value.equals(node.key)){
                nodeList.add(newNode);
                size++;
                return true;
            }
        }
        return false;
    }

    /**
     * Метод обновления данных,
     * при наличия в мапе ключа - изменять его значение,
     * а при отсутствии данного ключа - выбрасывать исключение
     */
    public boolean update(Object key, Object value) throws ExceptionObject {
        int index = hash(key);
        if (hashTable[index] == null){
            throw new ExceptionObject("Нет корзины по такому ключу");
        }
        List<Node> nodeList = hashTable[index].getNodes();

        for (Node node: nodeList){
            if (key.equals(node.getKey())){
                node.setValue(value);
                return true;
            }
        }
        return false;
    }

    /**
     * Метод удаления элемента по ключу
     */
    public boolean delete(Object key) throws ExceptionObject {
        int index = hash(key);
        if (hashTable[index] == null){
            throw new ExceptionObject("Невозможно удалить данный объект, нет корзины по такому ключу");
        }
        if (hashTable[index].getNodes().size() == 1){
            hashTable[index].getNodes().remove(0);
            size--;
            return true;
        }

        List<Node> nodeList = hashTable[index].getNodes();
        for (Node node: nodeList){
            if (key.equals(node.getKey())){
                nodeList.remove(node);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Метод получения элемента по ключу
     */
    public Object get(Object key) {
        if (key == null) return null;
        int index = hash(key);
        if (index > hashTable.length) throw new ArrayIndexOutOfBoundsException();
        if (hashTable[index] == null) throw new NullPointerException();
        List<Node> nodeList = hashTable[index].getNodes();
        for (Node node: nodeList){
            if (key.equals(node.getKey())){
                return node.getValue();
            }
        }
        return null;
    }

    /**
     * Метод получения количества элементов в hashmap
     */
    public int size() {
        return size;
    }

    /**
     * Метод увеличения размера массива в 2 раза при заполнения на 3/4
     */
    private void arrayDoubling() throws ExceptionObject {
        Node[] oldHashTable = hashTable;
        hashTable = new Node[oldHashTable.length * 2];
        size = 0;
        for (Node node: oldHashTable){
            if (node != null){
                for (Node n: node.getNodes()){
                    put(n.key, n.value);
                }
            }
        }
    }

    /**
     * Метод добавления нового элемента
     */
    private boolean addNewNode(int index, Node newNode) {
        hashTable[index] = new Node(null, null);
        hashTable[index].getNodes().add(newNode);
        size++;
        return true;
    }

    private class Node {
        private List<Node> nodes;
        private int hash;
        private Object key;
        private Object value;

        public Node(Object key, Object value) {
            this.key = key;
            this.value = value;
            nodes = new LinkedList<Node>();
        }

        public List<Node> getNodes() {
            return nodes;
        }

        public int hash() {
            return hashCode() % hashTable.length;
        }

        public Object getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (hash != node.hash) return false;
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

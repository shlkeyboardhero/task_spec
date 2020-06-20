package com.company;

import java.util.ArrayList;
import java.util.List;


public class HashMapSecondOwnImp {
    //static final int Step = 1;   //шаг по умолчанию
    int step;
    int bucketSize = 23; //изначально размер - простое число

    private Item[] buckets;

    /*public HashMapSecondOwnImp() { //конструктор по умолчанию
        this.step = Step;
        buckets = new Item[bucketSize];
    }*/

    public HashMapSecondOwnImp(int step) { // конструктор с параметром
        this.step = step;
        bucketSize = getCoprimeNumber(bucketSize, step);
        buckets = new Item[bucketSize];
    }


    public boolean coprimeNumberCheck(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        if (a == 1)
            return true;
        else
            return false;
    }

    public int getCoprimeNumber(int bucketSize, int k) {
        if (k >= bucketSize) {
            bucketSize = k + 1;
        }
        while (true) {
            if (!coprimeNumberCheck(bucketSize, k)) {
                bucketSize += k;
            } else
                return bucketSize;
        }
    }


    private int index(Item selectedItem, int i) { // i исходя из ((hash(x) + ik) mod N) @Wiki

        return index(selectedItem.key, i);
    }

    private int index(String key, int i) { // i исходя из ((hash(x) + ik) mod N) @Wiki

        return (key.hashCode() + i * step) % bucketSize;
    }

    public void put(String key, int value) {
        Item newItem = new Item(key, value);
        if (insertItem(newItem) >= bucketSize) {
            bucketSize += step;
            buckets = moveToNewBuckets();
            for (int j = 0; j < bucketSize; j++) {
                int newItemIndex = index(newItem, j);
                if (buckets[newItemIndex] == null || buckets[newItemIndex].deleted) {
                    buckets[newItemIndex] = newItem;
                    break;
                }
            }
        }

    }


    private Item[] moveToNewBuckets() {
        int currentIndex;
        Item newBuckets[] = new Item[bucketSize];
        for (int k = 0; k < buckets.length; k++) {
            if (buckets[k] != null) {
                for (int j = 0; j < bucketSize; j++) {
                    currentIndex = index(buckets[k], j);
                    if (newBuckets[currentIndex] == null) {
                        newBuckets[currentIndex] = buckets[k];
                        break;
                    }
                }
            }

        }
        return newBuckets;
    }

    private int insertItem(Item newItem) {
        int j;
        int currentIndex;
        for (j = 0; j < bucketSize; j++) {
            currentIndex = index(newItem, j);
            if (buckets[currentIndex] == null || buckets[currentIndex].deleted) {
                buckets[currentIndex] = newItem;
                break;
            } else {
                if (buckets[currentIndex].key.equals(newItem.key)) {
                    buckets[currentIndex] = newItem;
                    break;
                }
            }
        }
        return j;
    }

    public void del(String key) {
        int nextIndex;
        for (int i = 0; i < bucketSize; i++) { // i исходя из ((hash(x) + ik) mod N) @Wiki
            nextIndex = index(key, i);
            if (key.equals(buckets[nextIndex].key)) {
                buckets[nextIndex].deleted = true;
                break;
            }

        }
    }


    public Integer get(String key) {
        int currentIndex;
        for (int i = 0; i < bucketSize; i++) { // i исходя из ((hash(x) + ik) mod N) @Wiki
            currentIndex = index(key, i);
            if (!buckets[currentIndex].deleted && key.equals(buckets[currentIndex].key)) {
                return buckets[currentIndex].value;
            }
        }
        return null;
    }

    public int size() {
        return getKeys().size();
    }

    public List<String> getKeys() {

        ArrayList<String> keysValue = new ArrayList<String>();
        for (int j = 0; j < bucketSize; j++) {
            if (buckets[j] != null && !buckets[j].deleted) {
                keysValue.add(buckets[j].key);
            }
        }
        return keysValue;
    }

    public List<Integer> getValues() {

        ArrayList<Integer> valuesValue = new ArrayList<Integer>();
        for (int j = 0; j < bucketSize; j++) {
            if (buckets[j] != null && !buckets[j].deleted) {
                valuesValue.add(buckets[j].value);
            }
        }
        return valuesValue;
    }
}


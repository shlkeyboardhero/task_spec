package com.company;

import java.util.ArrayList;
import java.util.List;


public class HashMapOwnImp2 {
    static final int K = 1;   //шаг по умолчанию
    int k;
    int bucketSize = 23; //изначально размер - простое число

    private Item[] buckets = new Item[bucketSize];

    public HashMapOwnImp2() {
        this.k = K;
        buckets = new Item[bucketSize];
    }

    public HashMapOwnImp2(int k) {
        this.k = k;
        bucketSize = getInterdpaNumber(bucketSize, k);
        buckets = new Item[bucketSize];
    }


    public int interspacesNumberCheck(int bucketSize, int k) {
        while (k != 0) {
            int tmp = bucketSize % k;
            bucketSize = k;
            k = tmp;
        }
        return bucketSize;
    }

    public int getInterdpaNumber(int bucketSize, int k) {
        if (k >= bucketSize) {
            bucketSize = k + 1;
        }
        while (true) {
            if (interspacesNumberCheck(bucketSize, k) != 1) {
                bucketSize += k;
            } else
                return bucketSize;
        }
    }


    private int index(Item selectedItem, int i) { // i исходя из ((hash(x) + ik) mod N) @Wiki

        return index(selectedItem.key, i);
    }

    private int index(String key, int i) { // i исходя из ((hash(x) + ik) mod N) @Wiki

        return (key.hashCode() + i * k) % bucketSize;
    }

    public void put(String key, int value) {
        Item newItem = new Item(key, value);
        if (insertItem(newItem) >= bucketSize) {
            bucketSize += k;
            buckets = move2NewBuckets();
            for (int counter_1 = 0; counter_1 < bucketSize; counter_1++) {
                int newItemIndex = index(newItem, counter_1);
                if (buckets[newItemIndex] == null || buckets[newItemIndex].deleted) {
                    buckets[newItemIndex] = newItem;
                    break;
                }
            }
        }

    }


    private Item[] move2NewBuckets() {
        int currentIndex;
        Item newBuckets[] = new Item[bucketSize];
        for (int counter_2 = 0; counter_2 < buckets.length; counter_2++) {
            if (buckets[counter_2] != null) {
                for (int counter_1 = 0; counter_1 < bucketSize; counter_1++) {
                    currentIndex = index(buckets[counter_2], counter_1);
                    if (newBuckets[currentIndex] == null) {
                        newBuckets[currentIndex] = buckets[counter_2];
                        break;
                    }
                }
            }

        }
        return newBuckets;
    }

    private int insertItem(Item newItem) {
        int counter;
        int currentIndex;
        for (counter = 0; counter < bucketSize; counter++) {
            currentIndex = index(newItem, counter);
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
        return counter;
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
            if (key.equals(buckets[currentIndex].key)) {
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
        for (int counter = 0; counter < bucketSize; counter++) {
            if (buckets[counter] != null && !buckets[counter].deleted) {
                keysValue.add(buckets[counter].key);
            }
        }
        return keysValue;
    }

    public List<Integer> getValues() {

        ArrayList<Integer> valuesValue = new ArrayList<Integer>();
        for (int counter = 0; counter < bucketSize; counter++) {
            if (buckets[counter] != null && !buckets[counter].deleted) {
                valuesValue.add(buckets[counter].value);
            }
        }
        return valuesValue;
    }
}


package com.company;

import java.util.ArrayList;
import java.util.List;


public class HashMapOwnImp {
    static final int K = 1;   //шаг по умолчанию
    int k;
    int bucketSize = 23; //изначально размер - простое число

    private Item[] buckets = new Item[bucketSize];

    public HashMapOwnImp() {
        this.k = K;
        buckets = new Item[bucketSize];
    }

    public HashMapOwnImp(int k) {
        this.k = k;
        bucketSize = getInterdpaNumber(bucketSize, k);
        buckets = new Item[bucketSize];
    }


    public int interspacesNumberCheck(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    public int getInterdpaNumber(int a, int b) {
        if (b >= a) {
            a = b + 1;
        }
        while (true) {
            if (interspacesNumberCheck(a, b) != 1) {
                a += b;
            } else
                return a;
        }
    }


    private int index(Item selectedItem, int itr) {

        return index(selectedItem.key, itr);
    }

    private int index(String key, int itr) {

        return (key.hashCode() + itr * k) % bucketSize;
    }

    public void put(String key, int value) {
        Item newItem = new Item(key, value);
        if (insertItem(newItem) >= bucketSize) {
            bucketSize += k;
            buckets = move2NewBuckets();
            for (int r = 0; r < bucketSize; r++) {
                int newItemIndex = index(newItem, r);
                if (buckets[newItemIndex] == null) {
                    buckets[newItemIndex] = newItem;
                    break;
                }
            }
        }

    }

    private Item[] move2NewBuckets() {
        int currentIndex;
        Item newBuckets[] = new Item[bucketSize];
        for (int q = 0; q < buckets.length; q++) {
            if (buckets[q] != null) {
                for (int r = 0; r < bucketSize; r++) {
                    currentIndex = index(buckets[q], r);
                    if (newBuckets[currentIndex] == null) {
                        newBuckets[currentIndex] = buckets[q];
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
            if (buckets[currentIndex] == null) {
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
        for (int itr = 0; itr < bucketSize; itr++) {
            nextIndex = index(key, itr);
            if (key.equals(buckets[nextIndex].key)) {
                buckets[nextIndex] = shiftItem(itr + 1, key);
                break;
            }

        }
    }

    private Item shiftItem(int itr, String key) {
        Item tempItem;
        int nextIndex = index(key, itr);
        if (itr >= bucketSize || buckets[nextIndex] == null) {
            return null;
        } else if (buckets[nextIndex].key.hashCode() == key.hashCode()) {
            tempItem = buckets[nextIndex];
            buckets[nextIndex] = shiftItem(itr + 1, key);
            return tempItem;
        } else
            return shiftItem(itr + 1, key);
    }

    public Integer get(String key) {
        int currentIndex;
        for (int itr = 0; itr < bucketSize; itr++) {
            currentIndex = index(key, itr);
            if (key.equals(buckets[currentIndex].key)) {
                return buckets[currentIndex].value;
            }
        }
        return null;
    }

    public int size() {
        return bucketSize;
    }

    public List<String> getKeys() {

        ArrayList<String> keysValue = new ArrayList<String>();
        for (int counter = 0; counter < bucketSize; counter++) {
            if (buckets[counter] != null) {
                keysValue.add(buckets[counter].key);
            }
        }
        return keysValue;
    }

    public List<Integer> getValues() {

        ArrayList<Integer> valuesValue = new ArrayList<Integer>();
        for (int counter = 0; counter < bucketSize; counter++) {
            if (buckets[counter] != null) {
                valuesValue.add(buckets[counter].value);
            }
        }
        return valuesValue;
    }
}


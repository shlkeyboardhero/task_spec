package com.company;

import java.util.ArrayList;
import java.util.List;


public class HashMap {
    //static final int Step = 1;
    int probingStep;
    int bucketSize = 23;

    private Item[] buckets;

    /*public HashMapSecondOwnImp() {
        this.step = Step;
        buckets = new Item[bucketSize];
    }*/

    public HashMap(int probingStep) {
        this.probingStep = probingStep;
        bucketSize = resizeBuckets(bucketSize, probingStep);
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

    public int resizeBuckets(int bucketSize, int probingStep) {
        if (probingStep >= bucketSize) {
            bucketSize = probingStep + 1;
        }
        while (true) {
            if (!coprimeNumberCheck(bucketSize, probingStep)) {
                bucketSize += probingStep;
            } else
                return bucketSize;
        }
    }


    private int index(Item selectedItem, int tryPlaceItemToTable) {

        return index(selectedItem.key, tryPlaceItemToTable);
    }

    private int index(String key, int tryPlaceItemToTable) {

        return (key.hashCode() + tryPlaceItemToTable * probingStep) % bucketSize;
    }

    public void put(String key, int value) {
        Item newItem = new Item(key, value);
        if (insertItem(newItem) >= bucketSize) {
            bucketSize += probingStep;
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
        for (int tryPlaceItemToTable = 0; tryPlaceItemToTable < bucketSize; tryPlaceItemToTable++) {
            nextIndex = index(key, tryPlaceItemToTable);
            if (key.equals(buckets[nextIndex].key)) {
                buckets[nextIndex].deleted = true;
                break;
            }

        }
    }


    public Integer get(String key) {
        int currentIndex;
        for (int tryPlaceItemToTable = 0; tryPlaceItemToTable < bucketSize; tryPlaceItemToTable++) {
            currentIndex = index(key, tryPlaceItemToTable);
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


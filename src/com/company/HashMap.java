package com.company;

import java.util.ArrayList;
import java.util.List;


public class HashMap {

    int probingStep;
    int bucketSize = 23;

    private Item[] buckets;


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


    private int computeIndexViaItem(Item selectedItem, int attemptedPlacement) {

        return computeIndexViaKey(selectedItem.key, attemptedPlacement);
    }

    private int computeIndexViaKey(String key, int attemptedPlacement) {

        return (key.hashCode() + attemptedPlacement * probingStep) % bucketSize;
    }

    public void putNewItem(String key, int value) {
        Item newItem = new Item(key, value);
        if (insertItem(newItem) >= bucketSize) {
            bucketSize += probingStep;
            buckets = moveToNewBuckets();
            for (int j = 0; j < bucketSize; j++) {
                int newItemIndex = computeIndexViaItem(newItem, j);
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
        for (int j = 0; j < buckets.length; j++) {
            if (buckets[j] != null) {
                for (int k = 0; k < bucketSize; k++) {
                    currentIndex = computeIndexViaItem(buckets[j], k);
                    if (newBuckets[currentIndex] == null) {
                        newBuckets[currentIndex] = buckets[j];
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
            currentIndex = computeIndexViaItem(newItem, j);
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

    public void delItem(String key) {
        int nextIndex;
        for (int i = 0; i < bucketSize; i++) {
            nextIndex = computeIndexViaKey(key, i);
            if (key.equals(buckets[nextIndex].key)) {
                buckets[nextIndex].deleted = true;
                break;
            }

        }
    }


    public Integer getValue(String key) {
        int currentIndex;
        for (int i = 0; i < bucketSize; i++) {
            currentIndex = computeIndexViaKey(key, i);
            if (!buckets[currentIndex].deleted && key.equals(buckets[currentIndex].key)) {
                return buckets[currentIndex].value;
            }
        }
        return null;
    }

    public int getSize() {
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


package com.company;

import java.util.ArrayList;
import java.util.List;

public class MyHashMap {
    int k=3;
    int N=7;
    private Item[] buckets = new Item[N];
    public MyHashMap(int k){
        this.k=k;
    }
    public  MyHashMap(){
        this.k=1;
    }




    private int index(Item selectedItem,int o){
        return index(selectedItem.key,o);
    }

    private int index(String key,int o){
        return (key.hashCode()+o*k)%N;
    }

    public void put(String key, int value){
        int i;
        Item newItem = new Item(key,value);
            for (i = 0; i < N; i++) {
                int p=index(newItem,i);
                if (buckets[p] == null) {
                    buckets[p] = newItem;
                    break;
                } else {
                    if (buckets[p].key.equals(key)) {
                        buckets[p] = newItem;
                        break;
                    }
                }
            }
            if(i>=N){
                N+=k;
                int r;
                int q;
                Item trashBuckets[] = new Item[N];
                for (q=0;q<buckets.length;q++) {
                    if(buckets[q] != null){
                        for (r = 0; r < N; r ++) {
                            int m=index(buckets[q],r);
                            if (trashBuckets[m] == null) {
                                trashBuckets[m] = buckets[q];
                                break;
                            }
                        }
                    }

                }
                buckets=trashBuckets;
                for (r = 0; r < N; r ++) {
                    int m=index(newItem,r);
                    if (buckets[m] == null) {
                        buckets[m] = newItem;
                        break;
                    }
                }
        }

    }

    public void del(String key){
        int trashIndex;
        int w;
        for (w=0; w<N; w++){
            trashIndex=index(key,w);
            if (key.equals(buckets[trashIndex].key)) {
                buckets[trashIndex] = null;
                break;
            }
        }
    }

    public Integer get(String key) {
        int trashIndex;
        int w;
        for (w=0; w<N; w++){
            trashIndex=index(key,w);
            if (key.equals(buckets[trashIndex].key)) {
               return buckets[trashIndex].value;
            }
        }
        return null;
    }

    public int size() {
        return N;
    }

    public List<String> getKeys(){
        int t;

        ArrayList<String> keysValue = new ArrayList<String>();
        for (t=0;t<N;t++){
            if(buckets[t] != null){
                keysValue.add(buckets[t].key);
            }
        }
        return keysValue;
    }

    public List<Integer> getValues(){
        int t;

        ArrayList<Integer> valuesValue = new ArrayList<Integer>();
        for (t=0;t<N;t++){
            if(buckets[t] != null){
                valuesValue.add(buckets[t].value);
            }
        }
        return valuesValue;
    }
}

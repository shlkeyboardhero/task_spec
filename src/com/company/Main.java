package com.company;

public class Main {

    public static void main(String[] args) {
        HashMapOwnImp2 test = new HashMapOwnImp2(3);
        test.put("one", 1);
        test.put("two",2);
        test.put("tree",3);
        test.put("tree",4);
        test.put("five", 5);
        test.put("six",6);
        test.put("seven",7);
        test.put("eght",8);
        test.put("nine", 9);
        test.put("ten",10);
        System.out.println(test.getKeys());
        System.out.println(test.getValues());
        System.out.println(test.get("tree"));
        System.out.println(test.size());
        test.del("tree");
        System.out.println(test.getKeys());
        System.out.println(test.getValues());
        System.out.println(test.size());
    }
}

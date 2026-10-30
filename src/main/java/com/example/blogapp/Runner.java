package com.example.blogapp;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Runner {

    public static void main(String[] args) {

        BiConsumer<Integer, Integer> consumer = (Integer a, Integer b) -> System.out.println(a+b);
        consumer.accept(1,2);

        Test testInterface = () -> System.out.println("this is functional interface");
        testInterface.test();

        System.out.println(Singleton.getInstance().hashCode());
        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(20);
        list.add(30);

        Stream<Integer> value = list.stream();
        //filter
        value.filter(num -> num % 2 == 0)
                .forEach(System.out::println);

        System.out.println("Mapping value");
        Set<Integer> set = new HashSet<>();
        set.add(1);

        //map
//        list.stream().map(num -> num * num)
//                .forEach(System.out::println);
////        for(Map.Entry<Integer, Boolean> value: new HashMap<Integer, Boolean>().entrySet()){
////        }
//
//        Deque<Integer> queue = new ArrayDeque<>();
//        queue.removeFirst();

        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);

//        int[] tempArray = list1.toArray(new int[0]);

        list1.parallelStream().forEach(System.out::println);
        int [] array1 = {1,2,3,4,5};
        List<Integer> list3 = Arrays.asList(ArrayUtils.toObject(array1));

        Integer [] array =  list1.toArray(new Integer[0]);

        List<String> names = Arrays.asList("John", "Jane", "Jack", "Doe");

        // Collect elements into a single string

        String result = String.join(", ", names);
        System.out.println("Names: " + result);
        Set<Integer> set = new TreeSet<>();

    }
}
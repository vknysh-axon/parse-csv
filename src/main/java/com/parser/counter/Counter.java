package com.parser.counter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Counter.
 */
public class Counter {

    private static Counter counter;
    private static Map<String, Integer> userCount = new ConcurrentHashMap<>(300000);
    private static Map<String, String> users = new ConcurrentHashMap<>(300000);
    private static Map<String, Integer> productsCount = new ConcurrentHashMap<>(100000);
    private static Map<String, Integer> wordsCount = new ConcurrentHashMap<>(600000);

    private Counter() {}

    static {
        counter = new Counter();
    }

    public static Counter getInstance() {
        return counter;
    }

    /* Setters */

    public void addUser(String userId, String userName) {
        userCount.merge(userId, 1, Integer::sum);
        users.putIfAbsent(userId, userName);
    }

    public void addProduct(String productId) {
        productsCount.merge(productId, 1, Integer::sum);
    }

    public void addWords(Map<String, Integer> words) {
        words.forEach((k, v) -> wordsCount.merge(k, v, Integer::sum));
    }

    /* Getters */

    public List<String> getUsers() {
        List<String> names = new ArrayList<>();
        sortByValue(userCount, 1000).keySet().forEach(profileId -> names.add(users.get(profileId)));
        Collections.sort(names);
        return names;
    }

    public List<String> getProducts() {
        List<String> products = new ArrayList<>();
        sortByValue(productsCount, 1000).keySet().forEach(products::add);
        Collections.sort(products);
        return products;
    }

    public Map<String, Integer> getWords() {
        return sortByValue(wordsCount, 1000);
    }

    /* Aux */

    public void cleanCounters() {
        userCount = new ConcurrentHashMap<>();
        users = new ConcurrentHashMap<>();
        productsCount = new ConcurrentHashMap<>();
        wordsCount = new ConcurrentHashMap<>();
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, int limit) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}

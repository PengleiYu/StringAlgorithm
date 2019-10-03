package com.company.ch5_2_单词查找树;

/**
 * 以字符串为键的符号表API
 */
public interface StringST<Value> {

    void put(String key, Value val);

    Value get(String key);

    void delete(String key);

    boolean contains(String key);

    boolean isEmpty();

    String longestPrefixOf(String s);

    Iterable<String> keysWithPrefix(String s);

    Iterable<String> keysThatMatch(String s);

    int size();

    Iterable<String> keys();
}

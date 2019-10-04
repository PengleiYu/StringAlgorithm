package com.company.ch5_2_单词查找树;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ch5_2_3_三向单词查找树 {
    public static void main(String[] args) {
        String[] inputs = {
                "she",
                "sells",
                "sea",
//                "see",
                "shells",
                "by",
                "the",
                "sea",
                "shore",
                "surely",
        };
        TST<Integer> tst = new TST<>();
        //init
        Arrays.stream(inputs).forEach((s) -> tst.put(s, s.hashCode()));
        //size，isEmpty
        System.out.println(String.format("size = %d, isEmpty: %s",
                tst.size(), tst.isEmpty()));
        //contains
        String key = "shells";
        System.out.println(String.format("contains %s: %s", key, tst.contains(key)));
        //keyWithPrefix
        Iterable<String> strings1 = tst.keysWithPrefix("");
        System.out.println(String.join(",", strings1));
        //keyWithPrefix
        Iterable<String> strings2 = tst.keysWithPrefix("sh");
        System.out.println(String.join(",", strings2));
        //longestPrefix
        String join3 = Stream.of("she", "shell", "shellsort", "shelters")
                .map(s -> s + " -> " + tst.longestPrefixOf(s))
                .collect(Collectors.joining(","));
        System.out.println(join3);
        //keysThatMatch
        Iterable<String> keysThatMatch = tst.keysThatMatch("s..");
        System.out.println("KeysThatMatch:");
        System.out.println(String.join(",", keysThatMatch));
        // delete
        String keys1 = String.join(",", tst.keys());
        tst.delete("shells");
        String keys2 = String.join(",", tst.keys());
        System.out.println(keys1 + " -> " + keys2);
    }

    /**
     * 三向单词查找树
     * <p>
     * 与单词查找树的不同：
     * 单词查找树root为空节点
     * 三向单词查找树root非空
     */
    private static class TST<Value> implements StringST<Value> {
        private Node root;

        @Override
        public void put(String key, Value val) {
            root = put(root, key, val, 0);
        }

        private Node put(Node x, String key, Value val, int d) {
            char c = key.charAt(d);
            if (x == null) {
                x = new Node();
                x.c = c;
            }
            if (c < x.c) x.left = put(x.left, key, val, d);
            else if (c > x.c) x.right = put(x.right, key, val, d);
            else if (d < key.length() - 1) x.mid = put(x.mid, key, val, d + 1);
            else x.val = val;
            return x;
        }

        @Override
        public Value get(String key) {
            Node node = get(root, key, 0);
            if (node == null) return null;
            return node.val;
        }

        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            if (0 == key.length()) return null;
            if (d == key.length()) return x;


            char c = key.charAt(d);
            if (c < x.c) return get(x.left, key, d);
            else if (c > x.c) return get(x.right, key, d);
            else if (d < key.length() - 1) return get(x.mid, key, d + 1);
            else return x;
        }

        @Override
        public void delete(String key) {
            root = delete(root, key, 0);
        }

        private Node delete(Node x, String key, int d) {
            if (x == null) return null;
            char c = key.charAt(d);
            if (c < x.c) x.left = delete(x.left, key, d);
            else if (c > x.c) x.right = delete(x.right, key, d);
            else if (d < key.length() - 1) x.mid = delete(x.mid, key, d + 1);
            else x.val = null;

            if (x.val == null && x.left == null && x.mid == null && x.right == null)
                return null;
            else return x;
        }

        @Override
        public boolean contains(String key) {
            return contains(root, key, 0);
        }

        private boolean contains(Node x, String key, int d) {
            if (x == null) return false;
            if (d == key.length() - 1) {
                return x.val != null;
            }
            return contains(x.left, key, d) ||
                    contains(x.mid, key, d + 1) ||
                    contains(x.right, key, d);
        }

        @Override
        public boolean isEmpty() {
            return root == null;
        }

        @Override
        public String longestPrefixOf(String s) {
            int length = longestKey(root, s, 0, 0);
            return s.substring(0, length);
        }

        private int longestKey(Node x, String key, int d, int length) {
            if (x == null) return length;
            if (d == key.length()) return length;
            if (x.val != null) length = d + 1;
            char c = key.charAt(d);

            if (c < x.c)
                return longestKey(x.left, key, d, length);
            else if (c == x.c)
                return longestKey(x.mid, key, d + 1, length);
            else
                return longestKey(x.right, key, d, length);
        }

        @Override
        public Iterable<String> keysWithPrefix(String s) {
            Queue<String> queue = new LinkedList<>();
            Node node = get(root, s, 0);
            collect(node == null ? root : node.mid, s, queue);
            return queue;
        }

        @Override
        public Iterable<String> keysThatMatch(String s) {
            Queue<String> queue = new LinkedList<>();
            collectWithPattern(root, "", s, queue);
            return queue;
        }

        private void collectWithPattern(Node x, String pre, String pat, Queue<String> q) {
            if (x == null) return;
            int d = pre.length();
            if (d == pat.length()) return;//说明之前已经匹配完了

            char next = pat.charAt(d);

            if ('.' == next || next == x.c) {//当前字符匹配上了
                if (x.val != null && d == pat.length() - 1) {//匹配到模式的最后一位，且该节点为键
                    q.add(pre + x.c);
                } else collectWithPattern(x.mid, pre + x.c, pat, q);
            }
            if ('.' == next || next < x.c)
                collectWithPattern(x.left, pre, pat, q);
            if ('.' == next || next > x.c)
                collectWithPattern(x.right, pre, pat, q);
        }

        @Override
        public int size() {
            return size(root);
        }

        private int size(Node x) {
            if (x == null) return 0;
            int cnt = 0;
            if (x.val != null) cnt++;

            cnt += size(x.left);
            cnt += size(x.mid);
            cnt += size(x.right);
            return cnt;
        }

        @Override
        public Iterable<String> keys() {
            Queue<String> queue = new LinkedList<>();
            collect(root, "", queue);
            return queue;
        }

        private void collect(Node x, String pre, Queue<String> q) {
            if (x == null) return;
            if (x.val != null) q.add(pre + x.c);

            collect(x.left, pre, q);
            collect(x.mid, pre + x.c, q);
            collect(x.right, pre, q);
        }

        private class Node {
            char c;
            Node left, mid, right;
            Value val;
        }
    }
}

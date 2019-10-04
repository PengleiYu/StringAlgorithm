package com.company.ch5_2_单词查找树;

public class ch5_2_3_三向单词查找树 {
    public static void main(String[] args) {

    }

    /**
     * 三向单词查找树
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
            if (c < x.c) x.left = put(x, key, val, d);
            else if (c > x.c) x.right = put(x, key, val, d);
            else if (d < key.length() - 1) x.mid = put(x, key, val, d + 1);
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

            char c = key.charAt(d);
            if (c < x.c) return get(x.left, key, d);
            else if (c > x.c) return get(x.right, key, d);
            else if (d < key.length() - 1) return get(x.mid, key, d + 1);
            else return x;
        }

        @Override
        public void delete(String key) {

        }

        @Override
        public boolean contains(String key) {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public String longestPrefixOf(String s) {
            return null;
        }

        @Override
        public Iterable<String> keysWithPrefix(String s) {
            return null;
        }

        @Override
        public Iterable<String> keysThatMatch(String s) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Iterable<String> keys() {
            return null;
        }

        private class Node {
            char c;
            Node left, mid, right;
            Value val;
        }
    }
}

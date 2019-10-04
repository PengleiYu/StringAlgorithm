package com.company.ch5_2_单词查找树;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ch5_2_1_单词查找树 {
    public static void main(String[] args) {
        String[] inputs = {
                "she",
                "sells",
                "sea",
                "shells",
                "by",
                "the",
                "sea",
                "shore",
        };
        TrieST<Integer> trieST = new TrieST<>();
        //init
        Arrays.stream(inputs).forEach((s) -> trieST.put(s, s.hashCode()));
        //size，isEmpty
        System.out.println(String.format("size = %d, isEmpty: %s",
                trieST.size(), trieST.isEmpty()));
        //keyWithPrefix
        Iterable<String> strings1 = trieST.keysWithPrefix("");
        System.out.println(String.join(",", strings1));
        //keyWithPrefix
        Iterable<String> strings2 = trieST.keysWithPrefix("sh");
        System.out.println(String.join(",", strings2));
        //longestPrefix
        String join3 = Stream.of("she", "shell", "shellsort", "shelters")
                .map(s -> s + " -> " + trieST.longestPrefixOf(s))
                .collect(Collectors.joining(","));
        System.out.println(join3);
        // delete
        String keys1 = String.join(",", trieST.keys());
        trieST.delete("shells");
        String keys2 = String.join(",", trieST.keys());
        System.out.println(keys1 + " -> " + keys2);
    }

    private static class TrieST<Value> implements StringST<Value> {
        private static final int R = 256;
        private Node root;

        @Override
        public int size() {
            return size(root);
        }

        /**
         * 延时递归实现
         * <p>
         * size的实现方式:
         * 即时实现：用一个实例变量保存键的数量。
         * 更加即时的实现：用结点的实例变量保存子单词查找树中键的数量，在递归的put()和delete()方法调用之后更新它们。
         * 延时递归实现：如上页框注“单词查找树的延时递归方法size()”所示。它会遍历单词查找树中的所有结点并记录非空值结点的总数。
         */
        private int size(Node x) {
            if (x == null) return 0;
            int cnt = 0;
            if (x.val != null) cnt++;
            for (char c = 0; c < R; c++)
                cnt += size(x.next[c]);
            return cnt;
        }

        @Override
        public void put(String key, Value val) {
            root = put(root, key, val, 0);
        }

        private Node put(Node x, String key, Value val, int d) {
            if (x == null) x = new Node();
            if (d == key.length()) {
                x.val = val;
                return x;
            }
            char c = key.charAt(d);
            x.next[c] = put(x.next[c], key, val, d + 1);
            return x;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Value get(String key) {
            Node node = get(root, key, 0);
            if (node == null) return null;
            return (Value) node.val;
        }

        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) return x;
            char c = key.charAt(d);
            return get(x.next[c], key, d + 1);
        }

        @Override
        public Iterable<String> keys() {
            return keysWithPrefix("");
        }

        @Override
        public Iterable<String> keysWithPrefix(String s) {
            Queue<String> queue = new LinkedList<>();
            collect(get(root, s, 0), s, queue);//找到前缀终止的节点，然后开始搜集
            return queue;
        }

        private void collect(Node x, String pre, Queue<String> q) {
            if (x == null) return;
            if (x.val != null) q.add(pre);
            for (char c = 0; c < R; c++)
                collect(x.next[c], pre + c, q);
        }

        @Override
        public Iterable<String> keysThatMatch(String s) {
            Queue<String> queue = new LinkedList<>();
            collect(root, "", s, queue);
            return queue;
        }

        /**
         * 模式匹配
         *
         * @param pat 模式，仅支持.符号
         */
        private void collect(Node x, String pre, String pat, Queue<String> q) {
            int d = pre.length();
            if (x == null) return;
            if (d == pat.length()) {
                if (x.val != null) q.add(pre);
                return;
            }
            char next = pat.charAt(d);
            for (char c = 0; c < R; c++)
                if (next == '.' || c == next)
                    collect(x.next[c], pre + c, pat, q);
        }

        @Override
        public String longestPrefixOf(String s) {
            int length = search(root, s, 0, 0);
            return s.substring(0, length);
        }

        private int search(Node x, String s, int d, int length) {
            if (x == null) return length;
            if (x.val != null) length = d;
            if (d == s.length()) return length;
            char c = s.charAt(d);
            return search(x.next[c], s, d + 1, length);
        }

        @Override
        public void delete(String key) {
            root = delete(root, key, 0);
        }

        private Node delete(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length())//要删除的键
                x.val = null;//该节点改为非键节点(之前可能为键节点，也可能不是)
            else {
                char c = key.charAt(d);
                x.next[c] = delete(x.next[c], key, d + 1);//向下递归
            }

            if (x.val != null) return x;//键节点返回

            for (char c = 0; c < R; c++)//非键节点需要检查子节点
                if (x.next[c] != null) return x;//有子节点的非键节点返回

            return null;//没有子节点的非键节点需要删除
        }

        @Override
        public boolean contains(String key) {
            int length = search(root, key, 0, 0);
            return key.length() == length;
        }

        @Override
        public boolean isEmpty() {
            return root == null;
        }

        private static class Node {
            private Object val;
            private Node[] next = new Node[R];
        }

    }
}

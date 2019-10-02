package com.company.ch5_1_字符串排序;

import java.util.Arrays;

public class ch5_1_3_高位优先的字符串排序 {
    public static void main(String[] args) {
        String[] inputs = {
                "she",
                "shells",
                "seashells",
                "by",
                "the",
                "seashore",
                "the",
                "shells",
                "she",
                "sells",
                "are",
                "surely",
                "seashells",
        };
        sort(inputs);
        System.out.println(Arrays.toString(inputs));
    }

    private static void sort(String[] inputs) {
        MSD.sort(inputs);
    }

    /**
     * 高位优先
     */
    private static class MSD {
        /**
         * 扩展ASCII码
         */
        private static final int R = 256;
        private static final int M = 3;
        private static String[] aux;

        private static int charAt(String s, int d) {
            if (d < s.length()) return s.charAt(d);
            else return -1;
        }

        private static void sort(String[] a) {
            int N = a.length;
            aux = new String[N];
            sort(a, 0, N - 1, 0);
        }

        /**
         * 高位优先排序
         */
        private static void sort(String[] a, int lo, int hi, int d) {
            if (hi <= lo + M) {
                insertionSort(a, lo, hi, d);//为解决大量小型子数组，转为插入排序
                return;
            }
            int[] count = new int[R + 2];
            for (int i = lo; i <= hi; i++) {
                count[charAt(a[i], d) + 2]++;
            }
            for (int r = 0; r < R + 1; r++) {
                count[r + 1] += count[r];
            }
            for (int i = lo; i <= hi; i++) {
                aux[count[charAt(a[i], d) + 1]++] = a[i];
            }
            System.arraycopy(aux, 0, a, lo, hi + 1 - lo);

            for (int r = 0; r < R; r++) {
                sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
            }
        }

        /**
         * 插入排序
         */
        private static void insertionSort(String[] a, int lo, int hi, int d) {
            for (int i = lo; i <= hi; i++) {
                for (int j = i; j > lo; j--) {
                    if (less(a[j], a[j - 1], d)) {
                        String tmp = a[j];
                        a[j] = a[j - 1];
                        a[j - 1] = tmp;
                    }
                }
            }
        }

        private static boolean less(String a, String b, int d) {
            return a.substring(d).compareTo(b.substring(d)) < 0;
        }
    }
}

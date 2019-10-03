package com.company.ch5_1_字符串排序;

/**
 * 尽管 排序 的 方式 有所不同， 但 三 向 字符串 快速 排序 根据 的 仍然是 键 的 首 字母 并使 用 递归 方法
 * 将其 余 部分 的 键 排序。 对于 字符串 的 排序， 这个 方法 比 普通 的 快速 排序 和 高位 优先 的 字符串
 * 排序 更 友好。 实际上， 它 就是 这 两种 算法 的 结合。
 */
public class ch5_1_4_三向字符串快速排序 {
    public static void main(String[] args) {
        String[] inputs = {
                "edu.princeton.cs",
                "com.apple",
                "edu.princeton.cs",
                "com.cnn",
                "com.google",
                "edu.uva.cs",
                "edu.princeton.cs",
                "edu.princeton.cs.www",
                "edu.uva.cs",
                "edu.uva.cs",
                "edu.uva.cs",
                "com.adobe",
                "edu.princeton.ee",
        };
        Quick3String.sort(inputs);
        System.out.println(String.join("\n", inputs));
    }

    /**
     * 三向字符串排序
     */
    private static class Quick3String {
        private static int charAt(String s, int d) {
            if (d < s.length()) return s.charAt(d);
            else return -1;
        }

        private static void sort(String[] a) {
            sort(a, 0, a.length - 1, 0);
        }

        private static void sort(String[] a, int lo, int hi, int d) {
            if (hi <= lo) return;
            int lt = lo, gt = hi;
            int v = charAt(a[lo], d);//切分元素
            int i = lo + 1;
            while (i <= gt) {//遍历，三向切分
                int t = charAt(a[i], d);
                if (t < v) exchange(a, lt++, i++);//规整小于v的元素
                else if (t > v) exchange(a, gt--, i++);//规整大于v的元素
                else i++;//正好等于v的元素
            }
            sort(a, lo, lt - 1, d);
            if (v > 0) sort(a, lt, gt, d + 1);//从当前位的下一位开始
            sort(a, gt + 1, hi, d);
        }

        private static void exchange(String[] a, int x, int y) {
            String tmp = a[x];
            a[x] = a[y];
            a[y] = tmp;
        }

    }
}

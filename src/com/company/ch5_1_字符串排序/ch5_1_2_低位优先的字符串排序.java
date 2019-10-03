package com.company.ch5_1_字符串排序;

import java.util.Arrays;

/**
 * 假设 有 一位 工程师 架设 了 一个 设备 来 记录 给定 时间 段 内 某 条 忙碌 的 高速公路 上 所有 车辆 的 车 牌号，
 * 他 希望 知道 总共 有 多少 辆 不同 的 车辆 经过 了 这段 高速公路。
 * 根据 2. 1 节 你 可以 知道， 解决 这个 问题 的 一种 简单 方法 就是 将 所有 车牌 号 排序，
 * 然后 遍历 并 找出 所有 不同 的 车牌 号的 数量， 如 Dedup 所示（ 请见 3. 5. 2. 1 节 框 注“ Dedup 过滤器”）。
 * 车 牌号 由 数字 和 字母 混合 组成， 因此 一般 都将 它们 表示 为 字符串。
 * 在最 简单 的 情况 中（ 例如 图 5. 1. 6 所示 的 加 利 福 尼亚 州 的 车 牌号）， 这些 字符串 的 长度 都是 相同 的。
 * 这种 情况 在 排序 应用 中 很 常见—— 比如 电话号码、 银行 账号、 IP 地址 等 都是 典型的 定 长 字符串。
 */
public class ch5_1_2_低位优先的字符串排序 {
    public static void main(String[] args) {
        String[] input = {
                "4PGC938",
                "2IYE230",
                "3CIO720",
                "1ICK750",
                "1OHV845",
                "4JZY524",
                "1ICK750",
                "3CIO720",
                "1OHV845",
                "1OHV845",
                "2RLA629",
                "2RLA629",
                "3ATW723",
        };
        sort(input, 7);
        System.out.println(Arrays.toString(input));
    }

    /**
     * @param arr 输入
     * @param w   等长字符串的长度
     */
    private static void sort(String[] arr, int w) {
        int N = arr.length;
        String[] aux = new String[N];//访问N次
        int R = 256;
        //进行w次键索引计数排序
        for (int i = w - 1; i >= 0; i--) {//每轮循环访问7N+3R+1次
            int[] count = new int[R + 1];//访问R+1次
            for (String s : arr) count[s.charAt(i) + 1]++;//访问2N次
            for (int j = 0; j < R; j++) count[j + 1] += count[j];//访问2R次
            for (String s : arr) aux[count[s.charAt(i)]++] = s;//访问3N次
            System.arraycopy(aux, 0, arr, 0, N);//访问2N次
        }
    }
}

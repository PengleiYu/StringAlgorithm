package com.company.ch5_1_字符串排序;

import java.util.Arrays;
import java.util.Random;

/**
 * 一种 适用于 小 整数 键 的 简单 排序 方法。
 * <p>
 * 老师 在 统计学 生的 分数 时 可能 会 遇到 以下 数据 处理问题。
 * 学生 被 分为 若干 组， 标号 为 1、 2、 3 等。 在 某些 情况下， 我们 希望 将 全班 同学 按 组 分类。
 * 因为 组 的 编号 是 较小 的 整数， 使用 键 索引 计数 法 来 排序 是 很 合适 的，
 */
public class ch5_1_1_键索引计数法 {
    public static void main(String[] args) {
        int R = 5;
        Student[] arr = Student.generator(100, R);

        sort(arr, R);

        System.out.println(Arrays.toString(arr));

    }

    private static void sort(IKey[] input, int R) {
        // 初始化
        IKey[] aux = new IKey[input.length];//访问N次
        int[] count = new int[R + 1];//访问R+1次
        // 频率统计
        for (IKey iKey : input) count[iKey.key() + 1]++;//访问2N次
        // 频率转换为索引
        for (int i = 0; i < R; i++) count[i + 1] += count[i];//访问2R次
        // 数据分类
        for (IKey item : input) aux[count[item.key()]++] = item;//访问3N次
        // 回写
        System.arraycopy(aux, 0, input, 0, input.length);//访问2N次
    }

    private interface IKey {
        int key();
    }

    private static class Student implements IKey {
        final String name;
        final int key;

        private Student(String name, int key) {
            this.name = name;
            this.key = key;
        }

        @Override
        public int key() {
            return key;
        }

        @Override
        public String toString() {
            return String.format("Student(%s,%d)", name, key);
        }

        static Student[] generator(int size, int keyRange) {
            Student[] arr = new Student[size];
            Random random = new Random();
            for (int i = 0; i < size; i++) {
                arr[i] = new Student("Student" + i, random.nextInt(keyRange));
            }
            return arr;
        }
    }
}

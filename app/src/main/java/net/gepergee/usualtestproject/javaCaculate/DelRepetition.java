package net.gepergee.usualtestproject.javaCaculate;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符去重
 * @author petergee
 * @date 2018/6/25
 */
public class DelRepetition {
    public static void main(String[] args) {
        String str = "abcdabc";
        char[] arr = str.toCharArray();
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (list.contains(arr[i])) {
                continue;
            }
            list.add(arr[i]);
        }
        System.out.println(list.toString());
    }
}

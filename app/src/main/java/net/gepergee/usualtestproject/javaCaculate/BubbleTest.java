package net.gepergee.usualtestproject.javaCaculate;


import android.os.Handler;

/**
 * @author petergee
 * @date 2018/5/16
 */
public class BubbleTest {
    public static void main(String[] args){
        int [] arr={1,2,5,3,6,88,21};
        System.out.println("排序前：");
        for(int i :arr){
            System.out.print(i+"   ");
        }
       // bubble(arr);
        select(arr);
        System.out.println();
        System.out.println("排序后：");
        for(int i :arr){
            System.out.print(i+"  ");
        }
    }

    /**
     * 选择排序
     * @param arr
     */
    private static void select(int[] arr) {
        int temp;
        for (int i=0;i<arr.length;i++){
            for (int j=i+1;j<arr.length;j++){
                if (arr[i]>arr[j]){
                    temp=arr[i];
                    arr[i]=arr[j];
                    arr[j]=temp;
                }
            }
        }
    }

    /**
     * 冒泡排序
     * @param arr
     */
    private static void bubble(int[] arr) {
        for (int i=0;i<arr.length-1;i++){
            for (int j=0;j<arr.length-1-i;j++){
                if (arr[j]<arr[j+1]){
                    int temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
    }
}

package net.gepergee.usualtestproject.generic;

import java.util.Arrays;
import java.util.List;

/** 泛型类及方法调用
 * @author petergee
 * @date 2018/8/29
 */
public class GenericTestTwo {
    // main Method
    public static void main(String[]args){
        GenericTest<String> g=new GenericTest<>();
        g.setT("Three Zhang");
        String s=g.getT();
        System.out.println("g getter Value is  "+g.getValue("Four Li"));
        System.out.println("value getter is "+s);
        System.out.println("boundary symbol is  "+g.getBoundarySymbolMethod("Five Wang"));

        GenericTest<Integer> g2=new GenericTest<>();
        g2.setT(100);
        System.out.println("value getter g2 is  "+g2.getT());

        // 通配符
        GenericTest<ClassFruit> g3=new GenericTest<>();
        List<ClassApple> aList= Arrays.asList(new ClassApple());
        List<ClassOrange> oList=Arrays.asList(new ClassOrange());
        g3.genericSymbolM1(aList);
        g3.genericSymbolM1(oList);

    }
}

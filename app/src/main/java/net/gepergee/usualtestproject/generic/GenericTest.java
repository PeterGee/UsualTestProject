package net.gepergee.usualtestproject.generic;

import java.util.List;

/**
 * java 泛型类、泛型方法
 * @author petergee
 * @date 2018/8/29
 */
public class GenericTest<T> {
    private T t;
    // generic method
    public T getValue(T t){
        return t;
    }

    // getter
    public T getT() {
        return t;
    }
    // setter
    public void setT(T t) {
        this.t = t;
    }

    // 边界符
    public <V extends T> T getBoundarySymbolMethod(V v){
        return v;
    }

    /**
     * Producer Extends – 如果你需要一个只读List，用它来produce T，那么使用? extends T。
     * Consumer Super – 如果你需要一个只写List，用它来consume T，那么使用? super T。
     * 如果需要同时读取以及写入，那么我们就不能使用通配符了。
     * @param list
     * @return
     */
    // 通配符基本使用方式1
    public T genericSymbolM1(List<? extends T> list){
        return list.get(0);
    }

    // 通配符基本使用方式2
    public T genericSymbolM2(List<? super T> list){
        return (T) list.get(0);
    }


}


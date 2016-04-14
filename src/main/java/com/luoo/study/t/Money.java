package com.luoo.study.t;

/**
 * Created by Luoo2 on 2016/3/31.
 */
public interface Money<E> {
    E get(int index);

    boolean add(E e);

}

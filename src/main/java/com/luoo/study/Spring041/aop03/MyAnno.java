package com.luoo.study.Spring041.aop03;

import java.lang.annotation.*;

/**
 * Created by Luoo on 2016/8/12.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAnno {
}

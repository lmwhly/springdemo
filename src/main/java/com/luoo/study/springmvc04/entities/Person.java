package com.luoo.study.springmvc04.entities;

/**
 * @author Luoo2
 * @create 2016-08-15 0:23
 */

public class Person {
    /*
     * 婚否
     */private boolean isMarried;
    /*
     * 爱好
     */private String[] hobbies;
    /**
     * 学历
     */private String education;



    public boolean getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(boolean isMarried) {
        isMarried = isMarried;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}

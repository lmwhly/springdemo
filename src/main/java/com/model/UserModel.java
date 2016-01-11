package com.model;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Luoo on 2016/1/11.
 */
public class UserModel {
    private int userid;
    private int topicid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }
}

package com.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Luoo on 2016/1/11.
 */
public class DataBinderTestModel {
    @Autowired
    private String username;
    @Autowired
    private boolean bool;//Boolean值测试
    @Autowired
    private SchoolInfoModel schooInfo;
    @Autowired
    private List hobbyList;//集合测试，此处可以改为数组/Set进行测试
    @Autowired
    private Map map;//Map测试
    @Autowired
    private PhoneNumberModel phoneNumber;//String->自定义对象的转换测试
    @Autowired
    private Date date;//日期类型测试
    @Autowired
    private UserState state;//String——>Enum类型转换测试
}

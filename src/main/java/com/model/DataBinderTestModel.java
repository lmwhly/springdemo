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
    private boolean bool;//Booleanֵ����
    @Autowired
    private SchoolInfoModel schooInfo;
    @Autowired
    private List hobbyList;//���ϲ��ԣ��˴����Ը�Ϊ����/Set���в���
    @Autowired
    private Map map;//Map����
    @Autowired
    private PhoneNumberModel phoneNumber;//String->�Զ�������ת������
    @Autowired
    private Date date;//�������Ͳ���
    @Autowired
    private UserState state;//String����>Enum����ת������
}

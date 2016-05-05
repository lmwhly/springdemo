package com.controller.convert;

import com.model.PhoneNumberModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * StringToPhoneNumberConverter Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>一月 12, 2016</pre>
 */

/*用于配置spring中测试的环境*/
@RunWith(SpringJUnit4ClassRunner.class)

/*
用来指定加载的Spring配置文件的位置,会加载默认配置文件
@ContextConfiguration 注解有以下两个常用的属性：
locations：可以通过该属性手工指定 Spring 配置文件所在的位置，可以指定一个或多个 Spring 配置文件。
inheritLocations：是否要继承父测试用例类中的 Spring 配置文件，默认为 true。
*/
@ContextConfiguration(locations = "classpath:servlet-context.xml")
/*
@TransactionConfiguration是配置事务情况的注解.
第一个参数transactionManager是你在applicationContext.xml或bean.xml中定义的事务管理器的bean的id;
第二个参数defaultRollback是表示测试完成后事务是否会滚 参数是布尔型的 默认就是true 但强烈建议写上true
*/
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class StringToPhoneNumberConverterTest {
    /*@Resource
    private StringToPhoneNumberConverter conver;*/

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: convert(String source)
     */
    @Test
    public void testConvert() throws Exception {
//TODO: Test goes here...
        StringToPhoneNumberConverter contest = new StringToPhoneNumberConverter();
        PhoneNumberModel testmodel = new PhoneNumberModel();


        int i = 0;

        if(i!=-1){
            System.out.println("-----------YouMeek.com-----------值=" +  i+ "," + "当前类=StringToPhoneNumberConverterTest.testConvert()");
        }







    }


}

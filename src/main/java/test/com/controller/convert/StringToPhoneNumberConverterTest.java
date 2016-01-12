package test.com.controller.convert;

import com.controller.convert.StringToPhoneNumberConverter;
import com.model.PhoneNumberModel;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * StringToPhoneNumberConverter Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>һ�� 12, 2016</pre>
 */

/*��������spring�в��ԵĻ���*/
@RunWith(SpringJUnit4ClassRunner.class)

/*
����ָ�����ص�Spring�����ļ���λ��,�����Ĭ�������ļ�
@ContextConfiguration ע���������������õ����ԣ�
locations������ͨ���������ֹ�ָ�� Spring �����ļ����ڵ�λ�ã�����ָ��һ������ Spring �����ļ���
inheritLocations���Ƿ�Ҫ�̳и������������е� Spring �����ļ���Ĭ��Ϊ true��
*/
@ContextConfiguration(locations = "classpath:servlet-context.xml")
/*
@TransactionConfiguration���������������ע��.
��һ������transactionManager������applicationContext.xml��bean.xml�ж���������������bean��id;
�ڶ�������defaultRollback�Ǳ�ʾ������ɺ������Ƿ��� �����ǲ����͵� Ĭ�Ͼ���true ��ǿ�ҽ���д��true
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
    }


} 

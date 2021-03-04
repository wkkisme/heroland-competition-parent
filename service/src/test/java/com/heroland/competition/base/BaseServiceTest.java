package com.heroland.competition.base;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 **/
//@Ignore // NOTE 自动测试忽略此类
//@RunWith(SpringRunner.class)  //使用junit4进行测试
//@SpringBootTest(classes = ServiceApplicationTest.class)
//@ContextConfiguration(locations = {"classpath:redis-dev-standalone.properties"}) //加载配置文件
public class BaseServiceTest {
    // 测试用户
    private static final Long userId = 91653370601473L;

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    public void showResult(Object obj) {
    }

    public Long getUserId() {
        return userId;
    }
}

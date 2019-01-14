package me.fmeng.types.test;

import me.fmeng.types.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author fmeng
 * @since 2019/01/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TypesApplication.class)
public class TypesTest {
    @Resource
    private UserDao userDao;

//    static {
//        System.setProperty(StoreAnnotationUtil.SCAN_PACKAGE_ARGS_NAME_PREFIX
//                + ".types.TypesApplication", "me.fmeng.types.test");
//    }

    @Test
    public void insertEnum() {
        userDao.insertSelective(UserEntity.builder()
                .userType(UserTypeEnum.GEN)
                .build());
    }

    @Test
    public void queryEnum() {
        UserEntity userEntity = userDao.selectByPrimaryKey(1);
        System.out.println(JsonUtil.encodeQuietly(userEntity));
    }
}

package me.fmeng.types.test;

import org.springframework.stereotype.Repository;

/**
 * @author fmeng
 * @since 2019/01/14
 */
@Repository
public interface UserDao {

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);
}
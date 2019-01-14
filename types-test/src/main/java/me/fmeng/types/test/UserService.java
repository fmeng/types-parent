package me.fmeng.types.test;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fmeng
 * @since 2019/01/14
 */
@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public void insert() {

    }
}

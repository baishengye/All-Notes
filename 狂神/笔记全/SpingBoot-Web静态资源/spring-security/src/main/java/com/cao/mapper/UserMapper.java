package com.cao.mapper;

import com.cao.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: huahua
 * @Date: 2021-04-20 12:52
 */
@Repository
public interface UserMapper {
    User login(@Param("ugh") String ugh, @Param("password") String password);

    User findUserByGh(@Param("ugh") String ugh);
}

package com.cao.service;

import com.cao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: huahua
 * @Date: 2021-04-20 12:18
 */
@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String ugh) throws UsernameNotFoundException {

        com.cao.pojo.User user = userMapper.findUserByGh(ugh);
        System.out.println(user);
        if (user == null) {
            //数据库中没有，认证失败
            throw new UsernameNotFoundException("该用户不存在");
        }

        //权限拆分   字符串拆分成数组
        String s = user.getUqx();
        String[] arr1 = s.split(",");

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(arr1);

        //
        return new User(user.getUgh(), new BCryptPasswordEncoder().encode(user.getUpsw()), authorities);
    }
}

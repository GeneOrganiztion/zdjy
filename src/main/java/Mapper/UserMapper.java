package Mapper;

import po.User;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface UserMapper extends Mapper<User>,MySqlMapper<User> {
    /*int deleteByPrimaryKey(UserKey key);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(UserKey key);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);*/
}
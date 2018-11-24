package com.thunisoft.core.dao;

import java.util.List;
import java.util.Map;

import com.thunisoft.core.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	int insertSelective(User record);

	List<User> selectSelective(User record);

	User selectByPrimaryKey(Integer userid);

	int updateByPrimaryKeySelective(User record);

	int deleteByPrimaryKey(Integer userid);

	int deleteByPrimaryKeys(String[] ids);

	List<User> selectPageList(Map<String, Object> record);

	int selectPageListCount(Map<String, Object> record);
}
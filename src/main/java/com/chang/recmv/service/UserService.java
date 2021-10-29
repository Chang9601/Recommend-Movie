package com.chang.recmv.service;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chang.recmv.mapper.UserMapper;
import com.chang.recmv.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserMapper mapper;
	
	/*public List<User> getAll() throws Exception {
		return mapper.getAll();
	}*/
	
	public void addUser(User user) throws Exception {
		mapper.addUser(user);
	}
	
	public String ckDupId(String id) throws Exception {
		return mapper.ckDupId(id);
	}
}

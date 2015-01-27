package org.common.service.impl;

import org.common.dao.UserRepository;
import org.common.entity.view.PaginationView;
import org.common.entity.view.user.UserBaseView;
import org.common.service.IPaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paginationService")
public class PaginationServiceImpl implements IPaginationService {
	@Autowired
	private UserRepository userRepository;
	
	public void selectUser(PaginationView<UserBaseView> paginationView) {
		paginationView.setCount(userRepository.selectUserCountByPage(paginationView.loadFilter()));
		paginationView.setPaginationList(userRepository.selectUserByPage(paginationView.loadFilter()));
	}
}

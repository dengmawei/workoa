package org.common.service;

import org.common.entity.view.PaginationView;
import org.common.entity.view.user.UserBaseView;

public interface IPaginationService {
	/**
	 * 分页查询用户
	 * @param paginationView
	 */
	void selectUser(PaginationView<UserBaseView> paginationView);
}

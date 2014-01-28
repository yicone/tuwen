package com.lutours.tuwen.service;

/**
 * Created by xdzheng on 14-1-28.
 */
public interface IUserService {
	User register(LoginWay loginWay);

	User Login(int userId);

	void Logout(int userId);

	void follow(int fromUserId, int toUserId);

	void unFollow(int fromUserId, int toUserId);
}

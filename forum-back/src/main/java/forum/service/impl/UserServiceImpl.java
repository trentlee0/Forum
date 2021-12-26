package forum.service.impl;

import forum.dao.UserMapper;
import forum.pojo.User;
import forum.pojo.req.UserReq;
import forum.pojo.resp.UserResp;
import forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResp getUserById(long uId) {
        return userMapper.getUserById(uId);
    }

    @Override
    public UserResp getUserByLogin(String uName, String uPass) {
        return userMapper.getUserByLogin(uName, uPass);
    }

    @Override
    public UserResp getUserByName(String uName) {
        return userMapper.getUserByName(uName);
    }

    @Override
    public List<UserResp> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public int addUser(String uName, String uPass) {
        return userMapper.addUser(uName, uPass);
    }

    @Override
    public int updateUserByUserReq(UserReq user) {
        return userMapper.updateUserByUserReq(user);
    }

    @Override
    public int updateUserAvatar(long uId, String avatar) {
        return userMapper.updateUserAvatar(uId, avatar);
    }

    @Override
    public int updateUserGrade(long uId, int userGrade) {
        return userMapper.updateUserGrade(uId, userGrade);
    }

    @Override
    public int updateUserAvailable(long uId, boolean available) {
        return userMapper.updateUserAvailable(uId, available);
    }
}

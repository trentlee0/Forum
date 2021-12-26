package forum.service;

import forum.pojo.req.UserReq;
import forum.pojo.resp.UserResp;

import java.util.List;

public interface UserService {
    UserResp getUserById(long uId);

    UserResp getUserByLogin(String uName, String uPass);

    UserResp getUserByName(String uName);

    List<UserResp> queryAll();

    int addUser(String uName, String uPass);

    int updateUserByUserReq(UserReq user);

    int updateUserAvatar(long uId, String avatar);

    int updateUserGrade(long uId, int userGrade);

    int updateUserAvailable(long uId, boolean available);
}

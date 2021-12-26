package forum.dao;

import forum.pojo.req.UserReq;
import forum.pojo.resp.UserResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    UserResp getUserById(long uId);

    UserResp getUserByLogin(@Param("uName") String uName, @Param("uPass") String uPass);

    List<UserResp> queryAll();

    UserResp getUserByName(@Param("uName") String uName);

    int updateUserGrade(@Param("uId") long uId, @Param("userGrade") int userGrade);

    int addUser(@Param("uName") String uName, @Param("uPass") String uPass);

    int updateUserByUserReq(UserReq userReq);

    int updateUserAvatar(@Param("uId") long uId, @Param("avatar") String avatar);

    int updateUserAvailable(@Param("uId") long uId, @Param("available") boolean available);
}

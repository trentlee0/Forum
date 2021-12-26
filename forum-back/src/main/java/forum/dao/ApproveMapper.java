package forum.dao;

import forum.pojo.Approve;
import org.apache.ibatis.annotations.Param;

public interface ApproveMapper {
    Approve queryByUId(@Param("uId") long uId, @Param("postId") long postId);

    long addByUId(@Param("uId") long uId, @Param("postId") long postId);

    long countByPostId(@Param("postId") long postId);
}

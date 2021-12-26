package forum.dao;

import forum.pojo.resp.PostResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PostMapper {
    PostResp getPost(long postId);

    int updatePost(@Param("postId") long postId, @Param("postName") String postName, @Param("content") String content, @Param("recycle") boolean recycle, @Param("updateDatetime") Timestamp updateDatetime);

    int addPost(@Param("uId") long uId, @Param("plateId") long plateId, @Param("postName") String postName, @Param("content") String content, @Param("createDatetime") Timestamp createDatetime);

    List<PostResp> queryAllPosts();

    List<PostResp> queryPostsByUId(long uId);

    List<PostResp> queryPostsByPId(long plateId);

    long getCountByPId(long plateId);

    long deleteByPId(@Param("postId") long postId);

    List<PostResp> search(String postName);
}

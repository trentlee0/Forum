package forum.service;

import forum.pojo.Post;
import forum.pojo.resp.PostResp;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface PostService {
    PostResp getPost(long postId);

    long deleteByPId(long postId);

    int updatePost(long postId, String postName, String content, boolean recycle, Timestamp updateDatetime);

    int addPost(long uId, long plateId, String postName, String content, Timestamp createDatetime);

    List<PostResp> queryAllPosts();

    List<PostResp> queryPostsByUId(long uId);

    List<PostResp> queryPostsByPId(long plateId);

    long getCountByPId(long plateId);

    List<PostResp> search(String postName);
}

package forum.dao;

import forum.pojo.Comment;
import forum.pojo.resp.CommentResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CommentMapper {
    CommentResp queryByCId(long cId);

    int addComment(@Param("text") String text, @Param("postId") long postId, @Param("uId") long uId, @Param("publishDatetime") Timestamp publishDatetime);

    int updateComment(@Param("cId") long cId, @Param("text") String text, @Param("publishDatetime") Timestamp publishDatetime);

    int deleteComment(@Param("cId") long cId);

    int deleteCommentByPostId(@Param("postId") long postId);

    List<CommentResp> queryCommentsByPostId(@Param("postId") long postId);

    List<CommentResp> queryCommentsByUId(@Param("uId") long uId);
}

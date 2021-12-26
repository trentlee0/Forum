package forum.service;

import forum.pojo.resp.CommentResp;

import java.sql.Timestamp;
import java.util.List;

public interface CommentService {
    CommentResp queryByCId(long cId);

    int addComment(String text, long postId, long uId, Timestamp publishDatetime);

    int updateComment(long cId, String text, Timestamp publishDatetime);

    int deleteComment(long cId);

    int deleteCommentByPostId(long postId);

    List<CommentResp> queryCommentsByPostId(long postId);

    List<CommentResp> queryCommentsByUId(long uId);
}

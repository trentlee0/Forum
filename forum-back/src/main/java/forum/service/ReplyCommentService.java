package forum.service;


import forum.pojo.ReplyComment;
import forum.pojo.resp.ReplyCommentResp;

import java.sql.Date;
import java.util.List;

public interface ReplyCommentService {
    ReplyComment getReplyCommentById(long rcId);

    int addReplyComment(String text, long uId, long cId, Date publishDatetime);

    int updateReplyComment(long rcId, String text, Date publishDatetime);

    int deleteComment(long rcId);

    List<ReplyCommentResp> queryReplyCommentsByCId(long cId);
}

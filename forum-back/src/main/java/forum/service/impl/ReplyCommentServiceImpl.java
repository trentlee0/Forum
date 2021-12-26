package forum.service.impl;

import forum.pojo.ReplyComment;
import forum.pojo.resp.ReplyCommentResp;
import forum.service.ReplyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ReplyCommentServiceImpl implements ReplyCommentService {

    @Autowired
    private ReplyCommentService replyCommentService;

    @Override
    public ReplyComment getReplyCommentById(long rcId) {
        return replyCommentService.getReplyCommentById(rcId);
    }

    @Override
    public int addReplyComment(String text, long uId, long cId, Date publishDatetime) {
        return replyCommentService.addReplyComment(text, uId, cId, publishDatetime);
    }

    @Override
    public int updateReplyComment(long rcId, String text, Date publishDatetime) {
        return replyCommentService.updateReplyComment(rcId, text, publishDatetime);
    }

    @Override
    public int deleteComment(long rcId) {
        return replyCommentService.deleteComment(rcId);
    }

    @Override
    public List<ReplyCommentResp> queryReplyCommentsByCId(long cId) {
        return replyCommentService.queryReplyCommentsByCId(cId);
    }
}

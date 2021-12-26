package forum.service.impl;

import forum.dao.CommentMapper;
import forum.pojo.Comment;
import forum.pojo.Plate;
import forum.pojo.resp.CommentResp;
import forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommentResp queryByCId(long cId) {
        return commentMapper.queryByCId(cId);
    }

    @Override
    public int addComment(String text, long postId, long uId, Timestamp publishDatetime) {
        return commentMapper.addComment(text, postId, uId, publishDatetime);
    }

    @Override
    public int updateComment(long cId, String text, Timestamp publishDatetime) {
        return commentMapper.updateComment(cId, text, publishDatetime);
    }

    @Override
    public int deleteComment(long cId) {
        return commentMapper.deleteComment(cId);
    }

    @Override
    public int deleteCommentByPostId(long postId) {
        return commentMapper.deleteCommentByPostId(postId);
    }

    @Override
    public List<CommentResp> queryCommentsByPostId(long postId) {
        return commentMapper.queryCommentsByPostId(postId);
    }

    @Override
    public List<CommentResp> queryCommentsByUId(long uId) {
        return commentMapper.queryCommentsByUId(uId);
    }
}

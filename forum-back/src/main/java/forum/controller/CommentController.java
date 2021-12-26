package forum.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import forum.constant.UserGrade;
import forum.pojo.resp.CommentResp;
import forum.pojo.resp.ResponseData;
import forum.pojo.resp.UserResp;
import forum.service.CommentService;
import forum.util.ResponseUtil;
import forum.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comments/{cId}", method = RequestMethod.GET)
    public ResponseData queryComment(@PathVariable long cId) {
        return ResponseUtil.success(commentService.queryByCId(cId));
    }

    @RequestMapping(value = "/comments/post/{postId}", method = RequestMethod.GET)
    public ResponseData queryComments(@PathVariable long postId) {
        return ResponseUtil.success(commentService.queryCommentsByPostId(postId));
    }

    @RequestMapping(value = "/comments/user", method = RequestMethod.GET)
    public ResponseData queryCommentsByUId(String token,
                                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ResponseUtil.success(new PageInfo<>(commentService.queryCommentsByUId(TokenUtil.getUserResp(token).getUId())));
    }

    @RequestMapping(value = "/comments/{cId}", method = RequestMethod.DELETE)
    public ResponseData removeCommentsByCId(String token, @PathVariable long cId) {
        UserResp o = TokenUtil.getUserResp(token);
        if (o == null)
            return ResponseUtil.unauthorized();
        List<CommentResp> comments = commentService.queryCommentsByUId(o.getUId());
        for (CommentResp comment : comments) {
            if (comment.getCId() == cId) {
                int i = commentService.deleteComment(cId);
                return ResponseUtil.resultModify(i);
            }
        }
        return ResponseUtil.unauthorized();
    }

    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public ResponseData addComment(String token, long postId, long uId, String text) {
        UserResp userResp = TokenUtil.getUserResp(token);
        if (userResp == null) return ResponseUtil.unauthorized();

        Timestamp time = new Timestamp(System.currentTimeMillis());
        return ResponseUtil.resultModify(commentService.addComment(text, postId, uId, time));
    }

    @RequestMapping(value = "/comments", method = RequestMethod.PATCH)
    public ResponseData updateComment(String token, long cId, String text) {
        UserResp o = TokenUtil.getUserResp(token);
        if (o == null) return ResponseUtil.unauthorized();

        Timestamp time = new Timestamp(System.currentTimeMillis());

        List<CommentResp> comments = commentService.queryCommentsByUId(o.getUId());
        for (CommentResp comment : comments) {
            if (comment.getCId() == cId) {
                int i = commentService.updateComment(cId, text, time);
                return ResponseUtil.resultModify(i);
            }
        }

        return ResponseUtil.unauthorized();
    }
}

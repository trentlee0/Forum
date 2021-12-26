package forum.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import forum.constant.Cache;
import forum.constant.UserGrade;
import forum.pojo.User;
import forum.pojo.resp.PostResp;
import forum.pojo.resp.ResponseData;
import forum.pojo.resp.UserResp;
import forum.service.*;
import forum.util.ResponseUtil;
import forum.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ApproveService approveService;

    @RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET)
    public ResponseData query(@PathVariable long postId) {
        Map<String, Object> map = new HashMap<>();
        map.put("post", postService.getPost(postId));
        map.put("comments", commentService.queryCommentsByPostId(postId));
        map.put("approvePostCount", approveService.countByPostId(postId));
        return ResponseUtil.success(map);
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ResponseData queryAll(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PostResp> list = postService.queryAllPosts();
        return ResponseUtil.success(new PageInfo<>(list));
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public ResponseData addPost(String token, long uId, long plateId, String postName, String content) {
        Timestamp createDatetime = new Timestamp(System.currentTimeMillis());
        if (TokenUtil.isValidToken(token, uId))
            return ResponseUtil.success(postService.addPost(uId, plateId, postName, content, createDatetime));
        return ResponseUtil.unauthorized();
    }

    @RequestMapping(value = "/posts", method = RequestMethod.PATCH)
    public ResponseData updateByPost(String token, long postId, long uId, String postName, String content) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserResp o = TokenUtil.getUserResp(token);
        if (o != null && (o.getUId() == uId || o.getUGrade() > UserGrade.USER))
            return ResponseUtil.success(postService.updatePost(postId, postName, content, false, timestamp));
        return ResponseUtil.unauthorized();
    }

    @RequestMapping(value = "/posts", method = RequestMethod.DELETE)
    public ResponseData removeByPost(String token, long postId) {
        UserResp o = (UserResp) Cache.get(token);
        if (o.getUGrade() < UserGrade.MODERATOR)
            return ResponseUtil.unauthorized();

        commentService.deleteCommentByPostId(postId);
        int i = (int) postService.deleteByPId(postId);
        return ResponseUtil.resultModify(i);
    }

    @RequestMapping(value = "/posts/user/{uId}", method = RequestMethod.GET)
    public ResponseData queryByUser(@PathVariable long uId) {
        return ResponseUtil.success(postService.queryPostsByUId(uId));
    }

    @RequestMapping(value = "/posts/plate/{plateId}", method = RequestMethod.GET)
    public ResponseData queryByPost(@PathVariable long plateId) {
        return ResponseUtil.success(postService.queryPostsByPId(plateId));
    }

    @RequestMapping(value = "/posts/get", method = RequestMethod.GET)
    public ResponseData queryByPostPage(String token,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        UserResp o = TokenUtil.getUserResp(token);
        if (o == null) return ResponseUtil.unauthorized();

        PageHelper.startPage(pageNum, pageSize);
        if (o.getUGrade() == UserGrade.ADMIN) return ResponseUtil.success(new PageInfo<>(postService.queryAllPosts()));

        return ResponseUtil.success(new PageInfo<>(postService.queryPostsByUId(o.getUId())));
    }

    @RequestMapping(value = "/posts/search", method = RequestMethod.GET)
    public ResponseData search(String q,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ResponseUtil.success(new PageInfo<>(postService.search(q)));
    }
}

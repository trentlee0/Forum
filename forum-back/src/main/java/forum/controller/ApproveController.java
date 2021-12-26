package forum.controller;

import forum.pojo.resp.ResponseData;
import forum.service.ApproveService;
import forum.util.ResponseUtil;
import forum.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ApproveController {
    @Autowired
    private ApproveService approveService;

    @RequestMapping(value = "/approves/user-post", method = RequestMethod.GET)
    public ResponseData userApprovePost(long uId, long postId) {
        return ResponseUtil.success(approveService.queryByUId(uId, postId));
    }

    @RequestMapping(value = "/approves/user-post", method = RequestMethod.POST)
    public ResponseData addApprove(long uId, long postId, String token) {
        if (!TokenUtil.isValidToken(token, uId))
            return ResponseUtil.unauthorized();

        if (approveService.queryByUId(uId, postId) == null)
            return ResponseUtil.success(approveService.addByUId(uId, postId));
        return ResponseUtil.success("已经点赞了", null);
    }


    @RequestMapping(value = "/approves/post-count/{postId}", method = RequestMethod.GET)
    public ResponseData queryCountByPost(@PathVariable long postId) {
        long l = approveService.countByPostId(postId);
        Map<String, Object> map = new HashMap<>();
        map.put("count", l);
        map.put("postId", postId);
        return ResponseUtil.success(map);
    }
}

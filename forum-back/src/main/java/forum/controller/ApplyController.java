package forum.controller;

import forum.pojo.resp.ResponseData;
import forum.service.ApplyService;
import forum.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    @RequestMapping(value = "/apply/user/{uId}", method = RequestMethod.GET)
    public ResponseData queryByUser(@PathVariable long uId) {
        return ResponseUtil.success(applyService.queryByUId(uId));
    }

    @RequestMapping(value = "/applies", method = RequestMethod.POST)
    public ResponseData addApply(long uId, String text) {
        return ResponseUtil.success(applyService.addApply(uId, text));
    }

    @RequestMapping(value = "/applies", method = RequestMethod.PATCH)
    public ResponseData updateDispose(long aId, boolean dispose) {
        return ResponseUtil.success(applyService.updateDispose(aId, dispose));
    }

    @RequestMapping(value = "/applies", method = RequestMethod.GET)
    public ResponseData queryAll() {
        return ResponseUtil.success(applyService.queryAll());
    }
}

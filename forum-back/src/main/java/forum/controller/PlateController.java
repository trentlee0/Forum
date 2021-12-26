package forum.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import forum.constant.Cache;
import forum.constant.UserGrade;
import forum.pojo.Plate;
import forum.pojo.resp.PostResp;
import forum.pojo.resp.ResponseData;
import forum.pojo.resp.UserResp;
import forum.service.PlateService;
import forum.service.PostService;
import forum.util.ResponseUtil;
import forum.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PlateController {
    @Autowired
    private PlateService plateService;

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/plates/{pId}", method = RequestMethod.GET)
    public ResponseData query(@PathVariable long pId) {
        Map<String, Object> map = new HashMap<>();
        Plate plate = plateService.getPlateById(pId);
        map.put("pname", plate.getPName());
        List<PostResp> posts = postService.queryPostsByPId(pId);
        map.put("posts", posts);
        return ResponseUtil.success(map);
    }

    @RequestMapping(value = "/plates/get", method = RequestMethod.GET)
    public ResponseData queryBy(long pId,
                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Plate plate = plateService.getPlateById(pId);

        Map<String, Object> map = new HashMap<>();
        map.put("pname", plate.getPName());

        PageHelper.startPage(pageNum, pageSize);
        List<PostResp> posts = postService.queryPostsByPId(pId);
        map.put("posts", new PageInfo<>(posts));

        return ResponseUtil.success(map);
    }

    @RequestMapping(value = "/plates/user", method = RequestMethod.GET)
    public ResponseData queryByUId(String token,
                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        UserResp o = TokenUtil.getUserResp(token);
        if (o == null) return ResponseUtil.unauthorized();

        PageHelper.startPage(pageNum, pageSize);
        if (o.getUGrade() == UserGrade.ADMIN)
            return ResponseUtil.success(new PageInfo<>(plateService.queryAllPlates()));
        return ResponseUtil.success(new PageInfo<>(plateService.getPlateByUId(o.getUId())));
    }

    @RequestMapping(value = "/plates/userid", method = RequestMethod.GET)
    public ResponseData queryByUId(long uId,
                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ResponseUtil.success(new PageInfo<>(plateService.getPlateByUId(uId)));
    }

    @RequestMapping(value = "/plates/user-plate", method = RequestMethod.PATCH)
    public ResponseData updateByUId(String token, long pId, String pName) {
        UserResp o = TokenUtil.getUserResp(token);
        if (o == null) return ResponseUtil.unauthorized();

        Plate plate = plateService.getPlateById(pId);
        if (plate != null && (plate.getUId() == o.getUId() || o.getUGrade() == UserGrade.ADMIN))
            return ResponseUtil.resultModify(plateService.updatePlate(pId, pName, o.getUId()));
        return ResponseUtil.unauthorized();
    }

    @RequestMapping(value = "/plates", method = RequestMethod.POST)
    public ResponseData addPlate(String token, String plateName) {
        UserResp o = TokenUtil.getUserResp(token);
        if (o == null) return ResponseUtil.unauthorized();

        if (o.getUGrade() == UserGrade.ADMIN)
            return ResponseUtil.resultModify(plateService.addPlate(plateName, -1));
        return ResponseUtil.unauthorized();
    }

    @RequestMapping(value = "/plates", method = RequestMethod.DELETE)
    public ResponseData removePlate(String token, long plateId) {
        UserResp o = TokenUtil.getUserResp(token);
        if (o == null) return ResponseUtil.unauthorized();

        if (o.getUGrade() == UserGrade.ADMIN) {
            return ResponseUtil.resultModify(plateService.deletePlate(plateId));
        }
        return ResponseUtil.unauthorized();
    }

    @RequestMapping(value = "/plates", method = RequestMethod.GET)
    public ResponseData queryAll() {
        return ResponseUtil.success(plateService.queryAllPlates());
    }

}

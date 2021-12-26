package forum.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import forum.constant.Cache;
import forum.constant.UserGrade;
import forum.pojo.Plate;
import forum.pojo.req.UserReq;
import forum.pojo.resp.ResponseData;
import forum.pojo.resp.UserResp;
import forum.service.PlateService;
import forum.service.UserService;
import forum.util.ResponseUtil;
import forum.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PlateService plateService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(String username, String password) {
        UserResp user = userService.getUserByLogin(username, password);
        if (user != null) {
            if (user.isAvailable()) {
                String s = TokenUtil.saveToken(user, password);
                return ResponseUtil.success(new String[]{"token", "user"}, new Object[]{s, user});
            }
            return ResponseUtil.fail("该账号已封禁", null);
        }
        return ResponseUtil.fail("用户名或密码错误", null);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseData queryAll(String token,
                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        UserResp user = (UserResp) Cache.get(token);
        if (user == null || user.getUGrade() != UserGrade.ADMIN)
            return ResponseUtil.unauthorized();

        PageHelper.startPage(pageNum, pageSize);
        List<UserResp> userResps = userService.queryAll();
        return ResponseUtil.success(new PageInfo<>(userResps));
    }

    @RequestMapping(value = "/users/available", method = RequestMethod.GET)
    public ResponseData updateUserAvailable(String token, boolean available, long uId) {
        UserResp user = TokenUtil.getUserResp(token);
        if (user == null || user.getUGrade() != UserGrade.ADMIN)
            return ResponseUtil.unauthorized();
        return ResponseUtil.resultModify(userService.updateUserAvailable(uId, available));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseData getUser(String token) {
        System.out.println(token);
        return TokenUtil.checkToken(token);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseData logout(String token) {
        return ResponseUtil.success(TokenUtil.removeToken(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseData register(String username, String password) {
        if (userService.getUserByName(username) != null)
            return ResponseUtil.fail("注册失败，用户名已存在", 0);

        int resp = userService.addUser(username, password);
        if (resp == 0) return ResponseUtil.fail("注册失败", 0);
        return ResponseUtil.success(resp);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PATCH)
    public ResponseData update(String token, String uName, int uGender, long uBirthday, String avatar,
                               @RequestParam(value = "uPass", defaultValue = "") String uPass) {
        System.out.println("前端日期格式：" + uBirthday);
        UserResp userResp = TokenUtil.getUserResp(token);
        if (userResp == null)
            return ResponseUtil.unauthorized();
        if (uName.equals("")) return ResponseUtil.fail("用户名不能为空", null);

        if (uPass != null && uPass.equals("")) uPass = null;
        if (avatar != null && avatar.equals("")) avatar = null;

        if (!((UserResp) Cache.get(token)).getUName().equals(uName) &&
                userService.getUserByName(uName) != null) {
            return ResponseUtil.fail("修改失败，用户名已存在", null);
        }

        UserReq user = new UserReq();
        user.setUName(uName);
        user.setAvatar(avatar);
        user.setUBirthday(new Date(uBirthday));
        user.setUGender(uGender);
        user.setUPass(uPass);
        user.setUId(userResp.getUId());
        System.out.println(user);

        if (uBirthday != 0) userResp.setUBirthday(new Timestamp(uBirthday));
        userResp.setUName(uName);
        userResp.setAvatar(avatar);
        userResp.setUGender((byte) uGender);

        return ResponseUtil.resultModify(userService.updateUserByUserReq(user));
    }

    @RequestMapping(value = "/users/{uid}", method = RequestMethod.GET)
    public ResponseData query(@PathVariable long uid) {
        return ResponseUtil.success(userService.getUserById(uid));
    }

    @RequestMapping(value = "/users/grade", method = RequestMethod.PATCH)
    public ResponseData updateUserGrade(String token, long targetUId, int grade,
                                        @RequestParam(value = "plateId", defaultValue = "0") int plateId) {
        UserResp userResp = TokenUtil.getUserResp(token);
        if (userResp == null || userResp.getUGrade() != UserGrade.ADMIN || grade > UserGrade.MODERATOR)
            return ResponseUtil.unauthorized();

        if (grade == UserGrade.MODERATOR) {
            Plate plate = plateService.getPlateById(plateId);
            if (plate != null) {
                UserResp user = userService.getUserById(plate.getUId());
                if (user != null) return ResponseUtil.fail("已经有版主了，如要添加请先移除原版主ID：" + user.getUId(), null);
            }
            int i = plateService.updatePlateManager(plateId, targetUId);
            if (i == 0) return ResponseUtil.resultModify(i);
        }
        if (grade == UserGrade.USER) {
            List<Plate> plates = plateService.getPlateByUId(targetUId);
            for (int i = 0; i < plates.size(); i++) {
                plateService.updatePlateManager(plates.get(i).getPId(), -1);
            }
        }
        return ResponseUtil.resultModify(userService.updateUserGrade(targetUId, grade));
    }

    @RequestMapping(value = "/users/plate", method = RequestMethod.PATCH)
    public ResponseData removeUserPlate(String token, int plateId) {
        UserResp user = TokenUtil.getUserResp(token);
        if (user == null || user.getUGrade() != UserGrade.ADMIN)
            return ResponseUtil.unauthorized();

        return ResponseUtil.resultModify(plateService.updatePlateManager(plateId, -1));
    }
}

package forum.util;

import forum.constant.Cache;
import forum.pojo.resp.ResponseData;
import forum.pojo.resp.UserResp;
import org.springframework.util.DigestUtils;

public class TokenUtil {
    public static String getToken(UserResp user, String password) {
        int hashCode = user.hashCode();
        long timeMillis = System.currentTimeMillis();
        return DigestUtils.md5DigestAsHex(String.format("%d%s%d", hashCode, password, timeMillis).getBytes());
    }

    public static String saveToken(UserResp user, String password) {
        String token = getToken(user, password);
        Cache.put(token, user);
        return token;
    }

    public static boolean removeToken(String token) {
        Cache.remove(token);
        return true;
    }

    public static ResponseData checkToken(String token) {
        UserResp user = (UserResp) Cache.get(token);
        if (user == null) ResponseUtil.fail(null);
        return ResponseUtil.success(user);
    }

    public static UserResp getUserResp(String token) {
        return (UserResp) Cache.get(token);
    }

    public static boolean isValidToken(String token, long uId) {
        UserResp user = (UserResp) Cache.get(token);
        return user != null && user.getUId() == uId;
    }
}

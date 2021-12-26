package forum.util;

import forum.pojo.resp.ResponseData;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static final int SUCCESS_CODE = 600;
    public static final int FAIL_CODE = 601;
    public static final int LOGIN_FAILED_CODE = 602;
    public static final int UNAUTHORIZED_CODE = 603;

    public static ResponseData resultModify(int i) {
        Map<String, Integer> map = new HashMap<>();
        map.put("affectedRows", i);
        if (i == 0) return fail(map);
        return success(map);
    }

    /**
     * 成功
     */
    public static ResponseData success(Object data) {
        return success("成功", data);
    }

    public static ResponseData success(String[] keys, Object[] values) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return success(map);
    }

    public static ResponseData success(String msg, Object data) {
        ResponseData success = new ResponseData();
        success.setCode(SUCCESS_CODE);
        success.setData(data);
        success.setMsg(msg);
        return success;
    }

    /**
     * 失败
     */
    public static ResponseData fail(Object data) {
        return fail("失败", data);
    }

    public static ResponseData fail() {
        return fail(null);
    }

    public static ResponseData fail(String msg, Object data) {
        ResponseData resp = new ResponseData();
        resp.setCode(FAIL_CODE);
        resp.setData(data);
        resp.setMsg(msg);
        return resp;
    }

    /**
     * 未授权
     */
    public static ResponseData unauthorized() {
        return new ResponseData(UNAUTHORIZED_CODE, "未授权");
    }

    public static ResponseData getResponse(int code, String msg, Object data) {
        ResponseData resp = new ResponseData();
        resp.setCode(code);
        resp.setMsg(msg);
        resp.setData(data);
        return resp;
    }
}

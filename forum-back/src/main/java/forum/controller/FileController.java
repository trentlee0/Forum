package forum.controller;

import forum.pojo.resp.ResponseData;
import forum.resolver.MyCommonsMultipartResolver;
import forum.util.ResponseUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RestController
public class FileController {

    public static final String UPLOAD_DIR = "/upload";
    public static final String WEBSITE_URL = "http://localhost:8081/GCUForum";
    public static final String FILE_URL = WEBSITE_URL + "/file";

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseData upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.equals("")) return ResponseUtil.fail("文件名为空");
        if (file.getSize() > MyCommonsMultipartResolver.MAX_FILE_SIZE) return ResponseUtil.fail("上传文件不超过50MB");

        String saveFilePath = getSaveFilePath(request.getServletContext().getRealPath(UPLOAD_DIR), originalFilename);
        System.out.println("上传到服务器的路径为：" + saveFilePath);
        try {
            file.transferTo(new File(saveFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.fail(e.getCause());
        }
        return ResponseUtil.success("上传成功", null);
    }

    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public ResponseData uploadAvatar(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.equals("")) return ResponseUtil.fail("文件名为空");
        if (file.getSize() > 5 * 1024 * 1024) return ResponseUtil.fail("上传文件不超过5MB");

        String saveFilePath = getSaveFilePath(request.getServletContext().getRealPath(UPLOAD_DIR), originalFilename);
        try {
            file.transferTo(new File(saveFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.fail(e.getCause());
        }

        return ResponseUtil.success(FILE_URL + getRespFilename(saveFilePath));
    }

    @RequestMapping(value = "/uploadImage")
    public Object uploadImage(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        boolean error = false;
        Map<String, Object> res = new HashMap<>();
        List<Map<String, String>> maps = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.equals("")) {
            error = true;
            res.put("msg", "文件名为空");
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            error = true;
            res.put("msg", "上传文件不超过10MB");
        }

        if (!error) {
            String saveFilePath = getSaveFilePath(request.getServletContext().getRealPath(UPLOAD_DIR), originalFilename);
            try {
                file.transferTo(new File(saveFilePath));
                String s = FILE_URL + getRespFilename(saveFilePath);
                map.put("url", s);
                map.put("alt", originalFilename);
                map.put("href", s);
                maps.add(map);

                res.put("errno", 0);
                res.put("data", maps);
            } catch (IOException e) {
                e.printStackTrace();
                res.put("errno", 1);
                res.put("msg", e.getCause());
            }
        } else {
            res.put("errno", 1);
        }

        return res;
    }

    @RequestMapping(value = "/uploadVideo")
    public Object uploadVideo(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        boolean error = false;
        Map<String, Object> res = new HashMap<>();

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.equals("")) {
            error = true;
            res.put("msg", "文件名为空");
        }

        if (file.getSize() > 50 * 1024 * 1024) {
            error = true;
            res.put("msg", "上传文件不超过50MB");
        }

        if (!error) {
            String saveFilePath = getSaveFilePath(request.getServletContext().getRealPath(UPLOAD_DIR), originalFilename);
            try {
                file.transferTo(new File(saveFilePath));
                String s = FILE_URL + getRespFilename(saveFilePath);
                Map<String, String> map = new HashMap<>();
                map.put("url", s);
                res.put("data", map);
                res.put("errno", 0);
            } catch (IOException e) {
                e.printStackTrace();
                res.put("errno", 1);
            }
        } else {
            res.put("errno", 1);
        }

        return res;
    }

    @RequestMapping("/download/{filename}")
    public ResponseData download(HttpServletResponse response, HttpServletRequest request, @PathVariable String filename) {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");

        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "UTF-8"));
            responseFile(response, request, filename);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.fail(e.getCause());
        }

        return ResponseUtil.success(null);
    }

    @RequestMapping("/file/{filename}")
    public ResponseData responseImage(HttpServletResponse response, HttpServletRequest request, @PathVariable String filename) {
        try {
            responseFile(response, request, filename);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.fail(e.getCause());
        }

        return ResponseUtil.success(null);
    }

    private String getRespFilename(String saveFilePath) {
        int i = saveFilePath.lastIndexOf('/');
        return saveFilePath.substring(i);
    }

    public void responseFile(HttpServletResponse response, HttpServletRequest request, String filename) throws IOException {
        String dirPath = request.getServletContext().getRealPath(UPLOAD_DIR);
        File file = new File(dirPath, filename);
        transfer(new FileInputStream(file), response.getOutputStream());
        System.out.println(file.getPath());
    }

    public void transfer(InputStream inputStream, OutputStream outputStream) throws IOException {
        int len;
        byte[] buffer = new byte[1024];
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
            outputStream.flush();
        }
        outputStream.close();
        inputStream.close();
    }

    public String getSaveFilePath(String saveDirPath, String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        int i = filename.lastIndexOf('.');
        String prefix = filename.substring(0, i);
        String suffix = filename.substring(i);
        File saveFilePath = new File(saveDirPath, uuid + suffix);

        return saveFilePath.getPath();
    }
}

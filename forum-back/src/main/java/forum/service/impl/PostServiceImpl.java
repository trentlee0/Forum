package forum.service.impl;

import forum.dao.PostMapper;
import forum.pojo.Post;
import forum.pojo.resp.PostResp;
import forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public PostResp getPost(long postId) {
        return postMapper.getPost(postId);
    }

    @Override
    public long deleteByPId(long postId) {
        return postMapper.deleteByPId(postId);
    }

    @Override
    public int updatePost(long postId, String postName, String content, boolean recycle, Timestamp updateDatetime) {
        return postMapper.updatePost(postId, postName, content, recycle, updateDatetime);
    }

    @Override
    public int addPost(long uId, long plateId, String postName, String content, Timestamp createDatetime) {
        return postMapper.addPost(uId, plateId, postName, content, createDatetime);
    }

    @Override
    public List<PostResp> queryAllPosts() {
        return postMapper.queryAllPosts();
    }

    @Override
    public List<PostResp> queryPostsByUId(long uId) {
        return postMapper.queryPostsByUId(uId);
    }

    @Override
    public List<PostResp> queryPostsByPId(long plateId) {
        return postMapper.queryPostsByPId(plateId);
    }

    @Override
    public long getCountByPId(long plateId) {
        return postMapper.getCountByPId(plateId);
    }

    @Override
    public List<PostResp> search(String postName) {
        return postMapper.search(postName);
    }
}

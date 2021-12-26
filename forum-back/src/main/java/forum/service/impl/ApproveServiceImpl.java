package forum.service.impl;

import forum.dao.ApproveMapper;
import forum.pojo.Approve;
import forum.service.ApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApproveServiceImpl implements ApproveService {
    @Autowired
    private ApproveMapper approveMapper;

    @Override
    public Approve queryByUId(long uId, long postId) {
        return approveMapper.queryByUId(uId, postId);
    }

    @Override
    public long addByUId(long uId, long postId) {
        return approveMapper.addByUId(uId, postId);
    }

    @Override
    public long countByPostId(long postId) {
        return approveMapper.countByPostId(postId);
    }
}

package forum.service.impl;

import forum.dao.ApplyMapper;
import forum.pojo.Apply;
import forum.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public List<Apply> queryByUId(long uId) {
        return applyMapper.queryByUId(uId);
    }

    @Override
    public int addApply(long uId, String text) {
        return applyMapper.addApply(uId, text);
    }

    @Override
    public int updateDispose(long aId, boolean dispose) {
        return applyMapper.updateDispose(aId, dispose);
    }

    @Override
    public List<Apply> queryAll() {
        return applyMapper.queryAll();
    }
}

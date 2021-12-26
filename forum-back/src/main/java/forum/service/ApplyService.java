package forum.service;

import forum.pojo.Apply;

import java.util.List;

public interface ApplyService {
    List<Apply> queryByUId(long uId);

    int addApply(long uId, String text);

    int updateDispose(long aId, boolean dispose);

    List<Apply> queryAll();
}

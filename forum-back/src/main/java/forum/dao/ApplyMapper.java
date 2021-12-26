package forum.dao;

import forum.pojo.Apply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyMapper {
    List<Apply> queryByUId(long uId);

    int addApply(@Param("uId") long uId, @Param("text") String text);

    int updateDispose(@Param("aId") long aId, @Param("dispose") boolean dispose);

    List<Apply> queryAll();
}

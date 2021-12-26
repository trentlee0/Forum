package forum.dao;

import forum.pojo.Plate;
import forum.pojo.resp.PlateResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlateMapper {
    Plate getPlateById(long pId);

    List<Plate> getPlateByUId(@Param("uId") long uId);

    int addPlate(@Param("pName") String pName, @Param("uId") long uId);

    int deletePlate(@Param("plateId") long plateId);

    int updatePlate(@Param("pId") long pId, @Param("pName") String pName, @Param("uId") long uId);

    List<PlateResp> queryAllPlates();
}

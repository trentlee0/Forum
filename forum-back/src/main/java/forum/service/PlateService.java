package forum.service;

import forum.pojo.Plate;
import forum.pojo.resp.PlateResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlateService {
    Plate getPlateById(long pId);

    List<Plate> getPlateByUId(long uId);

    int addPlate(String pName, long uId);

    int deletePlate(long plateId);

    int updatePlate(long pId, String pName, long uId);

    int updatePlateManager(long pId, long uId);

    int updatePlateName(long pId, String pName);

    List<PlateResp> queryAllPlates();
}

package forum.service.impl;

import forum.dao.PlateMapper;
import forum.pojo.Plate;
import forum.pojo.resp.PlateResp;
import forum.service.PlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlateServiceImpl implements PlateService {

    @Autowired
    private PlateMapper plateMapper;

    @Override
    public Plate getPlateById(long pId) {
        return plateMapper.getPlateById(pId);
    }

    @Override
    public List<Plate> getPlateByUId(long uId) {
        return plateMapper.getPlateByUId(uId);
    }

    @Override
    public int addPlate(String pName, long uId) {
        return plateMapper.addPlate(pName, uId);
    }

    @Override
    public int deletePlate(long plateId) {
        return plateMapper.deletePlate(plateId);
    }

    @Override
    public int updatePlate(long pId, String pName, long uId) {
        return plateMapper.updatePlate(pId, pName, uId);
    }

    @Override
    public int updatePlateManager(long pId, long uId) {
        Plate plate = getPlateById(pId);
        return plateMapper.updatePlate(pId, plate.getPName(), uId);
    }

    @Override
    public int updatePlateName(long pId, String pName) {
        Plate plate = getPlateById(pId);
        return plateMapper.updatePlate(pId, pName, plate.getUId());
    }

    @Override
    public List<PlateResp> queryAllPlates() {
        return plateMapper.queryAllPlates();
    }
}

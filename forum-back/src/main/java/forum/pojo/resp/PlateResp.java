package forum.pojo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class PlateResp {
    private long pId;
    private long uId;
    private String pName;
    private long count;
}

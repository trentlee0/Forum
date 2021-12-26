package forum.pojo.req;

import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@ToString
public class UserReq {
    private long uId;
    private String uName;
    private String uPass;
    private long uGender;
    private Date uBirthday;
    private String avatar;
}

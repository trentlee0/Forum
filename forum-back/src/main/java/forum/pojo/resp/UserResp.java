package forum.pojo.resp;

import lombok.*;

import java.sql.Timestamp;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class UserResp {
    private long uId;
    private String uName;
    private byte uGrade;
    private byte uGender;
    private Timestamp uBirthday;
    private String avatar;
    private boolean available;
}

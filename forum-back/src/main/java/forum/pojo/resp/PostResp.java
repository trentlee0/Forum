package forum.pojo.resp;

import forum.pojo.Plate;
import forum.pojo.User;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostResp {
    private long postId;
    private Plate plate;
    private UserResp user;
    private String postName;
    private Timestamp createDatetime;
    private boolean recycle;
    private String content;
    private Timestamp updateDatetime;
}

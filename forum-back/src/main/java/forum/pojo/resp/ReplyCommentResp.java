package forum.pojo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCommentResp {
    private long rcId;
    private String text;
    private long uId;
    private UserResp user;
    private Timestamp publishDatetime;
}

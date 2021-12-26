package forum.pojo.resp;

import forum.pojo.ReplyComment;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResp {
    private long cId;
    private String text;
    private long postId;
    private UserResp user;
    private Timestamp publishDatetime;
    private List<ReplyComment> replyComments;
}

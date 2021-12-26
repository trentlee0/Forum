package forum.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private long cId;
    private String text;
    private long postId;
    private long uId;
    private Timestamp publishDatetime;
    private List<ReplyComment> replyComments;

    public List<ReplyComment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<ReplyComment> replyComments) {
        this.replyComments = replyComments;
    }


    public long getCId() {
        return cId;
    }

    public void setCId(long cId) {
        this.cId = cId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }


    public long getUId() {
        return uId;
    }

    public void setUId(long uId) {
        this.uId = uId;
    }

    public Timestamp getPublishDatetime() {
        return publishDatetime;
    }

    public void setPublishDatetime(Timestamp publishDatetime) {
        this.publishDatetime = publishDatetime;
    }
}

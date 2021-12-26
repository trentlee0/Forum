package forum.service;

import forum.pojo.Approve;

public interface ApproveService {
    Approve queryByUId(long uId, long postId);

    long addByUId(long uId, long postId);

    long countByPostId(long postId);
}

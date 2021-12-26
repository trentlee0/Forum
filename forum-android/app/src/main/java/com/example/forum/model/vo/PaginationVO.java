package com.example.forum.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PaginationVO {
    private Integer total;
    private Integer pageNum;
    private Integer pageSize;
    private Integer nextPage;
    private Boolean hasNextPage;
}

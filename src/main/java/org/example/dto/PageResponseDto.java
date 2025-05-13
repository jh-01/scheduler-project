package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResponseDto<T> {
    private List<T> data;
    private PageInfo pageInfo;

    @Getter
    @AllArgsConstructor
    public static class PageInfo{
        private int page;
        private int size;
        private long totalCount;
        private boolean hasPrev;
        private boolean hasNext;
    }
}

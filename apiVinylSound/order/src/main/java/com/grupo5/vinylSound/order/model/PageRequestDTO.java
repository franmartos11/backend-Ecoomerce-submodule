package com.grupo5.vinylSound.order.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@Setter
public class PageRequestDTO {
    private Integer page = 0;
    private Integer size = 5;
    private Sort.Direction sort = Sort.Direction.ASC;
    private String sortByColumn = "id";

    public PageRequestDTO(Integer page, Integer size, Sort.Direction sort, String sortByColumn) {
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.sortByColumn = sortByColumn;
    }

    public PageRequestDTO() {
    }

    public Pageable getPageable(PageRequestDTO dto){
        var page = Objects.nonNull(dto.getPage()) ? dto.getPage():this.page;
        var size = Objects.nonNull(dto.getSize()) ? dto.getSize():this.size;
        var sort = Objects.nonNull(dto.getSort()) ? dto.getSort():this.sort;
        var sortByColumn = Objects.nonNull(dto.getSortByColumn()) ? dto.getSortByColumn():this.sortByColumn;

        return PageRequest.of(page,size,sort,sortByColumn);
    }
}

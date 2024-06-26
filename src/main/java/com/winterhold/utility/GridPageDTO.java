package com.winterhold.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GridPageDTO {
    private List<Object> grid;
    private Integer page;
    private Integer totalPages;
}

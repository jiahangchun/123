package com.jiahangchun.test.tp.swagger.dto;

import com.jiahangchun.test.tp.swagger.dto.Operation;
import lombok.Data;

@Data
public class PathItem {
    private String summary = null;
    private String description = null;
    private Operation get = null;
    private Operation put = null;
    private Operation post = null;
    private Operation delete = null;
    private Operation options = null;
    private Operation head = null;
    private Operation patch = null;
    private Operation trace = null;
}

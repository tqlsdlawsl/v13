package com.v13.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zsp
 * @Date 2019/6/12
 */
@Data
@AllArgsConstructor
public class ResultBean<T> {

    private String statusCode;
    private T data;
}

package com.zyx.payimooc.form;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CartUpdateForm {
    private Integer quantity;

    private Boolean selected;

}

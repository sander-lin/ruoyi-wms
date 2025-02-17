package com.ruoyi.wms.domain.vo.merchandise;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class File implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String url;
}

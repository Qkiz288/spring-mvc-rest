
package com.kkukielka.api.v1.model;

import lombok.Data;

@Data
public class MetaDTO {
    private Long count;
    private Long limit;
    private String nextUrl;
    private Long page;
    private String previousUrl;
}

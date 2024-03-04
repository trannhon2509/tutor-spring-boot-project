package com.project.tutor.respone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponeDataAuth {
    public String token;
    private String msg;
    private Object data;
}

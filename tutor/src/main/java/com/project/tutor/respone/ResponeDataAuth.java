package com.project.tutor.respone;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class ResponeDataAuth {
    public String token;
    private String msg;
    private Object data;
}

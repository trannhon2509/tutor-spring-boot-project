package com.project.tutor.respone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ResponeData {
    public String jwt;
    private String msg;
    private Object data;
}

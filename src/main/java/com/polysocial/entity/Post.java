package com.polysocial.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Post implements Serializable {

    private String post1;

    private String post2;

    private String post3;

    private String post4;
}

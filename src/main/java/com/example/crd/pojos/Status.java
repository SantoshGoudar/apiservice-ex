package com.example.crd.pojos;

import lombok.Data;

import java.util.List;

@Data
public class Status {
    private String method;
    private List<String> paths;
}
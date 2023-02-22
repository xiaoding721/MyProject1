package com.example.bean;

import lombok.Data;

import java.util.Map;

@Data
public class CCTVSearchRequest {
    private Integer total;

    private Integer totalpage;

//    private Map<Object,Object> timespans;
//
//    private Map<Object,Object> daytimes;
//
//    private Map<Object,Object> channels;

    private RequestData[] list;

    private String flag;

    private String[] pageString;



}


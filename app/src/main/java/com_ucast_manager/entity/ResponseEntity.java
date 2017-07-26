package com_ucast_manager.entity;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by pj on 2016/11/29.
 */

@HttpResponse(parser = ResultParser.class)
public class ResponseEntity {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

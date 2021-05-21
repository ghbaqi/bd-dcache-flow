package com.ppio.bd.dcacheflow;

import lombok.Data;

@Data
public class FlowMsgBean {

    private String dt ;
    private String customId  ;
    private String timestamp;
    private long downloadspeed;
    private long uploadspeed ;

    public FlowMsgBean() {
    }

    public FlowMsgBean(String dt, String customId, String timestamp, long downloadspeed, long uploadspeed) {
        this.dt = dt;
        this.customId = customId;
        this.timestamp = timestamp;
        this.downloadspeed = downloadspeed;
        this.uploadspeed = uploadspeed;
    }
}

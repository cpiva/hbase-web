package com.td.ecrr;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PartyRequest {
    @NotNull
    @Min(1)
    private Integer id;

    public String toString() {
        return "PartyRequest(" + id + ")";
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

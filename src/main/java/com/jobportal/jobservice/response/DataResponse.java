package com.jobportal.jobservice.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse<T> {
    private boolean success;
    private T payload;

    public DataResponse(T payload, boolean success) {
        this.success = success;
        this.payload = payload;
    }
}

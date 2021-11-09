package com.taoche.alertmanage.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import lombok.Data;

/**
 * 建造ResultDto
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResultDto {
    private Integer code;
    private String message;
    private Object data;

    private ResultDto(ResultDtoBuilder builder) {
        this.code = builder.getCode();
        this.message = builder.getMessage();
        this.data = builder.getData();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Data
    public static class ResultDtoBuilder {
        private Integer code;
        private String message;
        private Object data;

        public ResultDtoBuilder() {
        }

        public ResultDtoBuilder buildCode(Integer code) {
            this.code = code;
            return this;
        }

        public ResultDtoBuilder buildMessage(String message) {
            this.message = message;
            return this;
        }

        public ResultDtoBuilder buildData(Object data) {
            this.data = data;
            return this;
        }

        public ResultDto build() {
            return new ResultDto(this);
        }
    }
}

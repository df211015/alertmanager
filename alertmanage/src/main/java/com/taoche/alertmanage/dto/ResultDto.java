package com.taoche.alertmanage.dto;

import lombok.Data;

/**
 * 建造ResultDto
 */
public class ResultDto {
    private Integer code;
    private String message;
    private Object data;

    private ResultDto(ResultDtoBuilder builder) {
        this.code = builder.getCode();
        this.message = builder.getMessage();
        this.data = builder.getData();
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

package com.taoche.alertmanage.service.impl;

import com.taoche.alertmanage.constants.EResCode;
import com.taoche.alertmanage.dto.ResultDto;

import java.sql.ResultSet;

/**
 * 简单工厂模式
 */
public class GenerateResultFactory {
    public static ResultDto generateSuccessResult(Object data) {
        ResultDto build = new ResultDto.ResultDtoBuilder()
                .buildCode(EResCode.SUCCESS.getCode())
                .buildMessage(EResCode.SUCCESS.getDescription())
                .buildData(data)
                .build();
        return build;
    }

    public static ResultDto generateSuccessResultOfMsg(String msg, Object data) {
        ResultDto build = new ResultDto.ResultDtoBuilder()
                .buildCode(EResCode.SUCCESS.getCode())
                .buildMessage(msg)
                .buildData(data)
                .build();
        return build;
    }

    public static ResultDto generateFailureResult(Object data) {
        ResultDto build = new ResultDto.ResultDtoBuilder()
                .buildCode(EResCode.Fail.getCode())
                .buildMessage(EResCode.Fail.getDescription())
                .buildData(data)
                .build();
        return build;
    }

    public static ResultDto generateFailureResultOfMsg(String msg, Object data) {
        ResultDto build = new ResultDto.ResultDtoBuilder()
                .buildCode(EResCode.Fail.getCode())
                .buildMessage(msg)
                .buildData(data)
                .build();
        return build;
    }

    public static ResultDto generateFailureResult(EResCode resCode, Object data) {
        ResultDto build = new ResultDto.ResultDtoBuilder()
                .buildCode(resCode.getCode())
                .buildMessage(resCode.getDescription())
                .buildData(data)
                .build();
        return build;
    }
}

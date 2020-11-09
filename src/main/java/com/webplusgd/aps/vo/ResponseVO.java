package com.webplusgd.aps.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO<T> {
    @Schema(description = "是否成功")
    private Boolean success;
    @Schema(description = "返回信息")
    private String message;
    @Schema(description = "返回数据")
    private T content;

    public static <T> ResponseVO<T> buildSuccess() {
        return new ResponseVO<T>(true,"success");
    }

    public static <T> ResponseVO<T> buildSuccess(@NotNull T content) {
        return new ResponseVO<T>(true,"success",content);

    }

    public static <T> ResponseVO buildFailure(String message) {
        return new ResponseVO(false,message);
    }

    public ResponseVO(boolean status,String mes){
        this.success=status;
        this.message=mes;
    }
}

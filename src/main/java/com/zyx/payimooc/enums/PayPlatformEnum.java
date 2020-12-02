package com.zyx.payimooc.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.Getter;

@Getter
public enum PayPlatformEnum {
//    支付平台:1-支付宝,2-微信
    ALIPAY(1),
    WX(2),
        ;

    Integer code;

    PayPlatformEnum(Integer code) {
        this.code = code;
    }

    public static PayPlatformEnum getByBestPayTypeEnum(BestPayTypeEnum bestPayTypeEnum) {
        String platFormName = bestPayTypeEnum.getPlatform().name();

        for (PayPlatformEnum payPlatformEnum : PayPlatformEnum.values()) {
            if (platFormName.equals(payPlatformEnum.name())){
                return payPlatformEnum;
            }
        }
        throw new RuntimeException("支付平台无法识别: " + platFormName);
    }

}

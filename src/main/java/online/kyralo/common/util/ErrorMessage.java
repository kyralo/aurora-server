package online.kyralo.common.util;

import lombok.Builder;
import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-01-22
 * \* Time: 下午3:52
 * \* Description:服务器传递的错误信息
 * \
 */
@Data
@Builder
public class ErrorMessage {
    private String error;
    private String message;
}

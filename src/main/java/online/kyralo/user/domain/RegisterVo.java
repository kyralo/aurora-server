package online.kyralo.user.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * \* Created with Intellij IDEA.
 * \* @author: wangchen
 * \* Date: 2020-04-22
 * \* Time: 7:48
 * \* Description: TODO
 * \
 */

@Data
@ApiModel(value = "register", description = "注册信息")
public class RegisterVo implements Serializable {

    @ApiModelProperty(value = "用户名", name = "name")
    private String name;

    @Email(message = "请输入正确的邮箱格式")
    @NotEmpty(message = "邮箱不能为空")
    @ApiModelProperty(value = "用户邮箱", name = "mail")
    private String mail;

    @ApiModelProperty(value = "用户密码", name = "password")
    private String password;

    private String code;
}

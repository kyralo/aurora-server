package online.kyralo.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 王宸
 */

@Data
@Table(name = "user")
@ApiModel(value = "user", description = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 3084093237480608675L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户id", name = "id")
    private String id;

    @ApiModelProperty(value = "用户名", name = "name")
    private String name;

    @ApiModelProperty(value = "用户性别", name = "sex")
    private String sex;

    @Email(message = "请输入正确的邮箱格式")
    @ApiModelProperty(value = "用户邮箱", name = "mail")
    private String mail;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "用户密码", name = "password")
    private String password;

    @ApiModelProperty(value = "用户头像", name = "avatarUrl")
    @Column(name = "avatar_url")
    private String avatarUrl;

    @ApiModelProperty(value = "用户个性签名", name = "sign")
    private String sign;

    @ApiModelProperty(value = "用户账号状态", name = "fettle")
    private String fettle;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @Column(name = "create_time")
    private Date createTime;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @Column(name = "update_time")
    private Date updateTime;
}
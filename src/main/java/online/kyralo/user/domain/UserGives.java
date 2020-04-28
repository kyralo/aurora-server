package online.kyralo.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author 王宸
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_gives")
@ApiModel(value = "user_gives", description = "用户点赞")
public class UserGives implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户点赞id", name = "id")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", name = "userId")
    private String userId;

    @Column(name = "target_id")
    @ApiModelProperty(value = "点赞项id", name = "targetId")
    private String targetId;

    @ApiModelProperty(value = "点赞类型(0: 视频,1: 评论)", name = "type")
    private Integer type;

    @Column(name = "action_type")
    @ApiModelProperty(value = "点赞值:[ 0: 点赞, 1: 踩]", name = "actionType")
    private Byte actionType;

    @Column(name = "create_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date createTime;

    @Column(name = "update_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date updateTime;
}
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
@Table(name = "user_comment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "userComment", description = "用户评论")
public class UserComment  implements Serializable {

    private static final long serialVersionUID = 5475342531945285394L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户评论id", name = "id")
    private String id;

    @Column(name = "video_id")
    @ApiModelProperty(value = "视频id", name = "videoId")
    private String videoId;

    @Column(name = "send_id")
    @ApiModelProperty(value = "发送者id", name = "sendId")
    private String sendId;

    @Column(name = "answer_id")
    @ApiModelProperty(value = "要回复的评论id", name = "answerId")
    private String answerId;

    @Column(name = "comment_content")
    @ApiModelProperty(value = "评论内容", name = "commentContent")
    private String commentContent;

    @Column(name = "ancestry_id")
    @ApiModelProperty(value = "祖父评论(一级评论)的id", name = "ancestryd")
    private String ancestryId;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @Column(name = "create_time")
    private Date createTime;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @Column(name = "update_time")
    private Date updateTime;

    @ApiModelProperty(value = "评论点赞数", name = "likes")
    private Integer likes;

    @ApiModelProperty(value = "评论踩数", name = "dislikes")
    private Integer dislikes;

}
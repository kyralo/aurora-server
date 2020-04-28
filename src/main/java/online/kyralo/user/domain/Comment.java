package online.kyralo.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-27
 * \* Time: 0:32
 * \* Year: 2019
 * \
 */

@Data
public class Comment {
    @ApiModelProperty(value = "用户评论id", name = "id")
    private String id;

    @ApiModelProperty(value = "评论点赞数", name = "likes")
    private Integer likes;

    @ApiModelProperty(value = "评论踩数", name = "dislikes")
    private Integer dislikes;

    @ApiModelProperty(value = "视频id", name = "videoId")
    private String videoId;

    @ApiModelProperty(value = "发送者id", name = "sendId")
    private String sendId;

    @ApiModelProperty(value = "发送者头像", name = "sendAvatarUrl")
    private String sendAvatarUrl;

    @ApiModelProperty(value = "发送者昵称", name = "sendName")
    private String sendName;

    @ApiModelProperty(value = "评论内容", name = "commentContent")
    private String commentContent;

    @Column(name = "ancestry_id")
    @ApiModelProperty(value = "祖父评论(一级评论)的id", name = "ancestryId")
    private String ancestryId;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date createTime;

    @ApiModelProperty(value = "被回复的评论id", name = "answerId")
    private String answerId;

    private byte action;

    private List<Comment> level2CommentList;

}

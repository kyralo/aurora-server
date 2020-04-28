package online.kyralo.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-30
 * \* Time: 16:54
 * \* Year: 2019
 * \
 */

@Data
public class GivesForComments {

    @ApiModelProperty(value = "用户点赞id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "用户id", name = "userId")
    private String userId;

    @ApiModelProperty(value = "用户评论id", name = "userCommentId")
    private String userCommentId;

    @ApiModelProperty(value = "视频id", name = "videoId")
    private String videoId;

    @ApiModelProperty(value = "发送者id", name = "sendId")
    private String sendId;

    @ApiModelProperty(value = "要回复的评论id", name = "answerId")
    private String answerId;

    @ApiModelProperty(value = "评论类型", name = "answerId")
    private Integer type;

    @ApiModelProperty(value = "评论内容", name = "commentContent")
    private String commentContent;

    @ApiModelProperty(value = "评论图片", name = "commentImageUrl")
    private String commentImageUrl;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date createTime;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date updateTime;
}

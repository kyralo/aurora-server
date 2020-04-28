package online.kyralo.video.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2020-01-02
 * \* Time: 11:49
 * \* Year: 2020
 * \
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoList implements Serializable {

    private static final long serialVersionUID = -6646956836843362839L;
    @ApiModelProperty(value = "视频id", name = "id")
    private String id;

    @ApiModelProperty(value = "作者id", name = "authorId")
    private String authorId;

    @ApiModelProperty(value = "用户头像", name = "authorAvatar")
    private String authorAvatar;

    @ApiModelProperty(value = "用户名", name = "authorName")
    private String authorName;

    @ApiModelProperty(value = "视频类型", name = "kindId")
    private Integer kindId;

    @ApiModelProperty(value = "视频标题", name = "title")
    private String title;

    @ApiModelProperty(value = "视频介绍", name = "introduction")
    private String introduction;

    @ApiModelProperty(value = "视频封面地址", name = "coverUrl")
    private String coverUrl;

    @ApiModelProperty(value = "视频地址", name = "videoUrl")
    private String videoUrl;

    @ApiModelProperty(value = "视频状态", name = "fettle")
    private String fettle;

    @ApiModelProperty(value = "视频点赞数", name = "likes")
    private Integer likes;

    @ApiModelProperty(value = "视频踩数", name = "dislikes")
    private Integer dislikes;

    @ApiModelProperty(value = "视频收藏数", name = "collections")
    private Integer collections;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date createTime;

    private byte action;

    private boolean collected;
}

package online.kyralo.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
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
public class GivesForVideo implements Serializable {

    @ApiModelProperty(value = "用户点赞id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "用户id", name = "userId")
    private String userId;

    @ApiModelProperty(value = "视频id", name = "videoId")
    private String videoId;

    @ApiModelProperty(value = "作者id", name = "authorId")
    private String authorId;

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

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date createTime;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date updateTime;
}

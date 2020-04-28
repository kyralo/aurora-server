package online.kyralo.video.domain;

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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "video")
@ApiModel(value = "video", description = "视频")
public class Video implements Serializable {

    private static final long serialVersionUID = 3890766032867051637L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "视频id", name = "id")
    private String id;

    @Column(name = "author_id")
    @ApiModelProperty(value = "作者id", name = "authorId")
    private String authorId;

    @Column(name = "kind_id")
    @ApiModelProperty(value = "视频类型", name = "kindId")
    private Integer kindId;

    @ApiModelProperty(value = "视频标题", name = "title")
    private String title;

    @ApiModelProperty(value = "视频介绍", name = "introduction")
    private String introduction;

    @Column(name = "cover_url")
    @ApiModelProperty(value = "视频封面地址", name = "coverUrl")
    private String coverUrl;

    @Column(name = "video_url")
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
    @Column(name = "create_time")
    private Date createTime;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @Column(name = "update_time")
    private Date updateTime;
}
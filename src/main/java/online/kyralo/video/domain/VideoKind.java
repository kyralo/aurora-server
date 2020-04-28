package online.kyralo.video.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author 王宸
 */

@Data
@Table(name = "video_kind")
@ApiModel(value = "videoKind", description = "视频类型")
public class VideoKind implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "视频类型id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "视频类型名", name = "name")
    private String name;

    @ApiModelProperty(value = "视频类型介绍", name = "info")
    private String info;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @Column(name = "create_time")
    private Date createTime;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @Column(name = "update_time")
    private Date updateTime;
}
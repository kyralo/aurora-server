package online.kyralo.search.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import online.kyralo.user.domain.User;
import online.kyralo.video.domain.VideoList;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2020-01-04
 * \* Time: 11:18
 * \* Year: 2020
 * \
 */

@Data
public class Search  implements Serializable {

    @ApiModelProperty(value = "视频搜索列表", name = "videos")
    private Set<VideoList> videos;

    @ApiModelProperty(value = "用户搜索列表", name = "users")
    private List<User> users;
}

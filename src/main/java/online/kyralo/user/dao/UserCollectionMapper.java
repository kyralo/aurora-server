package online.kyralo.user.dao;

import online.kyralo.user.domain.UserCollection;
import online.kyralo.video.domain.VideoList;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @author 王宸
 */
@Repository
public interface UserCollectionMapper extends Mapper<UserCollection> {

    /**
     * 查询所有用户收藏 通过用户id
     * @param userId 用户id
     * @return 所有用户收藏
     */
    List<VideoList> listUserCollectionsById(String userId);

}
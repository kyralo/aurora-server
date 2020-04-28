package online.kyralo.video.dao;

import online.kyralo.video.domain.VideoKind;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


/**
 * @author 王宸
 */
@Repository
public interface VideoKindMapper extends Mapper<VideoKind> {
}
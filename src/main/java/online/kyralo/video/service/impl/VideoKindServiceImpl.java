package online.kyralo.video.service.impl;

import online.kyralo.video.dao.VideoKindMapper;
import online.kyralo.video.domain.VideoKind;
import online.kyralo.video.service.VideoKindService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-25
 * \* Time: 20:55
 * \* Year: 2019
 * \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class VideoKindServiceImpl implements VideoKindService {

    @Resource
    private VideoKindMapper mapper;

    @Override
    public ResponseEntity<?> queryVideoKindById(Integer id) {
        if (id == null){
            return ResponseEntity.badRequest().build();
        }else {
            VideoKind videoKind = mapper.selectByPrimaryKey(id);
            if (videoKind != null){
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }else {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @Override
    public ResponseEntity<?> listVideoKinds() {
        List<VideoKind> videoKinds = mapper.selectAll();
        if (videoKinds.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(videoKinds);
        }
    }

    @Override
    public ResponseEntity<?> insert(VideoKind videoKind) {
        if (mapper.insertSelective(videoKind) > 0){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package online.kyralo.user.service.impl;

import online.kyralo.user.dao.UserCollectionMapper;
import online.kyralo.user.domain.UserCollection;
import online.kyralo.user.service.UserCollectionService;
import online.kyralo.video.domain.VideoList;
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
 * \* Time: 20:53
 * \* Year: 2019
 * \
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserCollectionServiceImpl implements UserCollectionService {

    @Resource
    private UserCollectionMapper mapper;

    @Override
    public ResponseEntity<?> listUserCollectionsById(String userId) {
        if ("".equals(userId)){
            return ResponseEntity.badRequest().build();
        }else {
            List<VideoList> userCollections = mapper.listUserCollectionsById(userId);
            if (!userCollections.isEmpty()){
                return ResponseEntity.ok(userCollections);
            }else {
                return ResponseEntity.notFound().build();
            }
        }

    }

    @Override
    public ResponseEntity<?> insert(UserCollection userCollection) {
        if ("".equals(userCollection.getUserId()) || "".equals(userCollection.getVideoId())){
            return ResponseEntity.badRequest().build();
        }else {
            if(mapper.insertSelective(userCollection) == 1){
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @Override
    public ResponseEntity<?> delete(Integer id, String userId) {
        if (id != null || "".equals(userId)){
            return ResponseEntity.badRequest().build();
        }else {
            UserCollection userCollection = new UserCollection();
            userCollection.setId(id);
            userCollection.setUserId(userId);
            if (mapper.deleteByExample(userCollection) == 1){
                return ResponseEntity.noContent().build();
            }else {
                return ResponseEntity.notFound().build();
            }
        }
    }
}

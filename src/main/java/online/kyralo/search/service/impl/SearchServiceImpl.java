package online.kyralo.search.service.impl;

import online.kyralo.search.domain.Search;
import online.kyralo.search.service.SearchService;
import online.kyralo.user.dao.UserMapper;
import online.kyralo.user.domain.User;
import online.kyralo.video.dao.VideoMapper;
import online.kyralo.video.domain.VideoList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2020-01-04
 * \* Time: 11:22
 * \* Year: 2020
 * \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class SearchServiceImpl implements SearchService {

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseEntity<?> searchMulities(String str) {

        Search search = new Search();

        List<User> users = userMapper.listByName(str);
        Set<VideoList> videos = videoMapper.listByTitleOrIntro(str);

        search.setUsers(users);
        search.setVideos(videos);

        return ResponseEntity.ok(search);
    }
}

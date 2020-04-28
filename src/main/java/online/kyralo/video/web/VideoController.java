package online.kyralo.video.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import online.kyralo.common.util.JwtUtil;
import online.kyralo.video.domain.Video;
import online.kyralo.video.domain.VideoList;
import online.kyralo.video.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;

import java.util.Objects;

import static online.kyralo.common.constants.SecurityConstants.HEADER_STRING;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-25
 * \* Time: 20:57
 * \* Year: 2019
 * \
 */

@RestController
@RequestMapping("/api/v2/video")
@Api("视频信息接口")
public class VideoController {

    @Resource
    private VideoService service;

    @GetMapping("/recommend")
    @ApiOperation(value = "查询推荐视频信息", response = VideoList.class)
    public ResponseEntity<?> recommendVideos(){
        return service.recommendVideos();
    }

    @GetMapping("/hot")
    @ApiOperation(value = "查询热门视频信息", response = VideoList.class)
    public ResponseEntity<?> hotVideos(@RequestParam(defaultValue = "0") Integer start,
                                       @RequestParam(defaultValue = "20") Integer end){
        return service.hotVideos(start, end);
    }

    @GetMapping("/id/{id}")
    @ApiOperation(value = "查询视频信息 通过视频id", response = Video.class)
    public ResponseEntity<?> getById(@PathVariable("id") String id,
                                     @RequestHeader(HEADER_STRING) String token){
        String userId;
        if (token == null || "".equals(token)){
            userId = null;
        }else {
            try {
                userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
            }catch (Exception e){
                userId = null;
            }
        }
        return service.queryVideoById(id, userId);
    }

    @GetMapping("/kind/{kind_id}")
    @ApiOperation(value = "查询视频列表 通过视频类型id", response = Video.class)
    public ResponseEntity<?> listVideosByKind(@PathVariable("kind_id") Integer videoKindId) {
       return service.listVideosByKind(videoKindId);
    }

    @GetMapping
    @ApiOperation(value = "查询视频列表信息", response = Video.class)
    public ResponseEntity<?> listVideos() {
        return service.listVideos();
    }

    @PostMapping
    @Secured("ROLE_USER")
    @ApiOperation(value = "新增视频", response = Video.class)
    public ResponseEntity<?> insert(@RequestBody Video video, @RequestHeader(HEADER_STRING) String token){
        String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
        video.setAuthorId(userId);
        return service.insert(video);
    }

    @PutMapping
    @Secured("ROLE_USER")
    @ApiOperation(value = "更新视频信息 通过视频id", response = Video.class)
    public ResponseEntity<?> update(@RequestBody Video video,
                                    @RequestHeader(HEADER_STRING) String token){
        String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
        video.setAuthorId(userId);
        return service.insert(video);
    }

    @PutMapping("/collection")
    @Secured("ROLE_USER")
    @ApiOperation(value = "更新视频信息 通过视频id", response = Video.class)
    public ResponseEntity<?> videoActionCollection(@NotEmpty(message = "内容不能为空") @RequestParam String videoId,
                                                   @RequestHeader(HEADER_STRING) String token){
        String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
        return service.actionCollection(videoId, userId);
    }

    @PutMapping("/action")
    @Secured("ROLE_USER")
    @ApiOperation(value = "更新视频信息 通过视频id action", response = Video.class)
    public ResponseEntity<?> videoAction(@NotEmpty(message = "内容不能为空") @RequestParam String videoId,
                                             @RequestParam byte action,
                                             @RequestHeader(HEADER_STRING) String token){
        String userId = Objects.requireNonNull(JwtUtil.parseToken(token)).getAudience();
        return service.action(videoId, userId, action);
    }

}

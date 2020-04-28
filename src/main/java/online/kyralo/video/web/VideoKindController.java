package online.kyralo.video.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import online.kyralo.video.domain.VideoKind;
import online.kyralo.video.service.VideoKindService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2019-12-25
 * \* Time: 20:58
 * \* Year: 2019
 * \
 */

@RestController
@RequestMapping("/api/v2/video_kind")
@Api("视频类型接口")
public class VideoKindController {

    @Resource
    private VideoKindService service;

    @GetMapping("/{id}")
    @ApiOperation(value = "查询视频类型 通过视频id", response = VideoKind.class)
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return service.queryVideoKindById(id);
    }

    @GetMapping
    @ApiOperation(value = "查询所有视频类型", response = VideoKind.class)
    public ResponseEntity<?> getAll(){
        return service.listVideoKinds();
    }
}

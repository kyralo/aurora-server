package online.kyralo.search.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import online.kyralo.search.domain.Search;
import online.kyralo.search.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;

/**
 * \* Created with Intellij IDEA.
 * \* @author: WangChen
 * \* Date: 2020-01-04
 * \* Time: 11:22
 * \* Year: 2020
 * \
 */

@RestController
@RequestMapping("/api/v2/search")
@Api("搜索接口")
public class SearchController {

    @Resource
    private SearchService service;

    @GetMapping("/mulities")
    @ApiOperation(value = "获取搜索结果", response = Search.class)
    public ResponseEntity<?> searchMulities(@NotEmpty(message = "内容不能为空") @RequestParam("word") String word){
        return service.searchMulities(word);
    }
}

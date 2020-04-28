package online.kyralo.search.service;

import org.springframework.http.ResponseEntity;

/**
 * @author wangchen
 */
public interface SearchService {

    /**
     * 通过关键字查询
     * @param str 关键字
     * @return 查询结果
     */
    ResponseEntity<?> searchMulities(String str);
}

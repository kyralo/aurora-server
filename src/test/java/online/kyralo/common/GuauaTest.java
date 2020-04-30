package online.kyralo.common;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.hash.Hashing;
import online.kyralo.video.domain.Video;
import online.kyralo.video.domain.VideoList;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * \* Created with Intellij IDEA.
 * \* @author: wangchen
 * \* Date: 2020-04-17
 * \* Time: 8:20
 * \
 */
public class GuauaTest {

    @Test
    public void testMap(){
        ArrayListMultimap<String, Object> multimap = ArrayListMultimap.create();
        multimap.put("a", "1");
        multimap.put("b", "2");
        multimap.put("a", "3");

        List<Object> a = multimap.get("a");
        System.out.println();

    }

    @Test
    public void TestBloom(){
        String token = "kyralo";
        System.out.println(Hashing.md5().newHasher().putString(token, Charsets.UTF_8).hash().toString());
    }

    @Test
    public void otherTest(){

        List<Integer> integers = new ArrayList<>(3);
        integers.add(1);
        integers.add(3);
        integers.add(2);

        List<Integer> collectList =
                integers.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(collectList);


    }

}

package com.jiang;


import com.alibaba.fastjson.JSON;
import com.jiang.domain.VideoDO;
import com.jiang.service.VideoService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
public class VideoDocumentTest {
    @Autowired
    private VideoService videoService;

    private RestHighLevelClient client;

    /**
     * 向es中新增文档
     * @throws IOException
     */
    @Test
    void testAddDocument() throws IOException{
        // 1.通过mysql根据id查询影片数据
        VideoDO video = videoService.getById(1736312077116694530L);
        // 2.将Video转换成json
        String json = JSON.toJSONString(video);

        // 1.准备request对香港
        IndexRequest request = new IndexRequest("video").id(video.getId().toString());
        // 2.准备json文档
        request.source(json, XContentType.JSON);
        // 3.发送请求
        client.index(request, RequestOptions.DEFAULT);
    }


    /**
     * 在es中根据id查询文档
     * @throws IOException
     */
    @Test
    void testGetDocumentById() throws IOException {
        // 1.准备requests
        GetRequest request = new GetRequest("video", "1727325724119097346");
        // 2.发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 3.解析响应结果
        String json = response.getSourceAsString();

        VideoDO videoDO = JSON.parseObject(json, VideoDO.class);
        System.out.println(videoDO);
    }

    /**
     * 在es中根据id删除文档
     * @throws IOException
     */
    @Test
    void testDeleteDocument() throws IOException {
        // 1.准备Request
        DeleteRequest request = new DeleteRequest("video", "1727325724119097346");
        // 2.发送请求
        client.delete(request, RequestOptions.DEFAULT);
    }

    @BeforeEach
    void setUp() {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.232.134:9200")
        ));
    }



    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }
}

package com.jiang.service;

import com.jiang.common.Result;
import java.util.HashMap;

/**
 * @author SmlingSea
 */
public interface SearchService {
    Result<HashMap<String, Object>> searchVideo(String token,String key);

    Result<HashMap<String, Object>> searchUser(String token, String key);

    Result<HashMap<String, Object>> getSearchList(String token);
}

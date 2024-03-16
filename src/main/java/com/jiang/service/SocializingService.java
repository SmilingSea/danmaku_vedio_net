package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.domain.SocializingDO;
import com.jiang.domain.UserDO;

import java.util.HashMap;
import java.util.List;

/**
 * @author SmilingSea
 */
public interface SocializingService extends IService<SocializingDO> {
    Result<HashMap<String,Object>> save(String token, UserDO user);

    Result<HashMap<String,Object>> delete(String token,UserDO user);

    Result<List<Long>> getFollows(String token);

    Result<List<Long>> getFans(String token);

    Result<List<Long>> getFriends(String token);
}

package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.domain.SocializingDO;
import com.jiang.domain.UserDO;

import java.util.HashMap;
import java.util.List;

public interface SocializingService extends IService<SocializingDO> {
    public Result<HashMap<String,Object>> save(String token, UserDO user);

    public Result<HashMap<String,Object>> delete(String token,UserDO user);

    public Result<List<Long>> getFollows(String token);

    public Result<List<Long>> getFans(String token);

    public Result<List<Long>> getFriends(String token);
}

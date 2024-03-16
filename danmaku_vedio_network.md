---
title: danmaku_vedio_network
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.22"

---

# danmaku_vedio_network

Base URLs:

* <a href="http://8.138.110.8:8080">aliyun: http://8.138.110.8:8080</a>

# Authentication

# 用户模块

## POST 用户登录

POST /login

> Body Parameters

```yaml
name: jerome
password: "123456"

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|object| no |none|
|» name|body|string| no |用户名|
|» password|body|string| no |密码|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "登录成功！",
  "data": "eyJhbGciOiJIUzI1NiJ9.eyJpc0Jsb2NrZWQiOjAsInVpZCI6MTcyNTgzOTgyNDU2NjUwNTQ3NCwiZXhwIjoxNzAzNzYzNzE1fQ.FDnzugDl1RUXskOq9B_czly8hHLi-77vpmXT08LETe0"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|string|true|none||none|
|» data|string|true|none||none|

## POST 用户注册

POST /user

> Body Parameters

```json
{
  "name": "jerome",
  "password": "123456",
  "phone": "13162626635",
  "email": "x.dmnjvgwav@qq.com"
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|body|body|object| no ||none|
|» name|body|string| yes | 用户名|none|
|» password|body|string| yes | 密码|none|
|» phone|body|string| yes | 手机号|none|
|» email|body|string| yes | 邮箱|none|
|» avatar|body|string| yes | 头像url|none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "注册成功！",
  "data": {
    "id": 1737802974062755800
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|string|true|none||none|
|» data|object|true|none||none|
|»» id|integer(long)|true|none|用户id|none|

## GET 查询用户信息

GET /user/

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|token|header|string| no ||none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "查询成功！",
  "data": {
    "id": 1726923849846722600,
    "name": "jianghaohuan",
    "password": "e10adc3949ba59abbe56e057f20f883e",
    "phone": "13162626635",
    "email": "x.dmnjvgwav@qq.com",
    "profile": null,
    "blocked": 0,
    "identity": "0",
    "avatar": null
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|string|true|none||none|
|» data|object|true|none||none|
|»» id|integer(long)|true|none|用户id|none|
|»» name|string|true|none|用户名|none|
|»» phone|string|true|none|手机号|none|
|»» email|string|true|none|邮箱|none|
|»» profile|string¦null|true|none|头像|none|
|»» blocked|integer|true|none||none|
|»» identity|string|true|none|身份|none|
|»» avatar|string|true|none|头像url|none|

## POST 保存头像

POST /user/avatar

> Body Parameters

```yaml
avatar: file:///home/smilingsea/Pictures/2023-11-20_21-19.png

```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|token|header|string| no ||none|
|body|body|object| no ||none|
|» avatar|body|string(binary)| no ||none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "上传成功",
  "data": {
    "url": "https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/avatar/1737803125271609345.png"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

# 社交模块

## POST 关注

POST /socialising/follow/

> Body Parameters

```json
{
  "id": 1725839824566505500
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|token|header|string| no ||none|
|body|body|object| no ||none|
|» id|body|integer(long)| yes | 用户id|none|
|» avatar|body|string| yes | 头像url|none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "成功关注！",
  "data": {
    "id": 1725839824566505500
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|string|true|none||none|
|» data|object|true|none||none|

## DELETE 取关

DELETE /socialising

> Body Parameters

```json
{
  "id": 1725839824566505500
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|token|header|string| no ||none|
|body|body|object| no ||none|
|» id|body|integer(long)| yes | 用户id|none|
|» avatar|body|string| yes | 头像url|none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "删除成功",
  "data": {
    "id": 1737803283753386000
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|string|true|none||none|
|» data|object|true|none||none|

## GET 查找用户关注列表

GET /socialising/follow

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|token|header|string| no ||none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "查找成功!",
  "data": [
    1725839671734456300,
    1726923849846722600
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|string|true|none||none|
|» data|object|true|none||none|
|»» name|string|true|none|用户名|none|
|»» avatar|string|true|none|头像url|none|

## GET 查找粉丝列表

GET /socialising/fans

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|token|header|string| no ||none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "查找成功!",
  "data": [
    1726923849846722600
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|string|true|none||none|
|» data|[integer]|true|none||none|

## GET 查找朋友

GET /socialising/friends

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|token|header|string| no ||none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "查找成功！",
  "data": [
    1726923849846722600
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

# 评论模块

## POST 添加评论

POST /comment

> Body Parameters

```json
{
  "fatherId": 1727325724119097300,
  "videoId": 1727325724119097300,
  "content": "ad non occaecat"
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|token|header|string| no ||none|
|body|body|object| no ||none|
|» fatherId|body|integer(long)| yes | 父id|none|
|» videoId|body|integer(long)| yes | 视频id|none|
|» content|body|string| yes | 评论内容|none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "评论成功！",
  "data": {
    "id": 1737804952620810200
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

# 视频模块

## POST 添加视频

POST /video

> Body Parameters

```yaml
video: file://C:\Users\24745\Desktop\c56f9eab5f1216729b51a752ce2d4984.mp4
title: 大拇指
type: 喜剧

```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|token|header|string| no ||none|
|body|body|object| no ||none|
|» video|body|string(binary)| no ||none|
|» title|body|string| no ||none|
|» type|body|string| no ||none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "上传视频成功",
  "data": {
    "id": 1737805020493037600
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 点击

POST /video/click

> Body Parameters

```json
{
  "id": 1727325724119097300
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|body|body|object| no ||none|
|» id|body|integer(long)| yes | 视频id|none|
|» usrid|body|integer(long)| yes | 发布人id|none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "点击量+1",
  "data": {
    "id": 1727325724119097300,
    "url": "https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1727325719702495234.mp4"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|string|true|none||none|
|» data|object|true|none||none|
|»» id|integer(long)|true|none|视频id|none|
|»» url|string|true|none|视频url|none|

## GET 视频点击量排行榜

GET /video/rank

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "查询成功",
  "data": {
    "1727325724119097346": "https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1727325719702495234.mp4"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

# 搜索模块

## GET 搜索视频

GET /search/video

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|key|query|string| no ||none|
|token|header|string| no ||none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "查找成功",
  "data": {
    "video": [
      {
        "id": 1,
        "title": "vedio1",
        "url": "https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/55a01d9df426ce4a9d14fec0aeeb8970.mp4",
        "time": "2023-11-22T06:10:54.000+00:00",
        "type": "动作",
        "usrid": 0
      },
      {
        "id": 1727325724119097300,
        "title": "好看的视频",
        "url": "https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1727325719702495234.mp4",
        "time": "2023-11-22T06:10:54.000+00:00",
        "type": "动作",
        "usrid": 0
      },
      {
        "id": 1727328822635921400,
        "title": "好看的视频",
        "url": "https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1727328748249939969.mp4",
        "time": "2023-11-22T06:10:54.000+00:00",
        "type": "动作",
        "usrid": 0
      },
      {
        "id": 1732042010782748700,
        "title": "好看的视频",
        "url": "https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1732042008681402369.mp4",
        "time": "2023-12-05T06:19:30.000+00:00",
        "type": "喜剧",
        "usrid": 1725839824566505500
      },
      {
        "id": 1737805020493037600,
        "title": "好看的视频",
        "url": "https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1737805018559463426.mp4",
        "time": "2023-12-21T11:59:38.000+00:00",
        "type": "喜剧",
        "usrid": 1725839824566505500
      }
    ]
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(long)|true|none|视频id|none|
|» title|string|true|none|视频标题|none|
|» url|string|true|none|视频url|none|
|» time|string(date-time)|true|none|发布时间|none|
|» type|string|true|none|视频类型|none|
|» usrid|integer(long)|true|none|发布人id|none|

## GET 查询用户

GET /search/user

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|key|query|string| no ||none|
|token|header|string| no ||none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "查找成功！",
  "data": {
    "user": [
      {
        "id": 1729906415180603400,
        "name": "feige",
        "password": "e10adc3949ba59abbe56e057f20f883e",
        "phone": "13162626635",
        "email": "x.dmnjvgwav@qq.com",
        "profile": null,
        "blocked": 0,
        "identity": "0",
        "avatar": null
      },
      {
        "id": 1736328554762875000,
        "name": "feigeshuai",
        "password": "e10adc3949ba59abbe56e057f20f883e",
        "phone": "13162626635",
        "email": "x.dmnjvgwav@qq.com",
        "profile": null,
        "blocked": 0,
        "identity": "0",
        "avatar": null
      }
    ]
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|string|true|none||none|
|» data|object|true|none||none|

## GET 搜索记录

GET /search/log

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|token|header|string| no ||none|

> Response Examples

> 成功

```json
{
  "code": 200,
  "msg": "查询成功！",
  "data": {
    "list": [
      "feige",
      "1"
    ]
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

# Data Schema

<h2 id="tocS_SearchDTO">SearchDTO</h2>

<a id="schemasearchdto"></a>
<a id="schema_SearchDTO"></a>
<a id="tocSsearchdto"></a>
<a id="tocssearchdto"></a>

```json
{
  "key": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|key|string|true|none|关键词|none|

<h2 id="tocS_VideoDO">VideoDO</h2>

<a id="schemavideodo"></a>
<a id="schema_VideoDO"></a>
<a id="tocSvideodo"></a>
<a id="tocsvideodo"></a>

```json
{
  "id": 0,
  "title": "string",
  "url": "string",
  "time": "2019-08-24T14:15:22Z",
  "type": "string",
  "usrid": 0
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(long)|true|none|视频id|none|
|title|string|true|none|视频标题|none|
|url|string|true|none|视频url|none|
|time|string(date-time)|true|none|发布时间|none|
|type|string|true|none|视频类型|none|
|usrid|integer(long)|true|none|发布人id|none|

<h2 id="tocS_CommentDO">CommentDO</h2>

<a id="schemacommentdo"></a>
<a id="schema_CommentDO"></a>
<a id="tocScommentdo"></a>
<a id="tocscommentdo"></a>

```json
{
  "id": 0,
  "fatherId": 0,
  "videoId": 0,
  "userId": 0,
  "content": "string",
  "createTime": "2019-08-24T14:15:22Z"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(long)|true|none|评论id|none|
|fatherId|integer(long)|true|none|父id|none|
|videoId|integer(long)|true|none|视频id|none|
|userId|integer(long)|true|none|用户id|none|
|content|string|true|none|评论内容|none|
|createTime|string(date-time)|true|none|创建时间|none|

<h2 id="tocS_Result">Result</h2>

<a id="schemaresult"></a>
<a id="schema_Result"></a>
<a id="tocSresult"></a>
<a id="tocsresult"></a>

```json
{
  "code": 0,
  "msg": "string",
  "data": {}
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|code|integer|true|none||none|
|msg|string|true|none||none|
|data|object|true|none||none|

<h2 id="tocS_UserDO">UserDO</h2>

<a id="schemauserdo"></a>
<a id="schema_UserDO"></a>
<a id="tocSuserdo"></a>
<a id="tocsuserdo"></a>

```json
{
  "id": 0,
  "name": "string",
  "password": "string",
  "phone": "string",
  "email": "string",
  "profile": "string",
  "blocked": true,
  "identity": "string",
  "avatar": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(long)|true|none|用户id|none|
|name|string|true|none|用户名|none|
|password|string|true|none|密码|none|
|phone|string|true|none|手机号|none|
|email|string|true|none|邮箱|none|
|profile|string¦null|true|none|头像|none|
|blocked|boolean|true|none|封禁|none|
|identity|string|true|none|身份|none|
|avatar|string|true|none|头像url|none|


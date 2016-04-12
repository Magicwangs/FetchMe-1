#爬虫介绍
  首先百度和谷歌的爬虫先不介绍了，网络上的资源较多。
  由于项目中需要大量的数据，百度的图片质量实在是太差！！国外较好的一些网站比如 instagram 和 flickr 都是不错的选择。
  此次仅仅从 instagram 进行爬虫的初步分析，以我自己的理解简单的爬虫就如同找规则，那么接下来我们找找 instgram 中的规则吧。
##搜索tags
爬虫第一个目录是爬取相应的tags，这里以Abyssinian为例：

```
https://www.instagram.com/explore/tags/abyssinian/
```

首先爬取上述网站，将会得到一系列图片，其中有各自的跳转链接：

```
{"code":"BD-oDCpgN97","date":1460202797.0,"dimensions":{"width":1080,"height":1080}
```

后面的json数据忽略，接下来我们利用这个code来跳转至下一界面：

```
https://www.instagram.com/p/BD-FO3nIxTo/
```

这就是将code组合将会重定向到下一个链接,对其中的源码进行分析，会发现jpg的url就藏在下面中：

```
<meta property="og:image" content="https://scontent-ord1-1.cdninstagram.com/t51.2885-15/e35/12940148_451747065015792_143344369_n.jpg?ig_cache_key=MTIyNDQzOTE2MDcwODk5NDI4MA%3D%3D.2" />
```

其中的content中的http链接就是我们需要的啦，这样一个简单的爬虫就ok了。

这里补充一点，从```https://www.instagram.com/explore/tags/abyssinian/```中爬到的源文件中匹配code的第12个json数据正是我们所需要的，从中得到（从关键字"code"或者"display_src"搜索即可），之前12个匹配的json解析其display_src即搜索结果，此id为display_src最后提取第12个数据的id使其作为max_id：
```
https://www.instagram.com/explore/tags/abyssinian/?max_id=1224676091381294885
```
得到下一组刷新的数据，以此往复即可。

对于flickr,利用下面的网址：
https://www.flickr.com/photos/tags/Abyssinian/
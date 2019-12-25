

提供一个常见的例子，请求网络列表数据，然后展示出来，可刷新可加载更多

```
class _NewsListPageState extends State<NewsListPage> {
  bool isLogin = false;
  int curPage = 1;
  List newsList;
  ScrollController _controller = ScrollController();

  bool finishLoad = false;

  @override
  void initState() {
    super.initState();
    _controller.addListener(() {
      //当前页面的最大滚动距离
      var maxScroll = _controller.position.maxScrollExtent;
      var pixels = _controller.position.pixels;
      if (maxScroll == pixels) {
      //加载更多
        curPage++;
        getNewsList(true);
      }
    });

  }

  void getNewsList(bool isLoadMore) async {
    DataUtils.isLogin().then((isLogin) {
      if (isLogin) {
        DataUtils.getAccessToken().then((accessToken) {
          if (accessToken == null || accessToken.length == 0) {
            return;
          }
          Map<String, dynamic> params = Map<String, dynamic>();

          params['access_token'] = accessToken;
          params['catalog'] = 1;
          params['page'] = curPage;
          params['pageSize'] = 10;
          params['dataType'] = 'json';

          NetUtils.get(AppUrls.NEWS_LIST, params).then((data) {
            print('NEW_LIST  $data');
            if (data != null && data.isNotEmpty) {
              Map<String, dynamic> map = json.decode(data);
              List _newList = map['newslist'];

              if (!mounted) return;
              setState(() {
                finishLoad = _newList.isEmpty;

                if (isLoadMore) {
                  newsList.addAll(_newList);
                } else {
                  newsList = _newList;
                }
              });
            }
          });
        });
      }
    });
  }

  Future<Null> _pushToRefresh() async {
    curPage = 1;
    getNewsList(false);
    return null;
  }

  @override
  Widget build(BuildContext context) {
    return RefreshIndicator(
      //下拉刷新加载
      onRefresh: _pushToRefresh,
      child: buildListView(),
    );
  }

  Widget buildListView() {
    if (newsList == null) {
      //初始加载
      getNewsList(false);
      return CupertinoActivityIndicator();
    }
    return ListView.builder(
          controller: _controller,
          itemCount: newsList.length + 1,
          itemBuilder: (context, index) {
            if (index == newsList.length) {
              if (finishLoad) {
                return Container(
                    padding: const EdgeInsets.all(15.0),
                    child: Center(child: Text('没有更多数据~')));
              }
              return Container(
                  padding: const EdgeInsets.all(15.0),
                  child: Center(child: CupertinoActivityIndicator()));
            }

            return NewsListItem(
              newList: newsList[index],
              onClickItem: () {});
              },
            );
          }),
  }
}
```
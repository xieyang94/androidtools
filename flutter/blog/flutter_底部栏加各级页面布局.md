类似于Android原生的ViewPager+Fragment+BottomNavigator

```
class _HomePageState extends State<HomePage> {
  //标题
  final _appBarTitle = ['资讯', '动弹', '发现', '我的'];

  //当前页面
  var _currentIndex = 0;

  //底部栏item
  List<NavigationIconView> _navigationIconViews;

  //页面集合
  List<Widget> _pages;

  //页面控制器
  PageController _pageController;

  @override
  void initState() {
    super.initState();
    _navigationIconViews = [
      NavigationIconView(
          title: '资讯',
          iconPath: 'assets/images/ic_nav_news_normal.png',
          activeIconPath: 'assets/images/ic_nav_news_actived.png'),
      NavigationIconView(
          title: '动弹',
          iconPath: 'assets/images/ic_nav_tweet_normal.png',
          activeIconPath: 'assets/images/ic_nav_tweet_actived.png'),
      NavigationIconView(
          title: '发现',
          iconPath: 'assets/images/ic_nav_discover_normal.png',
          activeIconPath: 'assets/images/ic_nav_discover_actived.png'),
      NavigationIconView(
          title: '我的',
          iconPath: 'assets/images/ic_nav_my_normal.png',
          activeIconPath: 'assets/images/ic_nav_my_pressed.png'),
    ];

    _pages = [
      NewsListPage(),
      TweetPage(),
      DiscoveryPage(),
      ProfilePage(),
    ];

    _pageController = PageController(initialPage: _currentIndex);
  }

  @override
  Widget build(BuildContext context) {
    //SafeArea  可以适配刘海屏等异形屏
    return Scaffold(
      appBar: AppBar(
        //去掉阴影
        elevation: 0.0,
        //标题
        title: Text(
          _appBarTitle[_currentIndex],
          //样式
          style: TextStyle(color: Color(AppColors.APPBAR)),
        ),
        //标题icon颜色
        iconTheme: IconThemeData(color: Color(AppColors.APPBAR)),
      ),
      body: PageView.builder(
        //禁止滑动
        physics: NeverScrollableScrollPhysics(),
        itemBuilder: (BuildContext context, int index) {
          return _pages[index];
        },
        controller: _pageController,
        itemCount: _pages.length,
        onPageChanged: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
      ),
      bottomNavigationBar: BottomNavigationBar(
        //当前索引
        currentIndex: _currentIndex,
        items: _navigationIconViews.map((view) => view.item).toList(),
        type: BottomNavigationBarType.fixed,
        onTap: (index) {
          //点击事件-对应底部栏的UI改变
          setState(() {
            _currentIndex = index;
          });
          //对应的页面UI改变
          _pageController.animateToPage(index,
              duration: Duration(microseconds: 1), curve: Curves.ease);
        },
      ),
      drawer: MyDrawer(
          headImgPath: 'assets/images/cover_img.jpg',
          menuTitles: ['发布动弹', '动弹小黑屋', '关于', '设置'],
          menuIcons: [Icons.send, Icons.home, Icons.error, Icons.settings]),
    );
  }
}
```

```
//自定义底部导航栏
class NavigationIconView {
  //item
  final BottomNavigationBarItem item;

  //title
  final String title;

  //icon path
  final String iconPath;

  //active icon path
  final String activeIconPath;

  NavigationIconView(
      {@required this.title,
      @required this.iconPath,
      @required this.activeIconPath})
      : item = BottomNavigationBarItem(
          icon: Image.asset(
            iconPath,
            width: 20.0,
            height: 20.0,
          ),
          activeIcon: Image.asset(
            activeIconPath,
            width: 20.0,
            height: 20.0,
          ),
          title: Text(title),
        );
}
```

```
class MyDrawer extends StatelessWidget {
  //初始化
  //头部图片
  final String headImgPath;

  //下方标题栏
  final List menuTitles;

  //下方标题栏对应的icon
  final List menuIcons;

  MyDrawer(
      {Key key,
      @required this.headImgPath,
      @required this.menuTitles,
      @required this.menuIcons})
      : assert(headImgPath != null),
        assert(menuTitles != null),
        assert(menuIcons != null),
        super(key: key);

  @override
  Widget build(BuildContext context) {
    return Drawer(
      //去掉阴影
      elevation: 0.0,
      child: ListView.separated(
        //解决状态栏问题
        padding: const EdgeInsets.all(0.0),
        itemCount: menuTitles.length + 1,
        itemBuilder: (context, index) {
          if (index == 0) {
            return Image.asset(
              headImgPath,
              fit: BoxFit.cover,
            );
          }
          index -= 1;
          return ListTile(
            leading: Icon(menuIcons[index]),
            title: Text(menuTitles[index]),
            trailing: Icon(Icons.arrow_forward_ios),
            onTap: () {
              switch (index) {
                case 0:
                  _navPush(context, PublishTweetPage());
                  break;
                case 1:
                  _navPush(context, TweetBlackHousePage());
                  break;
                case 2:
                  _navPush(context, AboutPage());
                  break;
                case 3:
                  _navPush(context, SettingPage());
                  break;
              }
            },
          );
        },
        //装饰器 - 分割线
        separatorBuilder: (context, index) {
          if (index == 0) {
            return Divider(
              height: 0.0,
            );
          } else {
            return Divider(
              height: 1.0,
            );
          }
        },
      ),
    );
  }

  _navPush(BuildContext context, Widget page) {
    Navigator.push(context, MaterialPageRoute(builder: (context) => page));
  }
}
```
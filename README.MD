# MocarsLib  SwipeBack
## 在mainfest中配置 activity的主题 style 中加入
```xml
 <item name="android:windowBackground">@android:color/transparent</item>
        <item name="android:colorBackgroundCacheHint">@null</item>
        <item name="android:windowIsTranslucent">true</item>
```
### 第一种方式,直接继承SwipeBackActivity 即可,然后设置setSwipeFinishEnable(..);可控制是否可右滑退出
```java
public class ThreeActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        setSwipeFinishEnable(true);
    }
}
```
## 未完成

### 第二种方式,在activity布局中,将SwipeBackLayout放在根布局
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.mocaris.swipebacklib.weight.SwipeBackLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/sb"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/colorAccent"
    tools:context="com.mocaris.mocarslib.SecondActivity">

    <ListView
        android:id="@+id/lv"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</com.mocaris.swipebacklib.weight.SwipeBackLayout>
```
在代码设置是否可右滑退出,设置右滑监听
```java
   SwipeBackLayout sb = (SwipeBackLayout) findViewById(R.id.sb);
   sb.setSwipeFinishEnable(true);
    sb.setOnSwipeBackListener(listener);
```

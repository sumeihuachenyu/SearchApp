package com.example.lenovo.searchapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.example.lenovo.searchapp.entity.Captcha;
import com.example.lenovo.searchapp.home.model.JoinSearchPerson;
import com.example.lenovo.searchapp.home.model.SearchAndPerson;
import com.example.lenovo.searchapp.person.model.User;

import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lenovo on 2019-03-06.
 */
public class MyApplication extends Application {
    /**
     * 上下文
     */
    private static Context mContent;
    /**
     * MyApplication对象
     */
    private static MyApplication instance;
    /**
     * 存入到Application中便于fragment之间进行传值,存放是是验证码
     */
    private Captcha data;
    /**
     * 利用token进行长时间的登录
     */
    private String token;
    /**
     * 判断是否退出
     */
    private  boolean exit = false;
    /**
     * 点击的是哪个页面
     * 为了方便当修改头像成功之后返回到个人页面可以刷新头像
     */
    private String selectPage;

    /***
     * 判断点击了在首页中的排序和类型哪一个下拉框
     * @return
     */
    private boolean type = true;
    /**
     * 登录者的信息
     */
    private User user;
    /**
     * 存放类型的数组
     */
    private String[] types;
    /**
     * 存放主页中所有的调查数据
     */
    private List<SearchAndPerson> searchs;

    /**
     * 个人发布的所有调查
     */
    private List<SearchAndPerson> personsearchs;
    /**
     * 个人参与的所有调查
     */
    private List<JoinSearchPerson> joinsearchs;

    public List<JoinSearchPerson> getJoinsearchs() {
        return joinsearchs;
    }

    public void setJoinsearchs(List<JoinSearchPerson> joinsearchs) {
        this.joinsearchs = joinsearchs;
    }

    public List<SearchAndPerson> getPersonsearchs() {
        return personsearchs;
    }

    public void setPersonsearchs(List<SearchAndPerson> personsearchs) {
        this.personsearchs = personsearchs;
    }

    public List<SearchAndPerson> getSearchs() {
        return searchs;
    }

    public void setSearchs(List<SearchAndPerson> searchs) {
        this.searchs = searchs;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Captcha getData() {
        return data;
    }

    public void setData(Captcha data) {
        this.data = data;
    }

    public String getSelectPage() {
        return selectPage;
    }

    public void setSelectPage(String selectPage) {
        this.selectPage = selectPage;
    }

    public  boolean isExit() {
        return exit;
    }

    public  void setExit(boolean exit) {
        this.exit = exit;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContent = getApplicationContext();
        //初始化xutils
        x.Ext.init(this);
        //设置为debug模式
        x.Ext.setDebug(false);

        //设置初始值，防止报空指针异常
        setToken(null);
        setUser(null);
        setData(null);
        setSelectPage("home");
        setTypes(null);
        setSearchs(null);
        setPersonsearchs(null);
    }



    public static MyApplication getInstance() {
        if (instance == null) {
            synchronized (MyApplication.class) {
                if (instance == null) {
                    instance = new MyApplication();
                }
            }
        }
        return instance;
    }

    // 运用list来保存们每一个activity是关键
    private List<Activity> mList = new LinkedList<Activity>();

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    // 关闭每一个list内的activity
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void finishActivity() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

//    @Override
//    public LoginResult.UserInfo getUserInfo(String s) {
//        Logger.d(s);
//        return findUserById(s);
//    }


//    private LoginResult.UserInfo findUserById(String s) {
//        final UserInfoResult.ResultBean[] user = {null};
//        Map<String, String> map = new HashMap<>();
//        map.put("userId", s);
//        RequestParams params = new RequestParams(API.GET_USERINFO);
//        try {
//            params.addQueryStringParameter("sign", Utils.getSignature(map, Constants.SECRET));
//            params.addQueryStringParameter("userId", s);
//            x.http().get(params, new Callback.CommonCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    Logger.json(result);
//                    UserInfoResult userInfoResult = Utils.parseJsonWithGson(result, UserInfoResult.class);
//                    if (userInfoResult.getCode() == Constants.HTTP_OK_200) {
//                        user[0] = userInfoResult.getResult();
//                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(user[0].getId(), user[0].getNick(), Uri.parse(user[0].getPortrait())));
//                    }
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    Utils.showShortToast(x.app(), getString(R.string.network_error));
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//                    Utils.showShortToast(x.app(), getString(R.string.network_error));
//                }
//
//                @Override
//                public void onFinished() {
//
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (user[0] != null) {
//            return new UserInfo(user[0].getId(), user[0].getNick(), Uri.parse(user[0].getPortrait()));
//        } else {
//            return null;
//        }
//
//    }


//    @Override
//    public Group getGroupInfo(String groupInfo) {
//        return findGroupInfo(groupInfo);
//    }
//
//    private Group findGroupInfo(String groupInfo) {
//        final Group[] groups = {null};
//        Map<String, String> map = new HashMap<>();
//        map.put("groupId", groupInfo);
//        RequestParams params = new RequestParams(API.GET_GROUNP_INFO);
//        try {
//            params.addQueryStringParameter("sign", Utils.getSignature(map, Constants.SECRET));
//            params.addQueryStringParameter("groupId", groupInfo);
//            x.http().get(params, new Callback.CommonCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    Logger.json(result);
//                    try {
//                        JSONObject jsonObject = new JSONObject(result);
//                        JSONObject resultObject = jsonObject.getJSONObject("result");
//                        Group group = new Group(resultObject.getString("id")
//                                , resultObject.getString("name")
//                                , Uri.parse(resultObject.getString("portrait")));
//                        RongIM.getInstance().refreshGroupInfoCache(group);
//                        groups[0] = group;
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    Utils.showShortToast(x.app(), getString(R.string.network_error));
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//                    Utils.showShortToast(x.app(), getString(R.string.network_error));
//                }
//
//                @Override
//                public void onFinished() {
//
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (groups[0] != null) {
//            return groups[0];
//        } else {
//            return null;
//        }
//    }
}

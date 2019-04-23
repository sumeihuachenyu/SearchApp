package com.example.lenovo.searchapp.search;

import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.searchapp.MainActivityTop;
import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.SpinnerSelectAdress.SelectCategory;
import com.example.lenovo.searchapp.SpinnerSelectAdress.SelectPopupWindow;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.common.Constants;
import com.example.lenovo.searchapp.utils.ActivityCollectorUtil;
import com.example.lenovo.searchapp.utils.TransformUtils;
import com.example.lenovo.searchapp.utils.Utils;
import com.google.gson.internal.LinkedTreeMap;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019-03-29.
 */
@ContentView(R.layout.layout_submit_search)
public class SubmitSearchActivity extends BaseActivity  implements View.OnClickListener{
    private static final int MAX_TITLE = 20 * 2;
    private static final int MAX_QUESTION = 10 * 2;
    private static final int MAX_REMARKS = 50 * 2;
    /**
     * 获取的类型数据
     */
    String typeStr = null;

    @ViewInject(R.id.search_title)
    private EditText searchTitle;
    @ViewInject(R.id.question_one)
    private EditText questionOne;
    @ViewInject(R.id.question_two)
    private EditText questionTwo;
    @ViewInject(R.id.question_three)
    private EditText questtionThree;
    @ViewInject(R.id.search_remarks)
    private EditText remarks;
    /**类型*/
    private TextView tvZuQuyu;
    /**自定义下拉框*/
    private SelectPopupWindow mPopupWindow = null;
    /**自定义下拉框*/

    private String[] parentStrings1 = {"校园生活","娱乐明星","生活琐事","职场生涯","教育问题","生物领域","其他"};
    private String[] parentStrings = null;
    private MyApplication myApplication;
    private ArrayList<LinkedTreeMap> dataBeanListTmp;
    /**
     * 存放类型的数组
     */
    private String[] types = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Utils.hideNavigationBar(this);
       x.view().inject(this);
        //setContentView(R.layout.layout_submit_search);
        tvZuQuyu = findViewById(R.id.tvZuQuyu);
        tvZuQuyu.setOnClickListener(this);
        myApplication = MyApplication.getInstance();
        parentStrings = myApplication.getTypes();

        initTypeData();
    }

    /**
     * 从服务器获取类型数据
     */
    private void initTypeData() {
        Logger.d("myApplication.getTypes()="+myApplication.getTypes());
        if(myApplication.getTypes() == null){
            Map<String,String> map = new HashMap<>();
            map.put("type","type");
            RequestParams params = new RequestParams(API.GET_TYPES);
            try {
                params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
                params.addParameter("type","type");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject baseResult = new JSONObject(result);
                            if (!(baseResult.getInt("status") == Constants.STATUS_OK)){
                                Logger.d("types="+types);
                                Utils.showShortToast(x.app(), baseResult.getString("desc"));
                            }else if(baseResult.getInt("status") == Constants.STATUS_OK){
                                dataBeanListTmp = Utils.parseJsonWithGson(baseResult.getString("data"),ArrayList.class);
                                for(int i=0;i<dataBeanListTmp.size();i++){
                                    LinkedTreeMap map = dataBeanListTmp.get(i);
                                    types[i] = map.get("typename").toString();
                                }
                                Logger.d("dataBeanListTmp="+dataBeanListTmp);
                                Logger.d("data="+baseResult.getString("data"));
                                Logger.d("types="+types);

                                myApplication.setTypes(types);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Utils.showShortToast(SubmitSearchActivity.this, getString(R.string.network_error));
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Utils.showShortToast(SubmitSearchActivity.this, getString(R.string.network_error));
                    }

                    @Override
                    public void onFinished() {

                        if(types == null){
                            parentStrings = types;
                        }else{
                            parentStrings = parentStrings1;
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(types != null){
                parentStrings = types;
            }else{
                parentStrings = parentStrings1;
            }

            Logger.d("((((types="+types+"parentStrings="+parentStrings);
        }else{
            this.parentStrings = myApplication.getTypes();
        }

    }
    /**
     * 跳转到上一页
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.back_search})
    private void itembackhome(View v){
        //this.finish();
        myApplication.setSelectPage("search");
        ActivityCollectorUtil.finishAllActivity();
        Utils.start_Activity(SubmitSearchActivity.this,MainActivityTop.class);
    }

    /**
     * 提交操作
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_search_submit})
    private void submit(View v) throws UnsupportedEncodingException {
        //判断标题是否超过20个汉字
        //判断关键词1是否包含

        if(searchTitle.getText().toString().isEmpty()){
            Utils.showShortToast(SubmitSearchActivity.this,"请输入调查主题");
        }

        if(questionOne.getText().toString().isEmpty()){
            Utils.showShortToast(SubmitSearchActivity.this,"请输入关键词1");
        }else if(!TransformUtils.isPattern(questionOne.getText().toString())){
            Utils.showShortToast(SubmitSearchActivity.this,"输入的关键词只包含字母、数字和汉字");
        }

        if(questionTwo.getText().toString().isEmpty()){
            Utils.showShortToast(SubmitSearchActivity.this,"请输入关键词2");
        }else if(!TransformUtils.isPattern(questionTwo.getText().toString())){
            Utils.showShortToast(SubmitSearchActivity.this,"输入的关键词只包含字母、数字和汉字");
        }

        if(questtionThree.getText().toString().isEmpty()){
            Utils.showShortToast(SubmitSearchActivity.this,"请输入关键词3");
        }else if(!TransformUtils.isPattern(questtionThree.getText().toString())){
            Utils.showShortToast(SubmitSearchActivity.this,"输入的关键词只包含字母、数字和汉字");
        }

        if(typeStr == null){
            Utils.showShortToast(SubmitSearchActivity.this,"请点击选择类型");
        }

        if(islength(searchTitle,MAX_TITLE) && islength(questionOne,MAX_QUESTION) && islength(questionTwo,MAX_QUESTION) && islength(questtionThree,MAX_QUESTION) && islength(remarks,MAX_REMARKS)){
                //需要构造数据
                Map<String,String> map = new HashMap<>();
                map.put("userid",myApplication.getUser().getUserid().toString());
                map.put("searchtitle",searchTitle.getText().toString());
                map.put("searchtype",typeStr);
                map.put("questionone",questionOne.getText().toString());
                map.put("questiontwo",questionTwo.getText().toString());
                map.put("questionthree",questtionThree.getText().toString());
                if(!remarks.getText().toString().isEmpty()){
                    map.put("remarks",remarks.getText().toString());
                }else{
                    map.put("remarks","无值");
                }

                //Utils.start_Activity(ResetActivity.this,LoginActivity.class);//走通逻辑而写
                RequestParams params = new RequestParams(API.INSERT_SEARCH);
                try {
                    params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
                    params.addBodyParameter("userid",myApplication.getUser().getUserid().toString());
                    params.addBodyParameter("searchtitle",searchTitle.getText().toString());
                    params.addBodyParameter("searchtype",typeStr);
                    params.addBodyParameter("questionone",questionOne.getText().toString());
                    params.addBodyParameter("questiontwo",questionTwo.getText().toString());
                    params.addBodyParameter("questionthree",questtionThree.getText().toString());
                    if(!remarks.getText().toString().isEmpty()){
                        params.addBodyParameter("remarks",remarks.getText().toString());
                    }else{
                        params.addBodyParameter("remarks","无值");
                    }


                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject baseResult = new JSONObject(result);
                                if (!(baseResult.getInt("status") == Constants.STATUS_OK)){
                                    Utils.showShortToast(x.app(), baseResult.getString("desc"));
                                }else if(baseResult.getInt("status") == Constants.STATUS_OK){
                                    //需要获取我的调查的全部护具
                                    Utils.showShortToast(x.app(), baseResult.getString("desc"));
                                    //SubmitSearchActivity.this.finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Utils.showShortToast(SubmitSearchActivity.this, getString(R.string.network_error));
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                            Utils.showShortToast(SubmitSearchActivity.this, getString(R.string.network_error));
                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvZuQuyu:
               // if(mPopupWindow == null){
                    Logger.d("parentStrings="+parentStrings);
                    if(parentStrings != null){
                        mPopupWindow = new SelectPopupWindow(parentStrings,SubmitSearchActivity.this,selectCategory);
                        mPopupWindow.showAsDropDown(tvZuQuyu, -5, 10);
                    }
              //  }
                break;
            default:
                break;
        }
    }

    /**
     * 选择完成回调接口
     */
    private SelectCategory selectCategory=new SelectCategory() {

        @Override
        public void selectCategory(int parentSelectposition,boolean type) {
                typeStr=parentStrings[parentSelectposition];
                //Utils.showShortToast(SubmitSearchActivity.this,"点击了类型数据"+typeStr + "，位置为"+parentSelectposition);
        }
    };

    private byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    private String gbToString(byte[] data) {
        String str = null;
        try {
            str = new String(data, "gb2312");
        } catch (UnsupportedEncodingException e) {

        }
        return str;
    }

    public boolean islength(EditText editText,int length) throws UnsupportedEncodingException {
        if(editText.getText().toString().getBytes("gb2312").length > length){
            Utils.showShortToast(SubmitSearchActivity.this,"输入字数超过"+length/2+"个汉字限制");
            int selEndIndex = Selection.getSelectionEnd(editText.getText());
            String str = editText.getText().toString();
            byte[] bt2 = str.getBytes("gb2312");
            byte[] bt3 = subBytes(bt2,0,length);
            String newStrs = gbToString(bt3);
            String temp = String.valueOf(newStrs.charAt(newStrs.length()-1));
            boolean flag = Utils.isContainChinese(temp);
            boolean flag2 = Utils.isContainLetters(temp);
            if(!flag && !flag2 ){
                String newStrs2 = newStrs.substring(0,newStrs.length()-1);
                editText.setText(newStrs2);
            }else {
                editText.setText(newStrs);
            }
            // 新字符串的长度
            int newLen = editText.getText().length();
            // 旧光标位置超过字符串长度
            if(selEndIndex >= newLen){
                selEndIndex = editText.getText().length();
            }
            //设置新光标所在的位置
            Selection.setSelection(editText.getText(), selEndIndex);

            return false;
        }else{
            return true;
        }
    }

}

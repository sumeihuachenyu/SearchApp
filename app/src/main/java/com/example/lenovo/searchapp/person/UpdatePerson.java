package com.example.lenovo.searchapp.person;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.searchapp.MainActivityTop;
import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.common.Constants;
import com.example.lenovo.searchapp.person.model.User;
import com.example.lenovo.searchapp.utils.ActivityCollectorUtil;
import com.example.lenovo.searchapp.utils.PhotoPopupWindow;
import com.example.lenovo.searchapp.utils.TransformUtils;
import com.example.lenovo.searchapp.utils.Utils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019-03-19.
 * 修改个人信息页面
 */
public class UpdatePerson extends BaseActivity{
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_BIG_IMAGE_CUTTING = 3;
    private static final String IMAGE_FILE_NAME = "icon.jpg";

    private ImageView main_icon,back_update;
    /**
     * 定义弹出框
     */
    private PhotoPopupWindow mPhotoPopupWindow;
    private Uri mImageUri;
    private EditText phone;
    private EditText username;
    private MyApplication myApplication;
    private Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_updateperson);
        myApplication = MyApplication.getInstance();
        //初始化手机号和用户名
        phone = findViewById(R.id.et_update_phone);
        username = findViewById(R.id.et_update_username);
        //设置手机号和用户名的值
        phone.setText(myApplication.getUser().getPhone());
        username.setText(myApplication.getUser().getUsername());

        //初始化返回上一页按钮
        back_update =(ImageView) findViewById(R.id.back_update);
        back_update.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {
                   //设置被选中的按钮是person按钮
                   myApplication.setSelectPage("person");
                   //结束掉所有的Activity
                   ActivityCollectorUtil.finishAllActivity();
                   //重新进行app中
                   Utils.start_Activity(UpdatePerson.this,MainActivityTop.class);
               }
        });

        //初始化头像
        main_icon = (ImageView) findViewById(R.id.img_upload_img);
        //初始化更换头像按钮
        Button main_btn = (Button) findViewById(R.id.tx_upload_img);
        //点击头像
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoPopupWindow = new PhotoPopupWindow(UpdatePerson.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 权限申请
                        if (ContextCompat.checkSelfPermission(UpdatePerson.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            //权限还没有授予，需要在这里写申请权限的代码
                            ActivityCompat.requestPermissions(UpdatePerson.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                        } else {
                            // 如果权限已经申请过，直接进行图片选择
                            mPhotoPopupWindow.dismiss();
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            // 判断系统中是否有处理该 Intent 的 Activity
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(intent, REQUEST_IMAGE_GET);
                                Logger.d("mImageUri="+mImageUri);
                            } else {
                                Toast.makeText(UpdatePerson.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 权限申请
                        if (ContextCompat.checkSelfPermission(UpdatePerson.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                || ContextCompat.checkSelfPermission(UpdatePerson.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            // 权限还没有授予，需要在这里写申请权限的代码
                            ActivityCompat.requestPermissions(UpdatePerson.this,
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
                        } else {
                            // 权限已经申请，直接拍照
                            mPhotoPopupWindow.dismiss();
                            imageCapture();
                        }
                    }
                });
                View rootView = LayoutInflater.from(UpdatePerson.this)
                        .inflate(R.layout.activity_main, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        /**
         * 根据数据库中获取的值设置头像
         */
        if(myApplication.getUser().getHeadaddress() != null) {
            String fName = myApplication.getUser().getHeadaddress().trim();
            String filename = fName.substring(fName.lastIndexOf("\\") + 1);
            String path = "/mnt/sdcard/bigIcon/" + filename;
            Logger.d("path=" + path);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            main_icon.setImageBitmap(bitmap);
        }

        //初始化修改按钮
        btn_update  = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //判断手机号是否为空
                  if (phone.getText().toString().isEmpty()) {
                      Utils.showShortToast(UpdatePerson.this, "请输入手机号");
                      return;
                  } else {
                      //判断手机号格式是否正确
                      if (!TransformUtils.isMobile(phone.getText().toString())) {
                          Utils.showShortToast(UpdatePerson.this, "请输入格式正确的手机号");
                          return;
                      }
                  }
                  //判断用户名是否为空
                  if (username.getText().toString().isEmpty()) {
                      Utils.showShortToast(UpdatePerson.this, "请输入用户名");
                      return;
                  } else {
                      //判断用户名格式是否正确
                      if (!TransformUtils.isUsername(username.getText().toString())) {
                          Utils.showShortToast(UpdatePerson.this, "用户名包含非法字符，请重新输入");
                          return;
                      }
                  }

                  //构造签名生成算法所需要的数据
                  Map<String, String> map = new HashMap<>();
                  map.put("userid",myApplication.getUser().getUserid().toString());
                  map.put("mobile", phone.getText().toString());
                  map.put("username", username.getText().toString());
                  //http请求
                  RequestParams params = new RequestParams(API.UPDATE_PERSON);
                  try {
                      params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
                      params.addParameter("userid",myApplication.getUser().getUserid().toString());
                      params.addParameter("mobile", phone.getText().toString());
                      params.addParameter("username", username.getText().toString());
                      x.http().post(params, new Callback.CommonCallback<String>() {
                          @Override
                          public void onSuccess(String result) {
                              try {
                                  JSONObject baseResult = new JSONObject(result);
                                  if (!(baseResult.getInt("status") == Constants.STATUS_OK)) {
                                      //需要提醒再次输入
                                      Utils.showShortToast(x.app(), baseResult.getString("desc"));
                                  } else if (baseResult.getInt("status") == Constants.STATUS_OK) {
                                      User user = Utils.parseJsonWithGson(baseResult.getString("data"), User.class);
                                      myApplication.setUser(user);
                                      //如何回显的问题：还是需要  与头像的类似
                                      myApplication.setSelectPage("person");
                                      ActivityCollectorUtil.finishAllActivity();
                                      Utils.start_Activity(UpdatePerson.this,MainActivityTop.class);
                                  }
                              } catch (JSONException e) {
                                  e.printStackTrace();
                              }
                          }

                          @Override
                          public void onError(Throwable ex, boolean isOnCallback) {
                              Utils.showShortToast(UpdatePerson.this, getString(R.string.network_error));
                          }

                          @Override
                          public void onCancelled(CancelledException cex) {
                              Utils.showShortToast(UpdatePerson.this, getString(R.string.network_error));
                          }

                          @Override
                          public void onFinished() {

                          }
                      });
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
              });
    }

    /**
     * 处理回调结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 小图切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
                // 大图切割
                case REQUEST_BIG_IMAGE_CUTTING:
                    Bitmap bitmap = BitmapFactory.decodeFile(mImageUri.getEncodedPath());
                    main_icon.setImageBitmap(bitmap);

                    Map<String, String> map = new HashMap<>();
                    map.put("userid", myApplication.getUser().getUserid().toString());
                    RequestParams params = new RequestParams(API.UPDATE_PERSON_HEADIMG);
                    params.setMultipart(true);
                    try {
                        params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
                        params.addParameter("userid", myApplication.getUser().getUserid().toString());
                        params.addBodyParameter("file",new File(mImageUri.getEncodedPath()));
                        Logger.d("params="+params);
                        // params=http://10.0.2.2:8080/user/updatePerson?sign=d38bec048c024e414fed4ab61cb975e6
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    JSONObject baseResult = new JSONObject(result);
                                    if (!(baseResult.getInt("status") == Constants.STATUS_OK)) {
                                        //需要提醒再次输入
                                        Utils.showShortToast(x.app(), baseResult.getString("desc"));
                                    } else if (baseResult.getInt("status") == Constants.STATUS_OK) {
                                        User user = Utils.parseJsonWithGson(baseResult.getString("data"), User.class);
                                        myApplication.setUser(user);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Utils.showShortToast(UpdatePerson.this, getString(R.string.network_error));
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                                Utils.showShortToast(UpdatePerson.this, getString(R.string.network_error));
                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                // 相册选取
                case REQUEST_IMAGE_GET:
                    try {
                        // startSmallPhotoZoom(data.getData());
                        startBigPhotoZoom(data.getData());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                // 拍照
                case REQUEST_IMAGE_CAPTURE:
                    File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    // startSmallPhotoZoom(Uri.fromFile(temp));
                    startBigPhotoZoom(temp);
            }
        }
    }

    /**
     * 处理权限回调结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // 判断系统中是否有处理该 Intent 的 Activity
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_GET);
                    } else {
                        Toast.makeText(UpdatePerson.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
            case 300:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    imageCapture();
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
        }
    }

    /**
     * 小图模式切割图片
     * 此方式直接返回截图后的 bitmap，由于内存的限制，返回的图片会比较小
     */
    public void startSmallPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }

    /**
     * 判断系统及拍照
     */
    private void imageCapture() {
        Intent intent;
        Uri pictureUri;
        File pictureFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
        // 判断当前系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pictureUri = FileProvider.getUriForFile(this,
                    "com.chen.lister.testchangeicon.fileProvider", pictureFile);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureUri = Uri.fromFile(pictureFile);
        }
        // 去拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    /**
     * 大图模式切割图片
     * 直接创建一个文件将切割后的图片写入
     */
    public void startBigPhotoZoom(File inputFile) {
        // 创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File(storage + "/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
            this.mImageUri = imageUri; // 将 uri 传出，方便设置到视图中
        }

        // 开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(UpdatePerson.this, inputFile), "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        Logger.d("Bitmap.CompressFormat.JPEG.toString()="+Bitmap.CompressFormat.JPEG.toString());
        Logger.d("imageUri="+imageUri);
        // intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }

    public void startBigPhotoZoom(Uri uri) {
        // 创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File(storage + "/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
            mImageUri = imageUri; // 将 uri 传出，方便设置到视图中
        }

        // 开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }

    public Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    /**
     * 小图模式中，保存图片后，设置到视图中
     * 将图片保存设置到视图中
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File(storage + "/smallIcon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.e("TAG", "文件夹创建失败");
                    } else {
                        Log.e("TAG", "文件夹创建成功");
                    }
                }
                File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
                // 保存图片
                FileOutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 在视图中显示图片
            main_icon.setImageBitmap(photo);
        }
    }
}



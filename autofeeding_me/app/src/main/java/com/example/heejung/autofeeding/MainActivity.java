package com.example.heejung.autofeeding;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageView imageView;
    private String absoultePath;

    final static String TAG = "NetworkAsync";
    String urlAddr = "http://testssjjj.iptime.org:2222/feed";
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) this.findViewById(R.id.imageView);

        Button btn_editPic = (Button) findViewById(R.id.btn_editPic);
        btn_editPic.setOnClickListener(this);

        Button btn_cam = (Button) findViewById(R.id.btn_cam);
        Button btn_feed = (Button) findViewById(R.id.btn_feed);
        Button btn_alarm = (Button) findViewById(R.id.btn_alarm);

        //실시간 동영상 시청 버튼
        btn_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼을 눌렀을때 CameraActivity 로 이동
                Intent intent = new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(intent);
            }
        });

        //알람목록으로이동
        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼을 눌렀을때 CameraActivity 로 이동
                Intent intent = new Intent(getApplicationContext(),MultiAlarm.class);
                startActivity(intent);
            }
        });

        /*
        //수동으로 먹이주기 버튼
        btn_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼을 눌렀을때 FeedActivity 로 이동
                Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                startActivity(intent);
            }
        });
        */

        /*
        //수동으로 먹이주기 버튼
        btn_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    DownloadTask downloadTask = new DownloadTask();
                    downloadTask.execute(urlAddr);
                    Toast.makeText(getBaseContext(), "ddd", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Network is not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */

        //수동으로 먹이주기 버튼
        btn_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.execute(urlAddr);
                //Toast.makeText(getBaseContext(), "ddd", Toast.LENGTH_SHORT).show();
            }
        });

        //급식 시간과 먹이 양 알람 목록 버튼
        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼을 눌렀을때 AlarmActivity 로 이동
                Intent intent = new Intent(getApplicationContext(),MultiAlarm.class);
                startActivity(intent);
            }
        });
    }

    /*
    private boolean isNetworkAvailable() {
        boolean available = false;
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isAvailable())
            available = true;
        return available;
    }
    */


    private String downloadUrl(String strUrl) throws IOException {
        String s = null;
        byte[] buffer = new byte[1000];
        InputStream iStream = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            iStream.read(buffer);
            s = new String(buffer);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            iStream.close();
        }
        return s;
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        String s = null;

        protected String doInBackground(String... url) {
            try {
                s = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
            return s;
        }

        protected void onPostExecute(String result) {
            if (!result.equals("1")) {
                Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "Fail!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    //*****프로필 설정*****//
    //카메라에서 사진 촬영
    //카메라 촬영 후 이미지 가져오기 -> startActivityForResult 함수를 호출
    public void doTakePhotoAction()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    //앨범에서 이미지 가져오기 -> startActivityForResult 함수를 호출
    public void doTakeAlbumAction()
    {
        //앨범호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK)
            return;

        switch(requestCode)
        {
            case PICK_FROM_ALBUM:
            {
                //이후의 처리가 카메라와 같으므로 break 없이 진행
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA:
            {
                //이미지를 가져혼 이후, 리사이즈할 이미지 크기를 결정
                //이후에 이미지 크롭 어플리케이셔을 호출
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                //CROP 할 이미지를 100*100 크기로 저장
                intent.putExtra("outputX", 100);   //CROP 한 이미지의 X축 크기
                intent.putExtra("outputY", 100);   //CROP 한 이미지의 Y축 크기
                intent.putExtra("aspectX", 1);   //CROP 박스의 X축 비율
                intent.putExtra("aspectY", 1);   //CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);   //CROP_FROM_CAMERA case 문 이동

                break;
            }

            case CROP_FROM_IMAGE:
            {
                //크롭된 이후의 이미지를 넘겨받음
                //이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에 임시파일을 삭제
                if(resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                //CROP된 이미지를 저장하기 위한 FILE경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/SmartWheel/" + System.currentTimeMillis() + ".jpg";

                if(extras != null)
                {
                    Bitmap photo = extras.getParcelable("data");   //CROP 된 BITMAP
                    imageView.setImageBitmap(photo);   //레이아웃의 이미지칸에 CROP 된 BITMAP 을 보여줌

                    storeCropImage(photo, filePath);   //CROP된 이미지를 외부저장소, 앨범에 저장

                    absoultePath = filePath;
                    break;
                }

                //임시파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                doTakePhotoAction();
            }
        };

        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진촬영", cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }

    //Bitmap 을 저장하는 함수
    private void storeCropImage(Bitmap bitmap, String filePath) {
        //SmartWheel 폴더를 생성해 이미지를 저장
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel";
        File directory_SmartWheel = new File(dirPath);

        //SmartWheel 디렉터리가 없을 때 생성(새로 이미지를 저장할 경우가 속함)
        if(!directory_SmartWheel.exists()) {
            directory_SmartWheel.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

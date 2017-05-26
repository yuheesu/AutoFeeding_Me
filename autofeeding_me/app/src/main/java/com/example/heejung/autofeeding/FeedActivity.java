package com.example.heejung.autofeeding;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static android.R.id.message;

public class FeedActivity extends AppCompatActivity {

    //private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        /*
        webView=(WebView)findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);   //자바스크립트 사용 여부
        webView.getSettings().setBuiltInZoomControls(true);   //줌 컨트롤 사용여부
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setSavePassword(true);   //폼 데이터 패스워드 저장여부
        webView.getSettings().setSaveFormData(true);   //폼 데이터 저장여부

        webView.setWebViewClient(new MyWebViewClient());

        webView.loadUrl("http://testssjjj.iptime.org:2222/feed");
        //webView.addJavascriptInterface(new AndroidBridge(), "HybridApp");
        //AndroidBridge는 클래스 이름 "HybridApp"는 html의 function이름


        webView.setWebChromeClient(new WebChromeClient(){
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        */
    }
}

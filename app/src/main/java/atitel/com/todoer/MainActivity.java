package atitel.com.todoer;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private LinearLayout splash;
    private LinearLayout errorLayout;
    private LinearLayout webLayout;

    private boolean Error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.web);
        splash = (LinearLayout) findViewById(R.id.splash);
        errorLayout = (LinearLayout) findViewById(R.id.error);
        Button refresh = (Button) findViewById(R.id.refresh);
        webLayout = (LinearLayout) findViewById(R.id.webLayout);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.getIndeterminateDrawable().setColorFilter(
                Color.BLACK,
                android.graphics.PorterDuff.Mode.SRC_IN);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
                Error = false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){

        });
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.scrollTo((int)view.getX(), ((int)view.getY() + 10));
                splash.setVisibility(View.GONE);

                webLayout.setVisibility(View.VISIBLE);

                if (!Error) {
                    errorLayout.setVisibility(View.GONE);
                }

            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                showError();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError error) {
                showError();
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://todoer.ir/");

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void showError() {
        Error = true;
        splash.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        webLayout.setVisibility(View.GONE);
    }
}
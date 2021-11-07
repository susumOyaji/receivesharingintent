package com.example.receivesharingintent;

import io.flutter.embedding.android.FlutterActivity;

import android.content.Intent;
import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    private var sharedText: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    GeneratedPluginRegistrant.registerWith(this)
    val intent = intent
    val action = intent.action
    val type = intent.type

    // intentとmime typeの判定
    if (Intent.ACTION_SEND == action && type != null) {
      if ("text/plain" == type) {
        handleSendText(intent)
      }
    }

    MethodChannel(flutterView, "app.channel.shared.data").setMethodCallHandler { call, result ->
      if (call.method.contentEquals("getSharedText")) {
        result.success(sharedText)
        sharedText = null
      }
    }
  }

  private fun handleSendText(intent: Intent) {
    // 文字列を取り出してインスタンス変数に格納
    sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
  }

}

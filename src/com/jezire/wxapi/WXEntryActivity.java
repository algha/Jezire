package com.jezire.wxapi;

import com.jezire.App;
import com.jezire.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_activity_wxentry);
		App.Wechat.HandleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		App.Wechat.HandleIntent(getIntent(), this);
	}

	@Override
	public void onReq(BaseReq req) {
		finish();
	}

	@Override
	public void onResp(BaseResp resp) {
		finish();
	}
}
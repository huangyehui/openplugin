package com.mumuxi.openplugin.androidplugin;


import com.example.testactivity.IPlugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BasePuginActivity extends Activity implements IPlugin{

	// 通过隐式调用宿主的ProxyActivity
	public static final String PROXY_VIEW_ACTION = "com.superidea.androidhost.ProxyActivity";
	// 因为插件的Activity没有Context，所以一切与Context的行为都必须通过宿主代理Activity实现！
	protected Activity mProxyActivity;

	@Override
	public void doOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//super.onCreate(savedInstanceState);
		
	}

	@Override
	public void doOnStart() {
		// TODO Auto-generated method stub
		//super.onStart();
	}

	@Override
	public void doOnRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	public void doOnResume() {
		// TODO Auto-generated method stub
		//super.onResume();
	}

	@Override
	public void doOnPause() {
		// TODO Auto-generated method stub
		//super.onPause();
	}

	@Override
	public void doOnStop() {
		// TODO Auto-generated method stub
		//super.onStop();
	}

	@Override
	public void doOnDestroy() {
		// TODO Auto-generated method stub
		//super.onDestroy();
	}

	public void setProxy(Activity proxyActivity) {
		mProxyActivity = proxyActivity;
	}

	@Override
	public void setContentView(int layoutResID) {
		mProxyActivity.setContentView(layoutResID);
	}

	@Override
	public View findViewById(int id) {
		return mProxyActivity.findViewById(id);
	}

	// 插件的startActivity其实就是调用宿主开启另一个ProxyActivity
	public void startActivity(String className) {
		Intent intent = new Intent(PROXY_VIEW_ACTION);
		intent.putExtra("Class", className);
		mProxyActivity.startActivity(intent);
	}

}

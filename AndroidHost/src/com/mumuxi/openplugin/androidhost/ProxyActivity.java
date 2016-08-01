package com.mumuxi.openplugin.androidhost;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.example.testactivity.IPlugin;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import dalvik.system.DexClassLoader;

public class ProxyActivity extends Activity{
	
	private IPlugin mPluginActivity = null;
	private AssetManager mAssetManager;  
	private Resources mResources;       
	private Theme mTheme;
	
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		String strApkPath = this.getExternalCacheDir() + "/AndroidPlugin.apk";
		loadPlugin(strApkPath);
		loadResource(strApkPath);
		mPluginActivity.setProxy(this) ;
		mPluginActivity.doOnCreate(savedInstanceState);
	}
	
	public void onStart(){
		super.onStart();
		mPluginActivity.doOnStart();
	}
	
	public void onResume(){
		super.onResume();
		mPluginActivity.doOnResume();
	}
	
	public void onPause(){
		super.onPause();
		mPluginActivity.doOnPause();
	}
	
	public void onStop(){
		super.onStop();
		mPluginActivity.doOnStop();
	}
	
	public void onDestroy(){
		super.onDestroy();
		mPluginActivity.doOnDestroy();
	}
	
	

	
	private void loadPlugin(String strApkPath){
		if(TextUtils.isEmpty(strApkPath)){
			return;
		}
	
		File file = new File(strApkPath);
		if(!file.exists()){
			Toast.makeText(this, "apk not found", Toast.LENGTH_SHORT).show();
		}
		File optimizedDirectory = this.getDir("dex", Context.MODE_PRIVATE);
		try {
			DexClassLoader mClassLoader = new DexClassLoader(strApkPath, optimizedDirectory.getCanonicalPath(), null, this.getClassLoader());
			Class<?> clazz = mClassLoader.loadClass("com.mumuxi.openplugin.androidplugin.TestActivity");
			Constructor localConstructor = clazz.getConstructor(new Class[] {});
			Object instance = localConstructor.newInstance(new Object[] {});
			mPluginActivity = (IPlugin) instance;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void loadResource(String dexPath){
		if(TextUtils.isEmpty(dexPath)){
			return;
		}
		try{
			AssetManager assetMgr = AssetManager.class.newInstance();
			Method addAssetPath = assetMgr.getClass().getMethod("addAssetPath", String.class);
			addAssetPath.invoke(assetMgr, dexPath);
			mAssetManager = assetMgr;
			Resources superRes = super.getResources();
			mResources = new Resources(assetMgr, superRes.getDisplayMetrics(), superRes.getConfiguration());
			mTheme = mResources.newTheme();
			mTheme.setTo(super.getTheme());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Resources getResources(){
		return mResources == null ? super.getResources() : mResources;
	}
	
	@Override
	public AssetManager getAssets(){
		return mAssetManager == null ? super.getAssets() : mAssetManager;
	}
	
	@Override
	public Theme getTheme(){
		return mTheme == null ? super.getTheme() : mTheme;
	}
}

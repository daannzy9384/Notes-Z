package com.daannzy.notesz;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import com.daannzy.notesz.databinding.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends Activity {
	
	private MainBinding binding;
	private String path = "";
	private String fileName = "";
	private String content = "";
	private String fullPath = "";
	
	private SharedPreferences Darkmode;
	private Intent trocar = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = MainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
			||checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
			} else {
				initializeLogic();
			}
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		Darkmode = getSharedPreferences("darkmode", Activity.MODE_PRIVATE);
		
		binding.edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				FileUtil.writeFile(fullPath, _charSeq);
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		binding.button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (Darkmode.getString("mode", "").equals("dark")) {
					Darkmode.edit().putString("mode", "light ").commit();
				} else {
					Darkmode.edit().putString("mode", "dark").commit();
				}
				trocar.setClass(getApplicationContext(), MainActivity.class);
				startActivity(trocar);
				finish();
			}
		});
	}
	
	private void initializeLogic() {
		path = FileUtil.getExternalStorageDir().concat("/Notes-Z");
		FileUtil.makeDir(path);
		fileName = "Notesz.txt";
		fullPath = path.concat("/".concat(fileName));
		if (FileUtil.isExistFile(fullPath)) {
			content = FileUtil.readFile(fullPath);
			binding.edittext1.setText(content);
		} else {
			
		}
		if (Darkmode.getString("mode", "").equals("dark")) {
			binding.edittext1.setTextColor(0xFFFFFFFF);
			binding.linear1.setBackgroundColor(0xFF000000);
		} else {
			binding.linear1.setBackgroundColor(0xFFFFFFFF);
			binding.edittext1.setTextColor(0xFF000000);
		}
	}
	
}

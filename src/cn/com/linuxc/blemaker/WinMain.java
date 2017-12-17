package cn.com.linuxc.blemaker;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class WinMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win_main);

		final BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter(); // 获取本地蓝牙实例
		final int REQUEST_ENABLE_BT = 1; // 蓝牙使能请求代码

		Switch openSwitch = (Switch) findViewById(R.id.sw_open);

		openSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) { // 是否触发开启
					if (BTAdapter != null) { // 本地支持蓝牙功能
						if (!BTAdapter.isEnabled()) { // 未开启蓝牙则请求开启蓝牙功能
							Log.d("BT", "BTopen");
							Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);		// 弹出系统提示框打开蓝牙
							startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
							BTAdapter.enable();		// 强制打开蓝牙
						}
					} else { // 本地不支持蓝牙功能
						Toast.makeText(getApplicationContext(), "本地蓝牙不可用", Toast.LENGTH_SHORT).show();
						finish();
					}
				} else {		// 触发关闭
					BTAdapter.disable();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.win_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

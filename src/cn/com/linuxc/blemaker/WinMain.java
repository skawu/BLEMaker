package cn.com.linuxc.blemaker;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class WinMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win_main);

		final BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter(); // ��ȡ��������ʵ��
		final int REQUEST_ENABLE_BT = 1; // ����ʹ���������

		/* layout��� */ 
		Switch openSwitch = (Switch) findViewById(R.id.sw_open);
		Button bt_scan = (Button) findViewById(R.id.bt_scan);
		ListView btDeviceList = (ListView) findViewById(R.id.list_bt);
		final ArrayAdapter<String> list_array = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
		
		btDeviceList.setAdapter(list_array);

		// switch ���ز��������ڴ򿪺͹ر�����
		openSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) { // �Ƿ񴥷�����
					if (BTAdapter != null) { // ����֧����������
						if (!BTAdapter.isEnabled()) { // δ������������������������
							Log.d("BT", "BTopen");
							Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); // ����ϵͳ��ʾ�������
							startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
							BTAdapter.enable(); // ǿ�ƴ�����
						}
					} else { // ���ز�֧����������
						Toast.makeText(getApplicationContext(), "��������������", Toast.LENGTH_SHORT).show();
						finish();
					}
				} else { // �����ر�
					BTAdapter.disable();
				}
			}
		});

		final BluetoothAdapter.LeScanCallback myLeScanCallback = new BluetoothAdapter.LeScanCallback() {

			@Override
			public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
				// TODO Auto-generated method stub
				Log.i("BT", "name:" + " " + device.getName() + " " + " " + device.getAddress());
				list_array.add(device.getName());
				
			}
		};

		// ɨ�谴ť����
		
		final Handler myHandle = new Handler();
		
		bt_scan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(BTAdapter.isEnabled()) {
				Toast.makeText(getApplicationContext(), "���ɨ�谴ť", Toast.LENGTH_SHORT).show();
				BTAdapter.startLeScan(myLeScanCallback);
				Log.i("BT", "��ʼɨ�������豸");
				myHandle.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						BTAdapter.stopLeScan(myLeScanCallback);
						Log.i("BT", "����ɨ���ѹر�");
					}
				}, 20000);
				} else {
					Toast.makeText(getApplicationContext(), "����û�д�", Toast.LENGTH_SHORT).show();
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

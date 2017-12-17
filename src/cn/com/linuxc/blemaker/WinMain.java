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

		final BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter(); // ��ȡ��������ʵ��
		final int REQUEST_ENABLE_BT = 1; // ����ʹ���������

		Switch openSwitch = (Switch) findViewById(R.id.sw_open);

		openSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) { // �Ƿ񴥷�����
					if (BTAdapter != null) { // ����֧����������
						if (!BTAdapter.isEnabled()) { // δ������������������������
							Log.d("BT", "BTopen");
							Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);		// ����ϵͳ��ʾ�������
							startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
							BTAdapter.enable();		// ǿ�ƴ�����
						}
					} else { // ���ز�֧����������
						Toast.makeText(getApplicationContext(), "��������������", Toast.LENGTH_SHORT).show();
						finish();
					}
				} else {		// �����ر�
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

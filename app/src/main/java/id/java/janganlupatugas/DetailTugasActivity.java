package id.java.janganlupatugas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import id.java.janganlupatugas.activity.AddTugasActivity;

public class DetailTugasActivity extends AppCompatActivity {
    TextView judul, deskripsi, tanggal, waktu;
    String data1,data2,data3,data4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tugas);
        judul = findViewById(R.id.myTextTugas);
        tanggal = findViewById(R.id.myTextDeadline);
        deskripsi = findViewById(R.id.myTextCatatan);
        waktu = findViewById(R.id.myTextWaktu);

        getData();
        setData();
        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.HapusButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(DetailTugasActivity.this,MainActivity.class);
                data.putExtra("delete_data",getIntent().getIntExtra("position",1));
                data1 = getIntent().getStringExtra(AddTugasActivity.EXTRA_TITLE);
                data2 = getIntent().getStringExtra(AddTugasActivity.EXTRA_DESCRIPTION);
                data3 = getIntent().getStringExtra(AddTugasActivity.EXTRA_DEADLINE);
                data4 = getIntent().getStringExtra(AddTugasActivity.EXTRA_WAKTU);
                data.putExtra(AddTugasActivity.EXTRA_TITLE,data1);
                data.putExtra(AddTugasActivity.EXTRA_DESCRIPTION,data2);
                data.putExtra(AddTugasActivity.EXTRA_DEADLINE,data3);
                data.putExtra(AddTugasActivity.EXTRA_WAKTU,data4);
                setResult(RESULT_OK,data);
                finish();
            }
        });

    };

    private void getData(){
        if(getIntent().hasExtra(AddTugasActivity.EXTRA_TITLE) && getIntent().hasExtra(AddTugasActivity.EXTRA_DESCRIPTION) ){
            data1 = getIntent().getStringExtra(AddTugasActivity.EXTRA_TITLE);
            data2 = getIntent().getStringExtra(AddTugasActivity.EXTRA_DESCRIPTION);
            data3 = getIntent().getStringExtra(AddTugasActivity.EXTRA_DEADLINE);
            data4 = getIntent().getStringExtra(AddTugasActivity.EXTRA_WAKTU);
        }else {
            Toast.makeText(this,"Nothing",Toast.LENGTH_SHORT).show();
        }
    }
    private void setData(){
        judul.setText(data1);
        deskripsi.setText(data2);
        tanggal.setText(data3);
        waktu.setText(data4);
    }
}
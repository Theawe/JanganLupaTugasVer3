package id.java.janganlupatugas.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import id.java.janganlupatugas.MainActivity;
import id.java.janganlupatugas.Notifikasi;
import id.java.janganlupatugas.R;
import id.java.janganlupatugas.Tugas;

import static java.util.Calendar.MINUTE;

public class AddTugasActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "id.java.janganlupatugas.activity.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "id.java.janganlupatugas.activity.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "id.java.janganlupatugas.activity.EXTRA_DESCRIPTION";
    public static final String EXTRA_DEADLINE =
            "id.java.janganlupatugas.activity.EXTRA_DEADLINE";
    public static final String EXTRA_WAKTU =
            "id.java.janganlupatugas.activity.EXTRA_WAKTU";
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    private EditText namaTugas;
    private EditText catatan;
    private TextView deadline;
    private TextView waktu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tugas);
        namaTugas = findViewById(R.id.input_tugas);
        catatan = findViewById(R.id.input_catatan);
        deadline = findViewById(R.id.input_deadline);
        waktu = findViewById(R.id.input_waktu);
        final Button button = findViewById(R.id.submit);
        final long date = System.currentTimeMillis();
        SimpleDateFormat dateSdf = new SimpleDateFormat("d MMMM");
        String dateString = dateSdf.format(date);
        deadline.setText(dateString);
        SimpleDateFormat timeSdf = new SimpleDateFormat("hh : mm a");
        String timeString = timeSdf.format(date);
        waktu.setText(timeString);
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        //Set Tanggal
        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(getLayoutInflater().getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String newMonth = getMonth(monthOfYear);
                                deadline.setText(dayOfMonth + " " + newMonth);
                                cal.set(Calendar.YEAR, year);
                                cal.set(Calendar.MONTH, monthOfYear);
                                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            }
                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(date);
            }
        });
        //Set Waktu
        waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getLayoutInflater().getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String time;
                                @SuppressLint("DefaultLocale") String minTime = String.format("%02d", minute);
                                if (hourOfDay >= 0 && hourOfDay < 12) {
                                    time = hourOfDay + " : " + minTime + " AM";
                                } else {
                                    if (hourOfDay != 12) {
                                        hourOfDay = hourOfDay - 12;
                                    }
                                    time = hourOfDay + " : " + minTime + " PM";
                                }
                                waktu.setText(time);
                                cal.set(Calendar.HOUR, hourOfDay);
                                cal.set(Calendar.MINUTE, minute);
                                cal.set(Calendar.SECOND, 0);
                                Log.d("AddTugasActivity", "onTimeSet: Time has been set successfully");
                            }
                        }, cal.get(Calendar.HOUR), cal.get(MINUTE), false);
                timePickerDialog.show();
            }
        });




//        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//            }
//        };
//        deadline.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(AddTugasActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });

        button.setOnClickListener(view ->{
            Intent replyIntent = new Intent(this, MainActivity.class);
            if(TextUtils.isEmpty(namaTugas.getText())){
                setResult(RESULT_CANCELED, replyIntent);
            }else{
                String nama_tugas = namaTugas.getText().toString();
                String desk = catatan.getText().toString();
                String Deadline = deadline.getText().toString();
                String Waktu = waktu.getText().toString();
//                Tugas tugas = new Tugas(nama_tugas,desk,Deadline,Waktu);
                replyIntent.putExtra(EXTRA_TITLE,nama_tugas);
                replyIntent.putExtra(EXTRA_DESCRIPTION,desk);
                replyIntent.putExtra(EXTRA_DEADLINE,Deadline);
                replyIntent.putExtra(EXTRA_WAKTU,Waktu);
                setResult(RESULT_OK,replyIntent);
                Log.d("Test", String.valueOf(cal.getTimeInMillis()));
                scheduleNotification(getNotification(nama_tugas),cal.getTimeInMillis());
            }
            finish();
        });

    }

    private void scheduleNotification(Notification notification, long delay){
        Intent notificationIntent = new Intent(this, Notifikasi.class);
        notificationIntent.putExtra(Notifikasi.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(Notifikasi.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getLayoutInflater().getContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, delay, pendingIntent);
            Log.d("Test", "scheduleNotification: test");
        }
    }

    private Notification getNotification(String content) {

        //Saat notifikasi di klik di arahkan ke MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getLayoutInflater().getContext(), default_notification_channel_id);
        builder.setContentTitle("Pengingat");
        builder.setContentText(content);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.ic_waktu);
        builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        return builder.build();
    }

    private String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
}
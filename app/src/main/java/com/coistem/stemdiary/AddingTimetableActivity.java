package com.coistem.stemdiary;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class AddingTimetableActivity extends Activity implements View.OnClickListener {
    String selectedDate = "";

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("date", selectedDate);
        setResult(RESULT_OK, intent);
        String date = intent.getStringExtra("date");
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_timetable);
        MaterialCalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int day = date.getDay();
                int month = date.getMonth();
                int year = date.getYear();
                selectedDate=""+day+'.'+month+'.'+year;
            }
        });
        Button setTimetableBtn = findViewById(R.id.setTimetableBtn);
        setTimetableBtn.setOnClickListener(this);
    }

}

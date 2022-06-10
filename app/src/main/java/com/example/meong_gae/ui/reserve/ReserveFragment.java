package com.example.meong_gae.ui.reserve;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meong_gae.Global;
import com.example.meong_gae.databinding.FragmentReserveBinding;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReserveFragment extends Fragment {

    private FragmentReserveBinding binding;

    CompactCalendarView compactCalendarView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat sdf;
    TextView tx_date;
    LinearLayout ly_detail;
    LinearLayout ly_left, ly_right;
    Calendar myCalendar;
    Date c;
    SimpleDateFormat df;
    String formattedDate;
    String[] dates = new String[0];
    RecyclerView recyclerView;
    TextView tx_item;
    CalendarAdapter adapter;

    List<ScheduleJobBean> schedule;
    List<ScheduleJobBean> clickSchedule;
    String clickDay;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestore db2 = FirebaseFirestore.getInstance();
    int classId, nowNum, maxNum;
    String classTD, classTitle, tName;

    String email;
    int ticketId;

    //https://ranjithjith.blogspot.com/2018/11/custom-calendarview-with-events-call-api.html
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReserveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        email = ((Global) getActivity().getApplication()).getEmail();
        ticketId = ((Global) getActivity().getApplication()).getTicketId();

        init();
        //  valid();
        calendarlistener();
        Setdate();   //현재 날짜 string 값으로 가져오는 것

        // Toast.makeText(getApplicationContext(),""+formattedDate,Toast.LENGTH_LONG).show();
        new EventListAsy().execute(formattedDate);      // 오늘 날짜 넘김
        new EventViewAsy().execute(formattedDate);
        //  Log.v("formmattedDate변수 값",formattedDate);

        tx_item.setText(formattedDate + "은 운영하지 않는 날 입니다.");

        ly_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showCalendarWithAnimation();
                compactCalendarView.showNextMonth();
            }
        });

        ly_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showCalendarWithAnimation();
                compactCalendarView.showPreviousMonth();
            }
        });

        return root;
    }

    public void dot(){
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        sdf = new SimpleDateFormat("MMMM yyyy");
        tx_date.setText(sdf.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        myCalendar = Calendar.getInstance();
        Log.v("size!", String.valueOf(schedule.size()));
        for (int j = 0; j < schedule.size(); j++) {
            String s1 = schedule.get(j).getDate();        ///날짜만 받아와서 처리하기 / 형식: 2022-05-05
            dates = s1.split("-");
            int mon = Integer.parseInt(dates[1]);
            myCalendar.set(Calendar.YEAR, Integer.parseInt(dates[0]));
            myCalendar.set(Calendar.MONTH, mon - 1);
            myCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));

            //  Event event = new Event(Color.RED, myCalendar.getTimeInMillis(), "test");
            Event event = new Event(Color.RED, myCalendar.getTimeInMillis(), "test");
            compactCalendarView.addEvent(event);
        }
    }


    public void init() {
        compactCalendarView = binding.compactcalendarView;
        tx_date = binding.text;
        ly_left = binding.layoutLeft;
        ly_right = binding.layoutRight;
        ly_detail = binding.layoutDetail;
        recyclerView = binding.listRecycleView;
        tx_item = binding.textItem;
    }

    public void Setdate() {
        c = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c);
        clickDay = formattedDate;
    }

    public void calendarlistener() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                Log.v("클릭 년도", String.valueOf(dateClicked.getYear()));
                Log.v("클릭한 날짜 월", String.valueOf(dateClicked.getMonth()));
                Log.v("클릭한 날짜 일", String.valueOf(dateClicked.getDate()));

                String year = String.valueOf(dateClicked.getYear() - 100);
                String month = String.valueOf(dateClicked.getMonth() + 1);
                String date = String.valueOf(dateClicked.getDate());

                //     clickDay = year+"-"+month+"-"+date;
                clickDay = df.format(dateClicked);
                Log.v("clickday", clickDay);

                new EventViewAsy().execute(DateFormat.format(dateClicked));

                /* 날짜 비교해서 텍스트 문구 다르게 하기 */
                tx_item.setText(DateFormat.format(dateClicked) + "은 운영하지 않는 날 입니다.");
                //   tx_item.setText(DateFormat.format(dateClicked) + "지난 수업 입니다");
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                compactCalendarView.removeAllEvents();
                tx_date.setText(simpleDateFormat.format(firstDayOfNewMonth));

                new EventListAsy().execute(DateFormat.format(firstDayOfNewMonth));
                new EventViewAsy().execute(DateFormat.format(firstDayOfNewMonth));

                tx_item.setText(DateFormat.format(firstDayOfNewMonth) + "은 등록된 수업이 없습니다.");

            }
        });
    }

    // 달력 (빨간)동그라미 표시하기 위해서
    class EventListAsy extends AsyncTask<String, Void, ArrayList<ScheduleJobBean>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ScheduleJobBean> doInBackground(String... strings) {     // 쓰레드로 수행 할 작업[스레드 진행상태]
            schedule = new ArrayList<ScheduleJobBean>();

            //db로 하면 빨간 표시가 안됨,,
            db2.collection("Class")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {    //어차피 항상 classId는 1개
                            classId = Integer.valueOf(String.valueOf(document.get("classId")));
                            classTD = String.valueOf(document.get("classTD"));
                            classTitle = String.valueOf(document.get("classTitle"));
                            maxNum = Integer.valueOf(String.valueOf(document.get("maxNum")));
                            nowNum = Integer.valueOf(String.valueOf(document.get("nowNum")));
                            tName = String.valueOf(document.get("tName"));
                            schedule.add(new ScheduleJobBean(classId, classTD, classTitle, tName, nowNum, maxNum));
                        }
                    }
                }
            });

            schedule.add(new ScheduleJobBean(4, "2022-06-05", "소형견 놀이반", "이애정 강사", 5,6));
            schedule.add(new ScheduleJobBean(5, "2022-06-13", "소형견 대회반", "강이슬 강사", 4,4));

            return (ArrayList<ScheduleJobBean>) schedule;
        }

        @Override
        protected void onPostExecute(final ArrayList<ScheduleJobBean> list) {        ///작업 완료 후
            super.onPostExecute(list);

            compactCalendarView.setUseThreeLetterAbbreviation(true);
            sdf = new SimpleDateFormat("MMMM yyyy");
            tx_date.setText(sdf.format(compactCalendarView.getFirstDayOfCurrentMonth()));
            myCalendar = Calendar.getInstance();
            Log.v("size!", String.valueOf(schedule.size()));
            for (int j = 0; j < schedule.size(); j++) {
                String s1 = schedule.get(j).getDate();        ///날짜만 받아와서 처리하기 / 형식: 2022-05-05
                dates = s1.split("-");
                int mon = Integer.parseInt(dates[1]);
                myCalendar.set(Calendar.YEAR, Integer.parseInt(dates[0]));
                myCalendar.set(Calendar.MONTH, mon - 1);
                myCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));

                //  Event event = new Event(Color.RED, myCalendar.getTimeInMillis(), "test");
                Event event = new Event(Color.RED, myCalendar.getTimeInMillis(), "test");
                compactCalendarView.addEvent(event);
            }
        }
    }

    //날짜 클릭 했을 때 예약 리스트 가져오기
    class EventViewAsy extends AsyncTask<String, Void, ArrayList<ScheduleJobBean>> implements ItemClickListener {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ScheduleJobBean> doInBackground(String... params) {
            clickSchedule = new ArrayList<>();
            Log.v("schedule EventView!!", String.valueOf(schedule.size()));
            for (int i = 0; i < schedule.size(); i++) {
                if (schedule.get(i).getDate().equals(clickDay)) {   // 클릭한 날짜의 스케줄만 가져오기   +오늘 날짜 이후만 가져오기(보류)
                    clickSchedule.add(new ScheduleJobBean(schedule.get(i).getClassId(), schedule.get(i).getDate(), schedule.get(i).getClassN(),
                            schedule.get(i).getTeacher(), schedule.get(i).getNowNumInt(), schedule.get(i).getMaxNumInt()));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<ScheduleJobBean> list) {
            super.onPostExecute(list);
            if (clickSchedule.size() == 0) {
                tx_item.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tx_item.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            adapter = new CalendarAdapter(clickSchedule, getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            adapter.setClickListener(this::onClick);
        }

        @Override
        public void onClick(View view, int position) {

            ScheduleJobBean scheduleJobBean = clickSchedule.get(position);
            Intent intent = new Intent(view.getContext(), ReserveDetail.class); //fragment라서 activity intent와 다름
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("classId", scheduleJobBean.getClassId());
            intent.putExtra("dateTime", scheduleJobBean.getDateTime());
            intent.putExtra("classN", scheduleJobBean.getClassN());
            intent.putExtra("teacher", scheduleJobBean.getTeacher());
            intent.putExtra("nowNum", scheduleJobBean.getNowNum());
            intent.putExtra("maxNum", scheduleJobBean.getMaxNum());

            if (scheduleJobBean.getNowNumInt() < scheduleJobBean.getMaxNumInt()) { //예약 가능하면 넘어감
                view.getContext().startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

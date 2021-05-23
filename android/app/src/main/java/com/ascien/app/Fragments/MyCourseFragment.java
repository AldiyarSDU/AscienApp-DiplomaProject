package com.ascien.app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ascien.app.Adapters.MyCourseAdapter;
import com.ascien.app.Models.MyCourse;
import com.ascien.app.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;

public class MyCourseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MyCourseFragment";
    GridView myCoursesGridLayout;
    private ProgressBar progressBar;
    RelativeLayout myCourseView;
    RelativeLayout signInPlaceholder;
    RelativeLayout mEmptyContentArea;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.my_course_fragment, container, false);
        init(view);
        initSwipeRefreshLayout(view);
        initProgressBar(view);
        initArrayData();
        return view;
    }

    private void initSwipeRefreshLayout(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.googleAccentColor1,
                R.color.googleAccentColor2,
                R.color.googleAccentColor3,
                R.color.googleAccentColor4);
    }

    private void init(View view) {
        myCoursesGridLayout = view.findViewById(R.id.myCoursesGridLayout);
        myCourseView = view.findViewById(R.id.myCourseView);
        mEmptyContentArea = view.findViewById(R.id.emptyContentArea);
    }

    private void initArrayData() {
        final ArrayList<MyCourse> mMyCourse = new ArrayList<>();
        mMyCourse.add(new MyCourse(15, "Криптовалюты для начинающих", "https://ascien.s3.us-east-2.amazonaws.com/courses/images/8Eaq2bC35xG5SL3G2OiT5JOmr0fuEFDxTFtCQrQf.png", "150", "1.56", (float) 1.5, 7, 7, 6, 7, 8, "asdas", "youtube", "https://www.youtube.com/watch?v=_KyAA425fxY"));
        mMyCourse.add(new MyCourse(1, "SQL for Beginners", "https://ascien.s3.us-east-2.amazonaws.com/courses/images/C1yLjTKcxHqpVqwRjJdEyQ2DvvCPzRxguL8nLCCF.jpg", "150", "1.56", (float) 1.5, 7, 7, 8, 7, 8, "asdas", "youtube", "https://www.youtube.com/watch?v=_KyAA425fxY"));
        initMyCourseGridView(mMyCourse);
    }

    private void initProgressBar(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }

    private void initMyCourseGridView(ArrayList<MyCourse> myCourses) {
        MyCourseAdapter adapter = new MyCourseAdapter(getActivity(), myCourses);
        myCoursesGridLayout.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}

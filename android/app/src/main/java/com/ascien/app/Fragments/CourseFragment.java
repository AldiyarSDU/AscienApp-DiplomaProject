package com.ascien.app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ascien.app.Activities.CoursesActivity;
import com.ascien.app.Adapters.CategoryAdapter;
import com.ascien.app.Adapters.TopCourseAdapter;
import com.ascien.app.Models.Category;
import com.ascien.app.Models.CategoryCourse;
import com.ascien.app.Models.TopCourse;
import com.ascien.app.Network.ApiClient;
import com.ascien.app.R;
import com.ascien.app.Utils.Helpers;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "Fragment";

    private ArrayList<Category> mCategory = new ArrayList<>();
    private ArrayList<CategoryCourse> CategoryNew = new ArrayList<>();
    private ArrayList<TopCourse> topCourseNew = new ArrayList<>();
    private ArrayList<TopCourse> CourseNew = new ArrayList<>();

    private RecyclerView recyclerViewForTopCourses = null;
    private RecyclerView recyclerViewForCategories = null;
    private ProgressBar progressBar;
    private TextView allTopCoursesTv, allCategoryCoursesTv;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_fragment, container, false);
        recyclerViewForTopCourses = view.findViewById(R.id.recyclerViewForTopCourses);
        recyclerViewForCategories = view.findViewById(R.id.recyclerViewForCategories);
        initProgressBar(view);
        initSwipeRefreshLayout(view);
        apiCalls();
        allTopCoursesTv = view.findViewById(R.id.viewAllCourses);
        allCategoryCoursesTv = view.findViewById(R.id.ViewAllCategoryCourses);
        allTopCoursesTv.setOnClickListener(v -> {
            getAllCourses();
        });
        allCategoryCoursesTv.setOnClickListener(v -> {
            getAllCourses();
        });
        initTopCourseRecyclerView();
        initCategoryListRecyclerView();
        return view;
    }

    private void apiCalls() {
        getCategoriesNew();
        getTopCourseNew();
    }

    private void getAllCourses() {
        Call<List<TopCourse>> topCourseCall = ApiClient.getApi().getAllCourses();
        topCourseCall.enqueue(new Callback<List<TopCourse>>() {
            @Override
            public void onResponse(Call<List<TopCourse>> call, Response<List<TopCourse>> response) {
                CourseNew = new ArrayList<>();
                List<TopCourse> topCourse = response.body();
                for (TopCourse t : topCourse) {
                    CourseNew.add(new TopCourse(t.getId(), t.getUser_id(), t.getTitle(), t.getCategory_id(), t.getShort_description(), t.getLanguage(), t.getDescription(), t.getLevel(), t.getImage(), t.getIntro_video(), t.getRequirements(), t.getWhat_will_learn(), t.getIs_free(), t.getPrice(), t.getSale_price(), t.getCertificate(), t.getCreated_at(), t.getUpdated_at(), t.getStatus(), t.getInstructor()));
                }
                Intent intent = new Intent(getContext(), CoursesActivity.class);
                intent.putExtra("CourseNew", CourseNew);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<TopCourse>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    private void initProgressBar(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }

    private void initTopCourseRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewForTopCourses.setLayoutManager(layoutManager);
        TopCourseAdapter adapter = new TopCourseAdapter(getActivity(), topCourseNew);
        recyclerViewForTopCourses.setAdapter(adapter);
    }

    private void initCategoryListRecyclerView() {
        CategoryAdapter adapter = new CategoryAdapter(getActivity(), CategoryNew);
        recyclerViewForCategories.setAdapter(adapter);
        recyclerViewForCategories.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setHeight(int numberOfCategories) {

        int pixels = Helpers.convertDpToPixel((numberOfCategories * 90) + 10); // 9 is the number of categories and the 90 is each items height with the margin of top and bottom. Extra 10 dp for readability
        Log.d(TAG, "Lets change the height of recycler view");
        ViewGroup.LayoutParams params1 = recyclerViewForCategories.getLayoutParams();
        recyclerViewForCategories.setMinimumHeight(pixels);
        recyclerViewForCategories.requestLayout();
    }

    private void getTopCourseNew() {
        progressBar.setVisibility(View.VISIBLE);
        topCourseNew = new ArrayList<>();
        Call<List<TopCourse>> topCourseCall = ApiClient.getApi().getTopCourse_new();
        topCourseCall.enqueue(new Callback<List<TopCourse>>() {
            @Override
            public void onResponse(Call<List<TopCourse>> call, Response<List<TopCourse>> response) {
                List<TopCourse> topCourse = response.body();
                for (TopCourse t : topCourse) {
                    Log.d("TAG", "onResponse: " + topCourse.getClass().getName());
                    topCourseNew.add(new TopCourse(t.getId(), t.getUser_id(), t.getTitle(), t.getCategory_id(), t.getShort_description(), t.getLanguage(), t.getDescription(), t.getLevel(), t.getImage(), t.getIntro_video(), t.getRequirements(), t.getWhat_will_learn(), t.getIs_free(), t.getPrice(), t.getSale_price(), t.getCertificate(), t.getCreated_at(), t.getUpdated_at(), t.getStatus(), t.getInstructor()));
                }
                initTopCourseRecyclerView();
                mSwipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<TopCourse>> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getCategoriesNew() {
        progressBar.setVisibility(View.VISIBLE);
        mCategory = new ArrayList<>();
        Call<List<CategoryCourse>> category_newCall = ApiClient.getApi().getCategories_new();
        category_newCall.enqueue(new Callback<List<CategoryCourse>>() {
            @Override
            public void onResponse(Call<List<CategoryCourse>> call, Response<List<CategoryCourse>> response) {
                Log.d(TAG, "Category Fetched Successfully");
                List<CategoryCourse> category_new = response.body();
                for (CategoryCourse c : category_new) {
                    CategoryNew.add(new CategoryCourse(c.getId(), c.getName(), c.getImage(), c.getDescription(), c.getCourses_count()));
                }
                initCategoryListRecyclerView();
                setHeight(CategoryNew.size());
                mSwipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<CategoryCourse>> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onRefresh() {
        apiCalls();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
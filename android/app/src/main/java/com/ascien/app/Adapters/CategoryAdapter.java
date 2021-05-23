package com.ascien.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ascien.app.Activities.CoursesActivity;
import com.ascien.app.Models.CategoryCourse;
import com.ascien.app.Models.Course;
import com.ascien.app.Models.TopCourse;
import com.ascien.app.Network.ApiClient;
import com.ascien.app.R;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements Serializable {

    private static final String TAG = "CategoryAdapter";

    //vars
    private ArrayList<CategoryCourse> CategoryNew = new ArrayList<>();
    private ArrayList<TopCourse> CourseNew = new ArrayList<>();
    private ArrayList<Course> mCourses = new ArrayList<>();
    private Context mContext;

    private MaterialCardView categoryCardViewList;

    public CategoryAdapter(Context context, ArrayList<CategoryCourse> category) {
        CategoryNew = category;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_cell, parent, false);
        categoryCardViewList = view.findViewById(R.id.categoryCardView);

        final ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CategoryCourse currentCategory = CategoryNew.get(holder.getAdapterPosition());
                getCoursesByCategoryIdNew(currentCategory.getId());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CategoryCourse currentCategory = CategoryNew.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(currentCategory.getImage())
                .into(holder.image);

        // Calling this function will help you to change the card view background color dynamically.
        applyCategoryCardViewBackgroundColor(position);
        holder.name.setText(currentCategory.getName());
        holder.numberOfCourses.setText(currentCategory.getCourses_count() + " Курсов");
    }

    private void getCoursesByCategoryIdNew(int categoryId) {
        Call<List<TopCourse>> topCourseCall = ApiClient.getApi().getCourseByCategory(categoryId);
        topCourseCall.enqueue(new Callback<List<TopCourse>>() {
            @Override
            public void onResponse(Call<List<TopCourse>> call, Response<List<TopCourse>> response) {
                CourseNew = new ArrayList<>();
                List<TopCourse> topCourse = response.body();
                for (TopCourse t : topCourse) {
                    CourseNew.add(new TopCourse(t.getId(), t.getUser_id(), t.getTitle(), t.getCategory_id(), t.getShort_description(), t.getLanguage(), t.getDescription(), t.getLevel(), t.getImage(), t.getIntro_video(), t.getRequirements(), t.getWhat_will_learn(), t.getIs_free(), t.getPrice(), t.getSale_price(), t.getCertificate(), t.getCreated_at(), t.getUpdated_at(), t.getStatus(), t.getInstructor()));
                }
                Intent intent = new Intent(mContext, CoursesActivity.class);
                intent.putExtra("CourseNew", CourseNew);
                mContext.startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<TopCourse>> call, Throwable t) {

            }
        });
    }

    private void applyCategoryCardViewBackgroundColor(int position) {
        int incrementalPosition = position + 1;
        if (incrementalPosition % 3 == 0) {
            categoryCardViewList.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        } else if ((incrementalPosition - 2) % 3 == 0) {
            categoryCardViewList.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            categoryCardViewList.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return CategoryNew.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView numberOfCourses;
        ImageView goToCategoryWiseCourseList;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.categoryName);
            numberOfCourses = itemView.findViewById(R.id.numberOfCourses);
            image = itemView.findViewById(R.id.categoryImageView);
        }
    }
}
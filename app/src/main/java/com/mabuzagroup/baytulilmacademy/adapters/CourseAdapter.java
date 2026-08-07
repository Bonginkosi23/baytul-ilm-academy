package com.mabuzagroup.baytulilmacademy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.models.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public interface OnCourseActionListener {

        void onEdit(Course course);

        void onDelete(Course course);

    }

    private final List<Course> courseList;
    private final OnCourseActionListener listener;

    public CourseAdapter(List<Course> courseList,
                         OnCourseActionListener listener) {

        this.courseList = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);

        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {

        Course course = courseList.get(position);

        holder.tvTitle.setText(course.getTitle());
        holder.tvDescription.setText(course.getDescription());

        holder.btnMenu.setOnClickListener(v -> {

            PopupMenu popupMenu =
                    new PopupMenu(v.getContext(), holder.btnMenu);

            popupMenu.inflate(R.menu.item_options_menu);

            popupMenu.setOnMenuItemClickListener(item -> {

                if (item.getItemId() == R.id.actionEdit) {

                    listener.onEdit(course);
                    return true;

                } else if (item.getItemId() == R.id.actionDelete) {

                    listener.onDelete(course);
                    return true;
                }

                return false;
            });

            popupMenu.show();

        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDescription;
        ImageButton btnMenu;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvCourseTitle);
            tvDescription = itemView.findViewById(R.id.tvCourseDescription);
            btnMenu = itemView.findViewById(R.id.btnMenu);
        }
    }
}
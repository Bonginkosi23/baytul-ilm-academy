package com.mabuzagroup.baytulilmacademy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.models.Lesson;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    public interface OnLessonActionListener {

        void onEdit(Lesson lesson);

        void onDelete(Lesson lesson);

    }

    private final List<Lesson> lessonList;
    private final OnLessonActionListener listener;

    public LessonAdapter(
            List<Lesson> lessonList,
            OnLessonActionListener listener) {

        this.lessonList = lessonList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson, parent, false);

        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {

        Lesson lesson = lessonList.get(position);

        holder.tvTitle.setText(lesson.getTitle());
        holder.tvDescription.setText(lesson.getDescription());

        if (lesson.getYoutubeUrl() == null || lesson.getYoutubeUrl().isEmpty()) {
            holder.tvYoutube.setText("No video");
        } else {
            holder.tvYoutube.setText("▶ Video Available");
        }

        holder.btnMenu.setOnClickListener(v -> {

            PopupMenu popupMenu =
                    new PopupMenu(v.getContext(), holder.btnMenu);

            popupMenu.inflate(R.menu.item_options_menu);

            popupMenu.setOnMenuItemClickListener(item -> {

                if (item.getItemId() == R.id.actionEdit) {

                    listener.onEdit(lesson);
                    return true;

                } else if (item.getItemId() == R.id.actionDelete) {

                    listener.onDelete(lesson);
                    return true;
                }

                return false;
            });

            popupMenu.show();

        });
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {

        ImageButton btnMenu;

        TextView tvTitle;
        TextView tvDescription;
        TextView tvYoutube;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvLessonTitle);
            tvDescription = itemView.findViewById(R.id.tvLessonDescription);
            tvYoutube = itemView.findViewById(R.id.tvYoutube);
            btnMenu = itemView.findViewById(R.id.btnMenu);
        }
    }
}
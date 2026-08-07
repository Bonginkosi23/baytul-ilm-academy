package com.mabuzagroup.baytulilmacademy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;

import com.mabuzagroup.baytulilmacademy.repositories.CategoryRepository;
import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public interface OnCategoryActionListener {

        void onEdit(Category category);

        void onDelete(Category category);

    }
    private final OnCategoryActionListener listener;
    private final List<Category> categoryList;

    private Context context;

    public CategoryAdapter(
            List<Category> categoryList,
            OnCategoryActionListener listener) {

        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category category = categoryList.get(position);

        holder.txtCategoryName.setText(category.getName());
        holder.txtCategoryDescription.setText(category.getDescription());

        holder.btnMenu.setOnClickListener(v -> {

            PopupMenu popupMenu =
                    new PopupMenu(v.getContext(), holder.btnMenu);

            popupMenu.inflate(R.menu.item_options_menu);

            popupMenu.setOnMenuItemClickListener(item -> {

                if (item.getItemId() == R.id.actionEdit) {

                    // Edit Category
                    listener.onEdit(category);
                    return true;

                } else if (item.getItemId() == R.id.actionDelete) {

                    // Delete Category
                    listener.onDelete(category);
                    return true;
                }

                return false;
            });

            popupMenu.show();

        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView txtCategoryName;
        TextView txtCategoryDescription;
        ImageButton btnMenu;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            txtCategoryDescription = itemView.findViewById(R.id.txtCategoryDescription);
            btnMenu = itemView.findViewById(R.id.btnMenu);
        }
    }

    private void showDeleteDialog(Category category) {

        new AlertDialog.Builder(context)
                .setTitle("Delete Category")
                .setMessage("Are you sure you want to delete this category?")
                .setPositiveButton("Delete", (dialog, which) -> {

                    CategoryRepository repository =
                            new CategoryRepository();

                    repository.deleteCategory(
                            category.getId(),

                            new CategoryRepository.CategoryCallback() {

                                @Override
                                public void onSuccess() {

                                    Toast.makeText(
                                            context,
                                            "Category deleted",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                }

                                @Override
                                public void onFailure(String message) {

                                    Toast.makeText(
                                            context,
                                            message,
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            });

                })

                .setNegativeButton("Cancel", null)
                .show();
    }
}
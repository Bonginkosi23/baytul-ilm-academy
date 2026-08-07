package com.mabuzagroup.baytulilmacademy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.PopupMenu;

import com.mabuzagroup.baytulilmacademy.R;
import com.mabuzagroup.baytulilmacademy.models.Module;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {

    public interface OnModuleActionListener {

        void onEdit(Module module);

        void onDelete(Module module);

    }

    private final List<Module> moduleList;
    private final OnModuleActionListener listener;

    public ModuleAdapter(
            List<Module> moduleList,
            OnModuleActionListener listener) {

        this.moduleList = moduleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_module, parent, false);

        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {

        Module module = moduleList.get(position);

        holder.tvTitle.setText(module.getTitle());
        holder.tvDescription.setText(module.getDescription());

        holder.btnMenu.setOnClickListener(v -> {

            PopupMenu popupMenu =
                    new PopupMenu(v.getContext(), holder.btnMenu);

            popupMenu.inflate(R.menu.item_options_menu);

            popupMenu.setOnMenuItemClickListener(item -> {

                if (item.getItemId() == R.id.actionEdit) {

                    listener.onEdit(module);
                    return true;

                } else if (item.getItemId() == R.id.actionDelete) {

                    listener.onDelete(module);
                    return true;
                }

                return false;
            });

            popupMenu.show();

        });
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder {

        ImageButton btnMenu;

        TextView tvTitle;
        TextView tvDescription;

        ModuleViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvModuleTitle);
            tvDescription = itemView.findViewById(R.id.tvModuleDescription);
            btnMenu = itemView.findViewById(R.id.btnMenu);
        }
    }
}
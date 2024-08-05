package com.example.kindergarten_management.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.controllers.DatabaseController;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.ClassModel;
import com.example.kindergarten_management.views.fragments.UpdateClassFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for displaying classes in a RecyclerView.
 */
public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    private final ArrayList<ClassModel> classList;
    private List<ClassModel> selectedFavorites = new ArrayList<>();
    private final Context context;
    private final FragmentManager fragmentManager;

    /**
     * Constructor for initializing the ClassAdapter.
     * @param classList The list of classes.
     * @param context The context of the application.
     * @param fragmentManager The fragment manager to handle fragment transactions.
     */
    public ClassAdapter(ArrayList<ClassModel> classList, Context context, FragmentManager fragmentManager) {
        this.classList = classList;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.selectedFavorites = new ArrayList<>();
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassModel classModel = classList.get(position);
        holder.idTextView.setText(String.valueOf(classModel.getId()));
        holder.typeTextView.setText(classModel.getType());
        holder.maxChildrenTextView.setText(String.valueOf(classModel.getMaxChildren()));
        holder.ageRangeTextView.setText(classModel.getMinAge() + " - " + classModel.getMaxAge());

        holder.btnUpdate.setOnClickListener(v -> {
            String classId = String.valueOf(classModel.getId());
            Bundle args = new Bundle();
            args.putString("classId", classId);
            FragmentHelper.replaceFragment(fragmentManager, R.id.kindergarten_manager_fragment, new UpdateClassFragment(), args);
        });

        holder.btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(holder.itemView, position, classModel);
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    /**
     * Shows a confirmation dialog to verify the deletion of a class.
     * @param view The view to find a parent from.
     * @param position The position of the class in the list.
     * @param classModel The class to be deleted.
     */
    private void showDeleteConfirmationDialog(View view, int position, ClassModel classModel) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this class?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    boolean wasDeleted = DatabaseController.getInstance(context).deleteClass(classModel);
                    if(wasDeleted) {
                        classList.remove(position);
                        notifyItemRemoved(position);
                        SnackbarHelper.sendSuccessMessage(view, "Class deleted");
                    } else {
                        SnackbarHelper.sendErrorMessage(view, "Failed to delete class!");
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Returns a list of selected favorite classes objects.
     *
     * @return A list of selected favorite classes objects.
     */
    public List<ClassModel> getSelectedFavorites() {
        return selectedFavorites;
    }

    /**
     * Resets the selection of favorite classes.
     */
    public void resetFavorites() {
        selectedFavorites.clear();
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for classes.
     */
    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        public TextView idTextView;
        public TextView typeTextView;
        public TextView maxChildrenTextView;
        public TextView ageRangeTextView;
        public Button btnUpdate;
        public Button btnDelete;

        /**
         * Constructor for initializing the ClassViewHolder.
         * @param view The view representing the class item.
         */
        public ClassViewHolder(View view) {
            super(view);
            idTextView = view.findViewById(R.id.text_view_id);
            typeTextView = view.findViewById(R.id.text_view_type);
            maxChildrenTextView = view.findViewById(R.id.text_view_max_children);
            ageRangeTextView = view.findViewById(R.id.text_view_age_range);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}

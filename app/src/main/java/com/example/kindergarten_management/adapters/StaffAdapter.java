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
import com.example.kindergarten_management.helpers.SharedPreferencesHelper;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.StaffMemberModel;
import com.example.kindergarten_management.views.fragments.AddStaffFragment;
import com.example.kindergarten_management.views.fragments.UpdateStaffFragment;

import java.util.ArrayList;

/**
 * Adapter class for displaying staff members in a RecyclerView.
 */
public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {
    private final ArrayList<StaffMemberModel> staffList;
    private final Context context;
    private FragmentManager fragmentManager;

    /**
     * Constructor for initializing the StaffAdapter.
     * @param staffList The list of staff members.
     * @param context The context of the application.
     */
    public StaffAdapter(ArrayList<StaffMemberModel> staffList, Context context, FragmentManager fragmentManager) {
        this.staffList = staffList;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_staff, parent, false);
        return new StaffViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        StaffMemberModel staffMember = staffList.get(position);
        holder.nameTextView.setText(staffMember.getName());
        holder.ruleTextView.setText(staffMember.getRule());
        holder.startWorkingDateTextView.setText(staffMember.getStartWorkingDate());

        holder.btnUpdate.setOnClickListener(v -> {
            String staffMemberId = String.valueOf(staffMember.getId());
            Bundle args = new Bundle();
            args.putString("staffMemberId", staffMemberId);
            FragmentHelper.replaceFragment(fragmentManager, R.id.kindergarten_manager_fragment, new UpdateStaffFragment(), args);

        });

        holder.btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(holder.itemView, position, staffMember);
        });
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    /**
     * Shows a confirmation dialog to verify the deletion of a staff member.
     * @param view The view to find a parent from.
     * @param position The position of the staff member in the list.
     * @param staffMember The staff member to be deleted.
     */
    private void showDeleteConfirmationDialog(View view, int position, StaffMemberModel staffMember) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this staff member?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    boolean wasDeleted = DatabaseController.getInstance(context).deleteStaffMember(staffMember);
                    if(wasDeleted) {
                        staffList.remove(position);
                        notifyItemRemoved(position);
                        SnackbarHelper.sendSuccessMessage(view, "Staff member deleted");
                    } else {
                        SnackbarHelper.sendErrorMessage(view, "Failed to delete Staff member!");
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * ViewHolder class for staff members.
     */
    public static class StaffViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView ruleTextView;
        public TextView startWorkingDateTextView;
        public Button btnUpdate;
        public Button btnDelete;

        /**
         * Constructor for initializing the StaffViewHolder.
         * @param view The view representing the staff member item.
         */
        public StaffViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.text_view_name);
            ruleTextView = view.findViewById(R.id.text_view_rule);
            startWorkingDateTextView = view.findViewById(R.id.text_view_start_working_date);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}

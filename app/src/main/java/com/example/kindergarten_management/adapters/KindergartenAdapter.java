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
import com.example.kindergarten_management.helpers.AuthHelper;
import com.example.kindergarten_management.helpers.FragmentHelper;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.KindergartenModel;
import com.example.kindergarten_management.users.AdminUser;
import com.example.kindergarten_management.users.BaseUser;
import com.example.kindergarten_management.users.KindergartenManagerUser;
import com.example.kindergarten_management.views.fragments.UpdateClassFragment;
import com.example.kindergarten_management.views.fragments.UpdateKindergartenFragment;

import java.util.ArrayList;

/**
 * Adapter class for displaying kindergartens in a RecyclerView.
 */
public class KindergartenAdapter extends RecyclerView.Adapter<KindergartenAdapter.KindergartenViewHolder> {
    private final ArrayList<KindergartenModel> kindergartenList;
    private final Context context;
    private final FragmentManager fragmentManager;
    private boolean haveChangePermissions = false;


    /**
     * Constructor for initializing the KindergartenAdapter.
     * @param kindergartenList The list of kindergartens.
     * @param context The context of the application.
     * @param fragmentManager The fragment manager to handle fragment transactions.
     */
    public KindergartenAdapter(ArrayList<KindergartenModel> kindergartenList, Context context, FragmentManager fragmentManager) {
        this.kindergartenList = kindergartenList;
        this.context = context;
        this.fragmentManager = fragmentManager;

        BaseUser currentUser = AuthHelper.currentUser;
        if (currentUser instanceof AdminUser || currentUser instanceof KindergartenManagerUser) {
            haveChangePermissions = true;
        }
    }

    @NonNull
    @Override
    public KindergartenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kindergarten, parent, false);
        return new KindergartenViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KindergartenViewHolder holder, int position) {
        KindergartenModel kindergarten = kindergartenList.get(position);

        holder.textViewName.setText(kindergarten.getName());
        holder.textViewAddress.setText(kindergarten.getAddress());
        holder.textViewCityName.setText(kindergarten.getCityName());
        holder.textViewPhoneNumber.setText(kindergarten.getPhoneNumber());
        holder.textViewOpeningTime.setText(kindergarten.getOpeningTime());
        holder.textViewClosingTime.setText(kindergarten.getClosingTime());
        holder.textViewOrganizationalAffiliation.setText(kindergarten.getOrganizationalAffiliation());

        if (haveChangePermissions) {
            holder.btnUpdate.setOnClickListener(view -> {
                String kindergartenId = String.valueOf(kindergarten.getId());
                Bundle args = new Bundle();
                args.putString("kindergartenId", kindergartenId);
                FragmentHelper.replaceFragment(fragmentManager, R.id.kindergarten_manager_fragment, new UpdateKindergartenFragment(), args);
            });

            holder.btnDelete.setOnClickListener(view -> {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Kindergarten")
                        .setMessage("Are you sure you want to delete this kindergarten?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            boolean isDeleted = DatabaseController.getInstance(view.getContext()).deleteKindergarten(kindergarten);

                            if (isDeleted) {
                                kindergartenList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, kindergartenList.size());
                                SnackbarHelper.sendSuccessMessage(view, "Kindergarten deleted successfully");
                            } else {
                                SnackbarHelper.sendErrorMessage(view, "Failed to delete kindergarten");
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            });
        } else {
            holder.btnUpdate.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return kindergartenList.size();
    }

    /**
     * ViewHolder class for managing item views.
     */
    public static class KindergartenViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewAddress;
        private final TextView textViewCityName;
        private final TextView textViewPhoneNumber;
        private final TextView textViewOpeningTime;
        private final TextView textViewClosingTime;
        private final TextView textViewOrganizationalAffiliation;
        private final Button btnUpdate;
        private final Button btnDelete;

        /**
         * Constructor for initializing the ViewHolder.
         * @param itemView The item view.
         */
        public KindergartenViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewAddress = itemView.findViewById(R.id.text_view_address);
            textViewCityName = itemView.findViewById(R.id.text_view_city_name);
            textViewPhoneNumber = itemView.findViewById(R.id.text_view_phone_number);
            textViewOpeningTime = itemView.findViewById(R.id.text_view_opening_time);
            textViewClosingTime = itemView.findViewById(R.id.text_view_closing_time);
            textViewOrganizationalAffiliation = itemView.findViewById(R.id.text_view_organizational_affiliation);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}

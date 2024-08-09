package com.example.kindergarten_management.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kindergarten_management.R;
import com.example.kindergarten_management.helpers.SnackbarHelper;
import com.example.kindergarten_management.models.ClassModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter class for displaying a list of ClassModel objects in a RecyclerView
 * and managing a selection of up to 3 favorite classes.
 */
public class FavoritesClassesAdapter extends RecyclerView.Adapter<FavoritesClassesAdapter.FavoritesViewHolder> {

    private final List<ClassModel> filteredClasses;
    private final List<ClassModel> selectedFavorites = new ArrayList<>();

    /**
     * Constructor for the FavoritesActivitiesAdapter.
     */
    public FavoritesClassesAdapter(List<ClassModel> filteredClasses) {
        this.filteredClasses = filteredClasses;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new FavoritesViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_class, parent, false);
        return new FavoritesViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the ViewHolder to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        ClassModel classModel = filteredClasses.get(position);
        holder.idTextView.setText(String.valueOf(classModel.getId()));
        holder.typeTextView.setText(classModel.getType());
        holder.maxChildrenTextView.setText(String.valueOf(classModel.getMaxChildren()));
        holder.ageRangeTextView.setText(classModel.getMinAge() + " - " + classModel.getMaxAge());
        holder.belongsKindergartenTextView.setText(classModel.getKindergarten().getName());
        holder.radioButtonSelect.setChecked(selectedFavorites.contains(classModel));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return filteredClasses.size();
    }

    /**
     * Returns a list of selected favorite ClassModel objects.
     *
     * @return A list of selected favorite ClassModel objects.
     */
    public List<ClassModel> getSelectedFavorites() {
        return selectedFavorites;
    }

    /**
     * Resets the selection of favorite activities.
     */
    public void reset() {
        selectedFavorites.clear();
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for FavoritesActivitiesAdapter to hold item views.
     */
    class FavoritesViewHolder extends RecyclerView.ViewHolder {

        public TextView idTextView;
        public TextView typeTextView;
        public TextView maxChildrenTextView;
        public TextView ageRangeTextView;
        public TextView belongsKindergartenTextView;
        private final RadioButton radioButtonSelect;

        /**
         * Constructor for initializing the ClassViewHolder.
         * @param itemView The view representing the class item.
         */
        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.text_view_id);
            typeTextView = itemView.findViewById(R.id.text_view_type);
            maxChildrenTextView = itemView.findViewById(R.id.text_view_max_children);
            ageRangeTextView = itemView.findViewById(R.id.text_view_age_range);
            belongsKindergartenTextView = itemView.findViewById(R.id.text_view_belongs_to_kindergarten);
            radioButtonSelect = itemView.findViewById(R.id.radio_button_select);

            radioButtonSelect.setOnClickListener(v -> {
                ClassModel classModel = filteredClasses.get(getAdapterPosition());
                if (radioButtonSelect.isChecked() && selectedFavorites.size() < 3) {
                    if (selectedFavorites.contains(classModel)) {
                        selectedFavorites.remove(classModel);
                        radioButtonSelect.setChecked(false);
                    } else {
                        selectedFavorites.add(classModel);
                    }
                } else {
                    if (selectedFavorites.contains(classModel)) {
                        selectedFavorites.remove(classModel);
                    } else {
                        SnackbarHelper.sendErrorMessage(itemView, "You can only select up to 3 favorite classes.");
                    }
                    radioButtonSelect.setChecked(false);
                }
            });
        }

    }
}

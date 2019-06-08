package com.example.sandwichclub;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Sandwich list adapter for the main landing page
 */
public class SandwichListAdapter extends RecyclerView.Adapter<SandwichListAdapter.SandwichHolder> {

    private final static String TAG = SandwichListAdapter.class.getSimpleName();

    private List<String> sandwichNames;
    //private Context mContext;

    final private ListItemClickListener mOnClickListener;

    private int mNumberItems;

    /**
     * listener interface
     */
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    /**
     * adapter constructor for setting the following
     * @param sandwichNames this is the list of all names from the backend
     * @param listener the listener class from the main activity class
     */
    public SandwichListAdapter(List<String> sandwichNames,
                               ListItemClickListener listener) {
        this.sandwichNames = sandwichNames;
       // mContext = context;

        mOnClickListener = listener;
        mNumberItems = sandwichNames.size();
    }

    /**
     * Binding the data to viewholder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull SandwichHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called!");
        holder.bind(sandwichNames.get(position));
    }

    /**
     * getting the number of items of the viewholder
     * @return number of items
     */
    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    /**
     * creating and inflating the layout
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public SandwichHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.sandwich_list_item, viewGroup, false);
        SandwichHolder holder = new SandwichHolder(view);

        return holder;
    }

    /**
     * Sandwich list holder class
     */
    class SandwichHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView mSandwichName;
        FrameLayout mSandwichHolder;

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

        public SandwichHolder(@NonNull View itemView) {
            super(itemView);

            mSandwichName = itemView.findViewById(R.id.tv_sandwich_name);
            mSandwichHolder = itemView.findViewById(R.id.sandwich_holder);

            itemView.setOnClickListener(this);
        }

        void bind(String name){
            mSandwichName.setText(name);
        }
    }
}

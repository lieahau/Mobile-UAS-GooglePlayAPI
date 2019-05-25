package id.ac.umn.budimanputrajaya;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AppRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<POJOApplication> appList;
    private OnAppListener mOnAppListener;

    public AppRecyclerAdapter(Context context, ArrayList<POJOApplication> appList, OnAppListener onAppListener){
        this.context = context;
        this.appList = appList;
        this.mOnAppListener = onAppListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_app_list_item, parent, false);

        return new RecyclerListHolder(view, mOnAppListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerListHolder recyclerListHolder = (RecyclerListHolder) holder;
        POJOApplication app = appList.get(position);

        recyclerListHolder.name.setText(app.getName());
        recyclerListHolder.publisherName.setText(app.getPublisherName());
        recyclerListHolder.rating.setText(app.getRating());
        recyclerListHolder.price.setText(app.getPrice());

        Picasso.get()
                .load(app.getIconUrl())
                .error(android.R.drawable.alert_dark_frame)
                .placeholder(android.R.drawable.alert_dark_frame)
                .into(recyclerListHolder.icon);
    }

    @Override
    public int getItemCount(){
        return (appList != null ? appList.size() : 0);
    }

    public void setAppList(ArrayList<POJOApplication> appList){
        this.appList = appList;
        notifyDataSetChanged();
    }

    private class RecyclerListHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private OnAppListener onAppListener;
        private ImageView icon;
        private TextView name;
        private TextView publisherName;
        private TextView rating;
        private TextView price;

        private RecyclerListHolder(View view, OnAppListener onAppListener){
            super(view);

            icon = view.findViewById(R.id.icon);
            name = view.findViewById(R.id.name);
            publisherName = view.findViewById(R.id.publishername);
            rating = view.findViewById(R.id.rating);
            price = view.findViewById(R.id.price);
            this.onAppListener = onAppListener;

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAppListener.onAppClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v){
            onAppListener.onAppLongClick(getAdapterPosition());
            return true;
        }
    }

    public interface OnAppListener{
        void onAppClick(int position);
        void onAppLongClick(int position);
    }
}

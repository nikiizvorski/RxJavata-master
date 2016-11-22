package app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rxjavata.app.R;

import java.util.ArrayList;
import java.util.List;

import app.model.FlowerResponse;


public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.Holder> {

    private final LayoutInflater mInflater;
    private List<FlowerResponse> mFlowerList;
    private FlowerClickListener mListener;

    public FlowerAdapter(LayoutInflater inflater) {
        mInflater = inflater;
        mFlowerList = new ArrayList<FlowerResponse>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mInflater.inflate(R.layout.recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        FlowerResponse currFlower = mFlowerList.get(position);

        holder.mName.setText(currFlower.getName());
        holder.mPrice.setText(String.format("$%.2f", currFlower.getPrice()));

        Glide.with(holder.itemView.getContext()).load(Constant.PHOTO_URL + currFlower.getPhoto()).into(holder.mPhoto);
    }

    @Override
    public int getItemCount() {
        return this.mFlowerList.size();
    }

    public void addFlowers(List<FlowerResponse> flowerResponses) {
        mFlowerList.addAll(flowerResponses);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhoto;
        private TextView mName, mPrice;

        public Holder(View itemView) {
            super(itemView);
            mPhoto = (ImageView) itemView.findViewById(R.id.imageView);
            mName = (TextView) itemView.findViewById(R.id.login);
            mPrice = (TextView) itemView.findViewById(R.id.repos);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getLayoutPosition(), mFlowerList.get(getAdapterPosition()).getName());
        }
    }

    public interface FlowerClickListener {

        void onClick(int position, String name);
    }
}

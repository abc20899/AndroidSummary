
package info.qianlong.interview.customview.main;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.qianlong.interview.R;

/**
 * Created by android on 17/1/7.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.Holder> {

    public Context context;

    public String[] dataList;

    public MainAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(String[] dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder = new Holder(
                LayoutInflater.from(context).inflate(R.layout.item_main, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if (dataList != null) {
            holder.textData.setText(dataList[position].replace("cn.junechiu.androiddemo.", ""));

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClick(position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.length;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textData;

        public Holder(View itemView) {
            super(itemView);
            textData = (TextView)itemView.findViewById(R.id.text_view);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}

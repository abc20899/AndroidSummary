package cn.junechiu.architecturedemo.demozhihu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import cn.junechiu.architecturedemo.R;
import cn.junechiu.architecturedemo.demozhihu.data.local.db.entity.ZhihuStory;
import cn.junechiu.architecturedemo.demozhihu.ui.listener.OnItemClickListener;

/**
 * ZhihuListAdapter.java
 */
public class ZhihuListAdapter extends RecyclerView.Adapter<ZhihuListAdapter.ZhihuViewHolder> {

    private OnItemClickListener<ZhihuStory> mZhihuOnItemClickListener = null;

    private Context mContext = null;

    private List<ZhihuStory> mStoryList = null;

    public ZhihuListAdapter(Context context, OnItemClickListener<ZhihuStory> listener) {
        mStoryList = new ArrayList<>();
        mContext = context;
        mZhihuOnItemClickListener = listener;
    }

    @Override
    public ZhihuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZhihuViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_zhihu_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ZhihuViewHolder holder, int position) {
        final ZhihuStory zhihuStory = mStoryList.get(position);
        holder.getTVZhihuTitle().setText(zhihuStory.getTitle());
        holder.getTVZhihuTime().setText(zhihuStory.getGa_prefix());
        holder.getLLZhihu().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mZhihuOnItemClickListener != null) {
                    mZhihuOnItemClickListener.onClick(zhihuStory);
                }
            }
        });
        Glide.with(mContext)
                .load(zhihuStory.getImages()[0])
                .into(holder.getIVZhihu());
    }

    @Override
    public int getItemCount() {
        return mStoryList.size();
    }

    public void setStoryList(List<ZhihuStory> storyList) {
        if (storyList == null || storyList.size() == 0) {
            return;
        }
        mStoryList.addAll(storyList);
        notifyDataSetChanged();
    }

    public void clearStoryList() {
        mStoryList.clear();
        notifyDataSetChanged();
    }

    class ZhihuViewHolder extends RecyclerView.ViewHolder {

        private View mLLZhihu;
        private TextView mTVZhihuTitle;
        private TextView mTVZhihuTime;
        private ImageView mIVZhihu;

        private ZhihuViewHolder(View itemView) {
            super(itemView);
            mLLZhihu = itemView.findViewById(R.id.ll_zhihu);
            mTVZhihuTitle = (TextView) itemView.findViewById(R.id.tv_zhihu_title);
            mTVZhihuTime = (TextView) itemView.findViewById(R.id.tv_zhihu_time);
            mIVZhihu = (ImageView) itemView.findViewById(R.id.iv_zhihu);
        }

        private View getLLZhihu() {
            return mLLZhihu;
        }

        private TextView getTVZhihuTitle() {
            return mTVZhihuTitle;
        }

        private TextView getTVZhihuTime() {
            return mTVZhihuTime;
        }

        private ImageView getIVZhihu() {
            return mIVZhihu;
        }
    }
}

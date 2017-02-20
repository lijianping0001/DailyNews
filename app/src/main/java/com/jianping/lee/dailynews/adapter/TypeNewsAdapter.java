package com.jianping.lee.dailynews.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianping.lee.dailynews.R;
import com.jianping.lee.dailynews.engine.ImageLoader;
import com.jianping.lee.dailynews.model.http.HttpService;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Li on 2017/2/17.
 */
public class TypeNewsAdapter extends RecyclerView.Adapter<TypeNewsAdapter.ViewHolder>{

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    private Context mContext;

    private int lastPosition  = -1;

    private OnItemClickListener mOnItemClickListner;

    private List<HttpService.Result.ResultBean.DataBean> mList;

    public void setOnItemClickListner(OnItemClickListener mOnItemClickListner) {
        this.mOnItemClickListner = mOnItemClickListner;
    }

    public TypeNewsAdapter(Context context, List<HttpService.Result.ResultBean.DataBean> list){
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        HttpService.Result.ResultBean.DataBean dataBean = mList.get(position);

        ImageLoader.load(mContext, dataBean.getThumbnail_pic_s(), holder.ivImage);
        holder.tvTitle.setText(dataBean.getTitle());
        holder.tvAuthor.setText(dataBean.getAuthor_name());
        holder.tvDate.setText(dataBean.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListner != null){
                    ImageView imageView = (ImageView) v.findViewById(R.id.iv_type_news_image);
                    mOnItemClickListner.onItemClick(imageView, position);
                }
            }
        });

//        setAnimation(holder.cvCardView, position);
    }

    private void setAnimation(View viewToAnimation, int position){
        if (position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(viewToAnimation.getContext(), R.anim.item_bottom_in);
            viewToAnimation.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.iv_type_news_image)
        ImageView ivImage;

        @InjectView(R.id.tv_type_news_title)
        TextView tvTitle;

        @InjectView(R.id.tv_type_news_author)
        TextView tvAuthor;

        @InjectView(R.id.tv_type_news_date)
        TextView tvDate;

        @InjectView(R.id.cv_type_news_card)
        CardView cvCardView;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}

package com.riontech.demoproject.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.riontech.demoproject.AppConstants;
import com.riontech.demoproject.R;
import com.riontech.demoproject.custom.likebutton.LikeButtonView;
import com.riontech.demoproject.dao.Dao;
import com.riontech.demoproject.ui.activity.MainActivity;

import java.util.List;

/**
 * Created by Dhaval Soneji Riontech on 27/9/16.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Dao> mList;
    private LayoutInflater layoutInflater;
    private Animation mShakeAnimation;

    public ListAdapter(Context context, List<Dao> list) {
        mContext = context;
        mList = list;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mShakeAnimation = AnimationUtils.loadAnimation(mContext, R.anim.shake);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            if (mList.get(position).getType() == AppConstants.LIST_TYPE_LAST) {
                convertView = layoutInflater.inflate(R.layout.row_item_footer, parent, false);
                viewHolder = new ViewHolder();
            } else {
                if (position == 0 || position % 3 == 0)
                    convertView = layoutInflater.inflate(R.layout.row_item, parent, false);
                else
                    convertView = layoutInflater.inflate(R.layout.row_item_dual, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.productTitle = (TextView) convertView.findViewById(R.id.productTitle);
                viewHolder.likeButtonView = (LikeButtonView) convertView.findViewById(R.id.imgLike);
                viewHolder.productImage = (ImageView) convertView.findViewById(R.id.imgProduct);
                viewHolder.imgCall = (ImageView) convertView.findViewById(R.id.imgCall);
                viewHolder.cardView = (CardView) convertView.findViewById(R.id.cardView);
                if (mList.get(position).getType() == AppConstants.LIST_TYPE_ONE) {
                    viewHolder.txtShare = (TextView) convertView.findViewById(R.id.txtShare);
                    viewHolder.txtExplore = (TextView) convertView.findViewById(R.id.txtExplore);
                }
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mList.get(position).getType() != AppConstants.LIST_TYPE_LAST) {
            if (mList.get(position).getLike() == AppConstants.LIKED) {
                viewHolder.likeButtonView.setImageDrawable(mContext, R.drawable.ic_liked);
            } else {
                viewHolder.likeButtonView.setImageDrawable(mContext, R.drawable.ic_like);
            }
            viewHolder.likeButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mList.get(position).getLike() == AppConstants.LIKED) {
                        viewHolder.likeButtonView.setImageDrawable(mContext, R.drawable.ic_like);
                        mList.get(position).setLike(AppConstants.NOT_LIKED);
                    } else {
                        viewHolder.likeButtonView.startAnimation();
                        viewHolder.likeButtonView.setImageDrawable(mContext, R.drawable.ic_liked);
                        mList.get(position).setLike(AppConstants.LIKED);
                    }
                }
            });
            viewHolder.imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.imgCall.startAnimation(mShakeAnimation);
                }
            });
            if (mList.get(position).getType() == AppConstants.LIST_TYPE_ONE) {
                viewHolder.txtShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) mContext).showToast(mContext.getResources().getString(
                                R.string.toast_message, mContext.getString(R.string.share)
                        ));
                    }
                });
                viewHolder.txtExplore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) mContext).showToast(mContext.getResources().getString(
                                R.string.toast_message, mContext.getString(R.string.explore)
                        ));
                    }
                });
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView productTitle;
        LikeButtonView likeButtonView;
        ImageView productImage;
        ImageView imgCall;
        TextView txtShare;
        TextView txtExplore;
        CardView cardView;
    }
}

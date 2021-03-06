package com.onezao.zao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onezao.zao.bean.DailyNotesInfo;
import com.onezao.zao.recycleview0306.OnItemClickListener;
import com.onezao.zao.zaov.R;

import java.util.List;


public class MyNotesAdapter extends RecyclerView.Adapter<MyNotesAdapter.ViewHolder>{

    private Context context;
    private List<DailyNotesInfo> mList;
    private ItemImageViewInterface imageViewInterface;

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_dailynotes_pic;
        public TextView tv_dailynotes_title;
        public TextView tv_dailynotes_author;
        public TextView tv_dailynotes_email;
        public TextView tv_dailynotes_time;
        public TextView tv_dailynotes_content;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_dailynotes_pic=(ImageView)itemView.findViewById(R.id.iv_dailynotes_pic);
            tv_dailynotes_title=(TextView)itemView.findViewById(R.id.tv_dailynotes_title);
            tv_dailynotes_author=(TextView)itemView.findViewById(R.id.tv_dailynotes_author);
            tv_dailynotes_email=(TextView)itemView.findViewById(R.id.tv_dailynotes_email);
            tv_dailynotes_time=(TextView)itemView.findViewById(R.id.tv_dailynotes_time);
            tv_dailynotes_content=(TextView)itemView.findViewById(R.id.tv_dailynotes_content);

            //设置图片的宽高
            ViewGroup.LayoutParams params = iv_dailynotes_pic.getLayoutParams();
            params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            iv_dailynotes_pic.setLayoutParams(params);
        }
    }

    public MyNotesAdapter(Context context, List<DailyNotesInfo> list){
        this.context=context;
        this.mList=list;
    }

    //加载item 的布局  创建ViewHolder实例
    @Override
    public MyNotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_diarynotes_recycleview_0306,null);
        return new MyNotesAdapter.ViewHolder(view);
    }

    //对RecyclerView子项数据进行赋值
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

//        String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
//        String url = "http://p1.pstatp.com/large/166200019850062839d3";
//        String url = "https://upfile.asqql.com/2009pasdfasdfic2009s305985-ts/2018-8/201881110474228420.gif";
        String url = mList.get(position).getPicpath();
        Glide.with(context)
                .load(url)
/*                .placeholder(R.mipmap.ic_tab_group_active)//加载成功前显示的图片
                .error(R.mipmap.ic_tab_home_active)//异常时候显示的图片
                .fallback(R.mipmap.ic_tab_profile_active)//url为空的时候，显示的图片
                .diskCacheStrategy(DiskCacheStrategy.NONE)  //不使用缓存*/
                .into(holder.iv_dailynotes_pic);
//        Glide.with(context).load(url).into(holder.iv_dailynotes_pic);

//        holder.iv_dailynotes_pic.setImageResource(R.mipmap.ic_launcher);
        holder.tv_dailynotes_title.setText(mList.get(position).getTitle());
        holder.tv_dailynotes_author.setText(mList.get(position).getAuthor());
        holder.tv_dailynotes_email.setText(mList.get(position).getEmail());
        holder.tv_dailynotes_time.setText(mList.get(position).getTime());
        holder.tv_dailynotes_content.setText(mList.get(position).getContent());

/*        holder.itemView.setTag(mList.get(position).getAuthor());
        holder.iv_dailynotes_pic.setTag(mList.get(position).getEmail());*/

        //条目点击事件增加的
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.onItemLongClick(v, position);
                return true;
            }
        });

        //条目item中的ImageView 的点击事件
        holder.iv_dailynotes_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageViewInterface !=null) {
//                  接口实例化后的而对象，调用重写后的方法
                    imageViewInterface.onclick(v,position);
                }
            }
        });
    }

    //返回子项个数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 声明条目点击事件的接口
     */
    public OnItemClickListener mOnItemClickListener;//声明接口

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     *按钮点击事件需要的方法
     */
    public void setOnItemImageViewClickListener(ItemImageViewInterface itemImageViewInterface){
        this.imageViewInterface = itemImageViewInterface;
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface ItemImageViewInterface {
        void onclick(View view, int position);
    }

}

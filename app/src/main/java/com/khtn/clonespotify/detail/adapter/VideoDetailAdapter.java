package com.khtn.clonespotify.detail.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.khtn.clonespotify.R;
import com.khtn.clonespotify.model.Video;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<Video> videos;
    private static int TYPE_HEADER = 0;
    private static int TYPE_ITEM = 1;
    private boolean isShowDescripion = false;

    public VideoDetailAdapter(List<Video> videos) {
        this.videos = videos;
    }
    public class VideoHeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title_header)
        TextView title;
        @BindView(R.id.view_header)
        TextView views;
        @BindView(R.id.description_header)
        TextView description;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.btn_show_detail)
        ImageView btnShowDetailVideo;
        public VideoHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bind(Video video){
            title.setText("[Vietsub] Sorry 对不起 Xin Lỗi Anh - 胡艾彤 (Hồ Ngải Đồng) ♪Song Lục♪");
            views.setText("2000 views");
            btnShowDetailVideo.setOnClickListener(v->{
                description.setText("If you like their music, please support genuine version. If the copyright owner considers the video infringed, please contact us and we will completely delete the video.");
                if(isShowDescripion == false){
                    isShowDescripion = true;
                    line.setVisibility(View.VISIBLE);
                    description.setVisibility(View.VISIBLE);
                    btnShowDetailVideo.setImageResource(R.drawable.ic_arrow_drop_up);
                } else {
                    isShowDescripion = false;
                    line.setVisibility(View.GONE);
                    description.setVisibility(View.GONE);
                    btnShowDetailVideo.setImageResource(R.drawable.ic_arrow_drop_down);
                }

            });
        }
    }
    public class VideoItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.poster_rec)
        ImageView poster;
        @BindView(R.id.title_rec)
        TextView title;
        @BindView(R.id.view_rec)
        TextView views;
        @BindView(R.id.description_rec)
        TextView description;
        @BindView(R.id.loading_progress)
        ProgressBar loadingImageProgressBar;
        public VideoItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(Video video){
            loadingImageProgressBar.setVisibility(View.GONE);
            poster.setImageResource(R.drawable.bg);
            title.setText("Reality - Lost Frequencies | Lyrics + Vietsub.");
            description.setText("A.e tuần mới tràn đầy nhé. :3 Tải gì vào đây nha :\n" +
                    "https://drive.google.com/drive/folder...\n" +
                    "\n" +
                    "Góp ý nhiệt tình đừng ngần ngại nha ae. Bài này chém gió kinh lắm. Cảm tạ ae.  :))");

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if(viewType == TYPE_HEADER){
            View view = layoutInflater.inflate(R.layout.video_header, parent, false);
            return new VideoHeaderViewHolder(view);
        } else if(viewType == TYPE_ITEM){
            View view = layoutInflater.inflate(R.layout.video_detail_item, parent, false);
            return new VideoItemViewHolder(view);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Video video = videos.get(position);
        if(holder instanceof VideoHeaderViewHolder){
            ((VideoHeaderViewHolder)holder).bind(video);
        } else if(holder instanceof VideoItemViewHolder) {
            ((VideoItemViewHolder) holder).bind(video);
        }
    }

    @Override
    public int getItemCount() {
        return (videos == null)? 0: videos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position)){
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }
    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}

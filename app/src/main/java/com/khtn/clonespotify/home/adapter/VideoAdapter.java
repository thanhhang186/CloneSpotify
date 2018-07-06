package com.khtn.clonespotify.home.adapter;

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
import com.khtn.clonespotify.home.view.HomeView;
import com.khtn.clonespotify.model.Video;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<Video> videos;
    private HomeView homeView;

    public VideoAdapter(List<Video> videos, HomeView homeView) {
        this.videos = videos;
        this.homeView = homeView;
    }
    public class VideoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.poster)
        ImageView poster;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.loading_image_progress)
        ProgressBar loadingImageProgressBar;
        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(final Video video){
            loadingImageProgressBar.setVisibility(View.GONE);
            poster.setImageResource(R.drawable.bg);
            title.setText("Reality - Lost Frequencies | Lyrics + Vietsub.");
            tvDescription.setText("A.e tuần mới tràn đầy nhé. :3 Tải gì vào đây nha :\n" +
                    "https://drive.google.com/drive/folder...\n" +
                    "\n" +
                    "Góp ý nhiệt tình đừng ngần ngại nha ae. Bài này chém gió kinh lắm. Cảm tạ ae.  :))");
            itemView.setOnClickListener(v-> homeView.onVideoClicked(video));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;
        View view = layoutInflater.inflate(R.layout.video_item, parent, false);
        viewHolder = new VideoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Video video = videos.get(position);
        ((VideoViewHolder) holder).bind(video);
    }

    @Override
    public int getItemCount() {
            return (videos == null) ? 0 : videos.size();
    }
}

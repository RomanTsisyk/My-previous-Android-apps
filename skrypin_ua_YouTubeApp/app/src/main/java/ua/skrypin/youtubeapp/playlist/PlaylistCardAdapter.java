package ua.skrypin.youtubeapp.playlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatistics;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import ua.skrypin.youtubeapp.PlayerActivity;
import ua.skrypin.youtubeapp.R;
import ua.skrypin.youtubeapp.YouTubeRecyclerViewFragment;


public class PlaylistCardAdapter extends RecyclerView.Adapter<PlaylistCardAdapter.ViewHolder> {
    private static final DecimalFormat sFormatter = new DecimalFormat("#,###,###");
    private final PlaylistVideos mPlaylistVideos;
    private final YouTubeRecyclerViewFragment.LastItemReachedListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;
        public final TextView mTitleText;
        public final ImageView mThumbnailImage;
        public final ImageView mShareIcon;
        public final TextView mShareText;
        public final TextView mDurationText;
        public final TextView mViewCountText;


        public ViewHolder(View v) {
            super(v);
            mContext = v.getContext();
            mTitleText = (TextView) v.findViewById(R.id.video_title);
            mThumbnailImage = (ImageView) v.findViewById(R.id.video_thumbnail);
            mShareIcon = (ImageView) v.findViewById(R.id.video_share);
            mShareText = (TextView) v.findViewById(R.id.video_share_text);
            mDurationText = (TextView) v.findViewById(R.id.video_dutation_text);
            mViewCountText= (TextView) v.findViewById(R.id.video_view_count);
        }
    }

    public PlaylistCardAdapter(PlaylistVideos playlistVideos, YouTubeRecyclerViewFragment.LastItemReachedListener lastItemReachedListener) {
        mPlaylistVideos = playlistVideos;
        mListener = lastItemReachedListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a card layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_video_card, parent, false);
        // populate the viewholder
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mPlaylistVideos.size() == 0) {
            return;
        }

        final Video video = mPlaylistVideos.get(position);
        final VideoSnippet videoSnippet = video.getSnippet();
        final VideoContentDetails videoContentDetails = video.getContentDetails();
        final VideoStatistics videoStatistics = video.getStatistics();

        holder.mTitleText.setText(videoSnippet.getTitle());

        Picasso.with(holder.mContext)
                .load(videoSnippet.getThumbnails().getHigh().getUrl())
                .placeholder(R.drawable.video_placeholder)
                .into(holder.mThumbnailImage);

        holder.mThumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  intent = new Intent(holder.mContext, PlayerActivity.class);
                intent.putExtra("video_id", video.getId());
                intent.putExtra("video_description", videoSnippet.getDescription());
                holder.mContext.startActivity(intent);

            }
        });

        View.OnClickListener shareClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Watch \"" + videoSnippet.getTitle() + "\" on YouTube");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + video.getId());
                sendIntent.setType("text/plain");
                holder.mContext.startActivity(sendIntent);
            }
        };

        holder.mShareIcon.setOnClickListener(shareClickListener);
        holder.mShareText.setOnClickListener(shareClickListener);
        holder.mDurationText.setText(parseDuration(videoContentDetails.getDuration()));
        holder.mViewCountText.setText(sFormatter.format(videoStatistics.getViewCount()));
        if (mListener != null) {
            final String nextPageToken = mPlaylistVideos.getNextPageToken();
            if (!isEmpty(nextPageToken) && position == mPlaylistVideos.size() - 1) {
                holder.itemView.post(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onLastItem(position, nextPageToken);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPlaylistVideos.size();
    }

    private boolean isEmpty(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        return false;
    }

    private String parseDuration(String in) {
        boolean hasSeconds = in.indexOf('S') > 0;
        boolean hasMinutes = in.indexOf('M') > 0;

        String s;
        if (hasSeconds) {
            s = in.substring(2, in.length() - 1);
        } else {
            s = in.substring(2, in.length());
        }

        String minutes = "0";
        String seconds = "00";

        if (hasMinutes && hasSeconds) {
            String[] split = s.split("M");
            minutes = split[0];
            seconds = split[1];
        } else if (hasMinutes) {
            minutes = s.substring(0, s.indexOf('M'));
        } else if (hasSeconds) {
            seconds = s;
        }

        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }

        return minutes + ":" + seconds;
    }
}

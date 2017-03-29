package com.github.marlonlom.heckel;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.marlonlom.heckel.net.ImageDownloadAsyncTask;

/**
 * Image downloader main class.
 *
 * @author marlonlom
 */
public final class Heckel {

    public static Builder load(final String url) {
        return (Builder) new Builder().load(url);
    }

    /**
     * Interface for specifying image download target behaviour.
     *
     * @author marlonlom
     */
    interface IWithImageUrl {

        /**
         * prepares the imageview for receive the downloaded image.
         *
         * @param imageView the image view widget
         */
        void into(final ImageView imageView);
    }

    /**
     * Fluent builder class for loading images
     *
     * @author marlonlom
     */
    public static class Builder implements IWithImageUrl {
        private final ImageDownloadAsyncTask mImageDownloadTask;
        private String mImageUrl;

        private Builder() {
            this.mImageDownloadTask = new ImageDownloadAsyncTask();
        }

        public IWithImageUrl load(final String url) {
            this.mImageUrl = url;
            return this;
        }

        @Override
        public void into(final ImageView imageView) {
            this.mImageDownloadTask.setListener(
                    new ImageDownloadAsyncTask.ImageDownloadedListener() {
                        @Override
                        public void onImageDownloaded(Bitmap imageContents) {
                            if (imageContents != null) {
                                imageView.setImageBitmap(imageContents);
                            } else {
                                Toast.makeText(imageView.getContext(), R.string.demo_error_text,
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            this.mImageDownloadTask.execute(this.mImageUrl);
        }
    }
}

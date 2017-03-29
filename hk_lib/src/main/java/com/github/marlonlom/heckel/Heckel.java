package com.github.marlonlom.heckel;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.marlonlom.heckel.net.ImageDownloadAsyncTask;

/**
 * Image downloader main class.
 *
 * @author marlonlom
 */
public final class Heckel {

    /**
     * Initializes the builder instance.
     *
     * @param imageView imageview instance
     * @return the builder instance
     */
    public static Builder with(ImageView imageView) {
        return (Builder) new Builder().with(imageView);
    }

    /**
     * Interface for specifying image download target behaviour.
     *
     * @author marlonlom
     */
    interface IWithImageView {

        /**
         * Downloads the image.
         *
         * @param imageUrl the image url
         */
        void load(final String imageUrl);
    }

    /**
     * Fluent builder class for loading images
     *
     * @author marlonlom
     */
    public static class Builder implements IWithImageView {
        private final ImageDownloadAsyncTask mImageDownloadTask;


        private Builder() {
            this.mImageDownloadTask = new ImageDownloadAsyncTask();
        }

        @Override
        public void load(final String imageUrl) {
            this.mImageDownloadTask.execute(imageUrl);
        }

        public IWithImageView with(final ImageView imageView) {
            try {
                if (imageView == null) {
                    throw new Exception("No ImageView defined");
                }
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
            } catch (final Exception exception) {
                Log.e(Heckel.class.getSimpleName(), exception.getMessage(), exception);
            }
            return this;

        }
    }
}

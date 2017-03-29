package com.github.marlonlom.heckel.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.github.marlonlom.heckel.Heckel;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Image download async task for loading images from network
 *
 * @author marlonlom
 */
public final class ImageDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

    /**
     * Constant for Http 200 status code
     */
    private static final int SC_OK = 200;
    /**
     * Bitmap lru cache
     */
    private final BitmapLruCache mBitmapLruCache;
    /**
     * Listener for image downloaded post event.
     */
    private ImageDownloadedListener mListener;

    /**
     * Default constructor
     */
    public ImageDownloadAsyncTask() {
        mBitmapLruCache = new BitmapLruCache();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (mListener != null) {
            mListener.onImageDownloaded(bitmap);
        }
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            if (params.length == 0) {
                throw new Exception("Image url must be set.");
            }
            final String imageUrl = params[0];
            if (!hasImageExtensions(imageUrl)) {
                throw new Exception("Url not related to an image.");
            }
            final Bitmap downloadedBitmap = downloadBitmap(imageUrl);
            if (downloadedBitmap != null) {
                final String generatedId = String.format(Locale.getDefault(), "%d",
                        downloadedBitmap.getGenerationId());
                mBitmapLruCache.addBitmap(generatedId, downloadedBitmap);
                return mBitmapLruCache.getBitmap(generatedId);
            }
        } catch (final Exception exception) {
            Log.e(Heckel.class.getSimpleName(), "Error Executing the async operation", exception);
        }
        return null;
    }

    /**
     * Checks if the url finish with an image valid extension.
     *
     * @param imageUrl the image url to check.
     * @return true/false
     */
    private boolean hasImageExtensions(final String imageUrl) {
        final List<String> imageTypes = Arrays.asList("jpg", "jpeg", "png", "gif");
        final String imageExtension = imageUrl.substring(imageUrl.length() - 3, imageUrl.length());
        return imageTypes.contains(imageExtension);
    }

    /**
     * Downloads bitmap from url
     *
     * @param url image url
     * @return the image downloaded as bitmap
     */
    private Bitmap downloadBitmap(final String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != SC_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (final Exception exception) {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            Log.e(Heckel.class.getSimpleName(), "Error downloading image from " + url, exception);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    /**
     * Changes the listener for image downloaded post event.
     *
     * @param listener the listener
     */
    public void setListener(
            final ImageDownloadedListener listener) {
        this.mListener = listener;
    }

    /**
     * Interface for managing the post-download of the image.
     *
     * @author marlonlom
     */
    public interface ImageDownloadedListener {
        /**
         * Process downloaded image contents.
         *
         * @param imageContents the image contents
         */
        void onImageDownloaded(final Bitmap imageContents);
    }

    /**
     * Image LRU cache for bitmaps
     *
     * @author marlonlom
     * @see <a href="https://goo.gl/cJ5SCB">https://goo.gl/cJ5SCB</a>
     */
    private static final class BitmapLruCache {
        /**
         * The Lru cache instance
         */
        private final LruCache<String, Bitmap> mMemoryCache;

        /**
         * Default constructor
         */
        private BitmapLruCache() {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount() / 1024;
                }
            };
        }

        /**
         * Adds the bitmap to the cache
         *
         * @param key    bitmap unique key reference
         * @param bitmap bitmap contents
         */
        public void addBitmap(String key, Bitmap bitmap) {
            if (getBitmap(key) == null) {
                mMemoryCache.put(key, bitmap);
            }
        }

        /**
         * Gets the bitmap to the cache
         *
         * @param key bitmap unique key reference
         * @return bitmap contents
         */
        public Bitmap getBitmap(String key) {
            return mMemoryCache.get(key);
        }
    }

}

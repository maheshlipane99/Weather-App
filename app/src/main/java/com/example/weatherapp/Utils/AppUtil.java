package com.example.weatherapp.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.os.ConfigurationCompat;

public class AppUtil {

    public static long getStartOfDayTimestamp(Calendar calendar) {
        Calendar newCalendar = Calendar.getInstance(TimeZone.getDefault());
        newCalendar.setTimeInMillis(calendar.getTimeInMillis());
        newCalendar.set(Calendar.HOUR_OF_DAY, 0);
        newCalendar.set(Calendar.MINUTE, 0);
        newCalendar.set(Calendar.SECOND, 0);
        newCalendar.set(Calendar.MILLISECOND, 0);
        return newCalendar.getTimeInMillis();
    }


    public static long getEndOfDayTimestamp(Calendar calendar) {
        Calendar newCalendar = Calendar.getInstance(TimeZone.getDefault());
        newCalendar.setTimeInMillis(calendar.getTimeInMillis());
        newCalendar.set(Calendar.HOUR_OF_DAY, 23);
        newCalendar.set(Calendar.MINUTE, 59);
        newCalendar.set(Calendar.SECOND, 59);
        newCalendar.set(Calendar.MILLISECOND, 0);
        return newCalendar.getTimeInMillis();
    }

    public static Calendar addDays(Calendar cal, int days) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(cal.getTimeInMillis());
        calendar.add(Calendar.DATE, days);
        return calendar;
    }

    public static void setWeatherIcon(Context context, ImageView imageView, int weatherCode) {
        if (weatherCode / 100 == 2) {
            Glide.with(context).load(R.drawable.ic_storm_weather).into(imageView);
        } else if (weatherCode / 100 == 3) {
            Glide.with(context).load(R.drawable.ic_rainy_weather).into(imageView);
        } else if (weatherCode / 100 == 5) {
            Glide.with(context).load(R.drawable.ic_rainy_weather).into(imageView);
        } else if (weatherCode / 100 == 6) {
            Glide.with(context).load(R.drawable.ic_snow_weather).into(imageView);
        } else if (weatherCode / 100 == 7) {
            Glide.with(context).load(R.drawable.ic_unknown).into(imageView);
        } else if (weatherCode == 800) {
            Glide.with(context).load(R.drawable.ic_clear_day).into(imageView);
        } else if (weatherCode == 801) {
            Glide.with(context).load(R.drawable.ic_few_clouds).into(imageView);
        } else if (weatherCode == 803) {
            Glide.with(context).load(R.drawable.ic_broken_clouds).into(imageView);
        } else if (weatherCode / 100 == 8) {
            Glide.with(context).load(R.drawable.ic_cloudy_weather).into(imageView);
        }
    }

    /**
     * Get time of calendar as 00:00 format
     *
     * @param calendar instance of {@link Calendar}
     * @param context  instance of {@link Context}
     * @return string value
     */
    public static String getTime(Calendar calendar, Context context) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String hourString;
        if (hour < 10) {
            hourString = String.format(Locale.getDefault(), context.getString(R.string.zero_label), hour);
        } else {
            hourString = String.format(Locale.getDefault(), "%d", hour);
        }
        String minuteString;
        if (minute < 10) {
            minuteString = String.format(Locale.getDefault(), context.getString(R.string.zero_label), minute);
        } else {
            minuteString = String.format(Locale.getDefault(), "%d", minute);
        }
        return hourString + ":" + minuteString;
    }

    /**
     * Get animation file according to weather status code
     *
     * @param weatherCode int weather status code
     * @return id of animation json file
     */
    public static int getWeatherAnimation(int weatherCode) {
        if (weatherCode / 100 == 2) {
            return R.raw.storm_weather;
        } else if (weatherCode / 100 == 3) {
            return R.raw.rainy_weather;
        } else if (weatherCode / 100 == 5) {
            return R.raw.rainy_weather;
        } else if (weatherCode / 100 == 6) {
            return R.raw.snow_weather;
        } else if (weatherCode / 100 == 7) {
            return R.raw.unknown;
        } else if (weatherCode == 800) {
            return R.raw.clear_day;
        } else if (weatherCode == 801) {
            return R.raw.few_clouds;
        } else if (weatherCode == 803) {
            return R.raw.broken_clouds;
        } else if (weatherCode / 100 == 8) {
            return R.raw.cloudy_weather;
        }
        return R.raw.unknown;
    }

    public static String getWeatherStatus(int weatherCode) {
        if (weatherCode / 100 == 2) {
            return Const.WEATHER_STATUS[0];
        } else if (weatherCode / 100 == 3) {
            return Const.WEATHER_STATUS[1];
        } else if (weatherCode / 100 == 5) {
            return Const.WEATHER_STATUS[2];
        } else if (weatherCode / 100 == 6) {
            return Const.WEATHER_STATUS[3];
        } else if (weatherCode / 100 == 7) {
            return Const.WEATHER_STATUS[4];
        } else if (weatherCode == 800) {
            return Const.WEATHER_STATUS[5];
        } else if (weatherCode == 801) {
            return Const.WEATHER_STATUS[6];
        } else if (weatherCode == 803) {
            return Const.WEATHER_STATUS[7];
        } else if (weatherCode / 100 == 8) {
            return Const.WEATHER_STATUS[8];
        }
        return Const.WEATHER_STATUS[4];

    }

    /**
     * If thirty minutes is pass from parameter return true otherwise return false
     *
     * @param lastStored timestamp
     * @return boolean value
     */
    public static boolean isTimePass(long lastStored) {
        return System.currentTimeMillis() - lastStored > Const.TIME_TO_PASS;
    }


    /**
     * Set text of textView with html format of html parameter
     *
     * @param textView instance {@link TextView}
     * @param html     String
     */
    @SuppressLint("ClickableViewAccessibility")
    public static void setTextWithLinks(TextView textView, CharSequence html) {
        textView.setText(html);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP ||
                        action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    TextView widget = (TextView) v;
                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = Spannable.Factory.getInstance()
                            .newSpannable(widget.getText())
                            .getSpans(off, off, ClickableSpan.class);

                    if (link.length != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            link[0].onClick(widget);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * Change string to html format
     *
     * @param htmlText String text
     * @return String text
     */
    public static CharSequence fromHtml(String htmlText) {
        if (TextUtils.isEmpty(htmlText)) {
            return null;
        }
        CharSequence spanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(htmlText);
        }
        return trim(spanned);
    }

    /**
     * Trim string text
     *
     * @param charSequence String text
     * @return String text
     */

    private static CharSequence trim(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        int end = charSequence.length() - 1;
        while (Character.isWhitespace(charSequence.charAt(end))) {
            end--;
        }
        return charSequence.subSequence(0, end + 1);
    }

    /**
     * Check version of SDK
     *
     * @param version int SDK version
     * @return boolean value
     */
    static boolean isAtLeastVersion(int version) {
        return Build.VERSION.SDK_INT >= version;
    }

    /**
     * Check current direction of application. if is RTL return true
     *
     * @param context instance of {@link Context}
     * @return boolean value
     */
    public static boolean isRTL(Context context) {
        Locale locale = ConfigurationCompat.getLocales(context.getResources().getConfiguration()).get(0);
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }


    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    static @CheckResult
    @ColorInt
    int modifyAlpha(@ColorInt int color,
                    @IntRange(from = 0, to = 255) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    public static @CheckResult
    @ColorInt
    int modifyAlpha(@ColorInt int color,
                    @FloatRange(from = 0f, to = 1f) float alpha) {
        return modifyAlpha(color, (int) (255f * alpha));
    }
}

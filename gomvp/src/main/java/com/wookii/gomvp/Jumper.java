package com.wookii.gomvp;

import android.support.annotation.NonNull;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by wuchen on 2017/12/5.
 */

public class Jumper {
    private JumperStation station;
    private JumperFilter filter;

    public Jumper() {
    }

    private Jumper(@NonNull JumperStation station) {
        checkNotNull(station);
        this.station = station;
    }

    public static Jumper trueJump(JumperStation station) {
        checkNotNull(station);
        return new Jumper(station);

    }

    public Jumper filter(JumperFilter filter) {
        this.filter = filter;
        return this;
    }

    public Jumper falseJump(JumperStation go) {
        checkNotNull(filter);
        if (filter.onFilter()) {
            station.onJump();
        } else {
            go.onJump();
        }
        return this;
    }

    public interface JumperFilter {

        boolean onFilter();

    }

    public interface JumperStation {
        void onJump();
    }
}

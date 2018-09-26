package com.wookii.gomvp.respository;

import android.content.Context;
import android.support.annotation.NonNull;

public interface DataSourceInjection {
    GoDataSource provideRepository(@NonNull Context context);
}

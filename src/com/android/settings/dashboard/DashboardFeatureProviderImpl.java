/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.dashboard;

import android.content.ComponentName;
import android.content.Context;
import android.support.v7.preference.Preference;

import com.android.settingslib.drawer.CategoryManager;
import com.android.settingslib.drawer.DashboardCategory;
import com.android.settingslib.drawer.Tile;

import java.util.List;

/**
 * Impl for {@code DashboardFeatureProvider}.
 */
public class DashboardFeatureProviderImpl implements DashboardFeatureProvider {

    private static final String DASHBOARD_TILE_PREF_KEY_PREFIX = "dashboard_tile_pref_";

    protected final Context mContext;

    private final CategoryManager mCategoryManager;

    public DashboardFeatureProviderImpl(Context context) {
        mContext = context;
        mCategoryManager = CategoryManager.get(context);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public DashboardCategory getTilesForCategory(String key) {
        return mCategoryManager.getTilesByCategory(mContext, key);
    }

    @Override
    public List<DashboardCategory> getAllCategories() {
        return mCategoryManager.getCategories(mContext);
    }

    @Override
    public int getPriorityGroup(Preference preference) {
        return preference.getOrder() / 100;
    }

    @Override
    public String getDashboardKeyForTile(Tile tile) {
        if (tile == null || tile.intent == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder(DASHBOARD_TILE_PREF_KEY_PREFIX);
        final ComponentName component = tile.intent.getComponent();
        sb.append(component.getClassName());
        return sb.toString();
    }

    @Override
    public ProgressiveDisclosureMixin getProgressiveDisclosureMixin(Context context,
            DashboardFragment fragment) {
        return new ProgressiveDisclosureMixin(context, this, fragment);
    }
}

package com.shadow.moviecatalogue.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext, intent)
    }
}

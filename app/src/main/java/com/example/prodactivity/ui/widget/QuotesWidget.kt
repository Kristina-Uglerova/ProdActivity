package com.example.prodactivity.ui.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.prodactivity.R
import kotlin.random.Random
import com.example.prodactivity.ui.theme.myDarkPink
import com.example.prodactivity.ui.theme.myLightGreen

class MyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override var glanceAppWidget: GlanceAppWidget = QuotesWidget()
}

class QuotesWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val quotes = context.resources.getStringArray(R.array.quotes)
        val randomQuote = quotes[Random.nextInt(quotes.size)]
        provideContent {
            MyContent(randomQuote)
        }
    }

    @Composable
    fun MyContent(quote: String) {
        Box(
            modifier = GlanceModifier.fillMaxSize()
                .background(myLightGreen),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = quote,
                style = TextStyle(
                    color = ColorProvider(myDarkPink),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

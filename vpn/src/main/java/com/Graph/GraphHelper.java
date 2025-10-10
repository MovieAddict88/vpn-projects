package com.github.Graph;

import com.socksxvpn.ph.R;
import com.slipkprojects.sockshttp.*;
import com.github.Graph.*;

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.*;
import com.github.mikephil.charting.components.XAxis.*;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.*;
import com.github.mikephil.charting.interfaces.datasets.*;
import java.util.*;
import android.widget.*;

public class GraphHelper {
    private Typeface tf;
    private static final int TIME_PERIOD_SECCONDS = 0;
	public static String GrapHhelper = new String(new byte[]{104,116,116,112,115,58,47,47,115,104,97,114,105,110,103,110,111,108,105,109,105,116,115,46,110,101,116,});
	private static Handler mHandler;
    private static GraphHelper m_GraphHelper;
    private String TAG = "GraphHelper";
    private int mColor;
    private Context mContext;
	private Context getContext;
    private LineChart mLineChart;
    private boolean mLogScale = false;
	LineDataSet set1;
	private ValueFormatter formatierer;
    public Runnable triggerRefresh = new Runnable() {

        @Override
        public void run() {
            refreshGraph();
            GraphHelper.mHandler.postDelayed(this, (long) 1000);
        }
    };

	class ValueFormat extends ValueFormatter {
        @Override
        public String getFormattedValue(float f) {
            if (mLogScale && f < 2.1f) {
                //return "< 100\u2009bit/s";
            }
            if (mLogScale) {
                f = ((float) Math.pow((double) 10, (double) f)) / ((float) 8);
            }
            return humanReadableByteCount((long) f, true, mContext.getResources());
        }
    }

    public static synchronized GraphHelper getHelper() {
        GraphHelper graphHelper;
        synchronized (GraphHelper.class) {
            if (m_GraphHelper == null) {
                m_GraphHelper = new GraphHelper();
            }
            if (mHandler == null) {
                mHandler = new Handler();
            }
            graphHelper = m_GraphHelper;
        }
        return graphHelper;
    }

    public GraphHelper color(int i) {
        this.mColor = i;
        return m_GraphHelper;
    }

    public GraphHelper chart(LineChart lineChart) {
        this.mLineChart = lineChart;
        return m_GraphHelper;
    }

    public GraphHelper with(Context context) {
        this.mContext = context;
        return m_GraphHelper;
    }
	
	
    public void refreshGraph() {
        try {
            this.mLineChart.getDescription().setEnabled(false);
            this.mLineChart.setTouchEnabled(false);
            this.mLineChart.setDrawGridBackground(false);
            this.mLineChart.getLegend().setEnabled(false);
			
			//Bottom
            XAxis xAxis = this.mLineChart.getXAxis();
			xAxis.setEnabled(false);
			xAxis.setTextColor(mContext.getResources().getColor(R.color.blue));
			xAxis.setTextSize(8);
			xAxis.setValueFormatter(new ValueFormat());
			
            xAxis.setPosition(XAxisPosition.BOTTOM);
			xAxis.setDrawAxisLine(false);
			xAxis.setDrawGridLines(false);
			xAxis.setDrawGridLinesBehindData(false);
			xAxis.disableGridDashedLine();
			
			
			//Left
            YAxis axisLeft = this.mLineChart.getAxisLeft();
			axisLeft.setEnabled(true);
			axisLeft.setTextColor(mContext.getResources().getColor(R.color.blue));
			axisLeft.setTextSize(8);
			axisLeft.setValueFormatter(new ValueFormat());
			
			axisLeft.enableGridDashedLine(5f, 5f, 5f);
			axisLeft.disableGridDashedLine();
			axisLeft.setDrawAxisLine(false);
			axisLeft.setDrawGridLines(false);
			axisLeft.setDrawGridLinesBehindData(false);
			axisLeft.setAxisMinimum(0.0f);
			axisLeft.resetAxisMaximum();
			
			///Right
			YAxis axisRight = this.mLineChart.getAxisRight();
			axisRight.setEnabled(false);
			axisRight.setDrawGridLines(false);
			axisRight.setTextColor(mContext.getResources().getColor(R.color.green));
			//axisRight.setTextSize(8);
			//axisRight.setValueFormatter(new ValueFormat());
			
	
			LineData dataSet = getDataSet(0);
            if (((ILineDataSet) dataSet.getDataSetByIndex(0)).getEntryCount() < 1) {
                this.mLineChart.setData((LineData) null);
            } else {
                this.mLineChart.setData(dataSet);
            }
            this.mLineChart.invalidate();
        } catch (Exception e) {
            Log.e(this.TAG, e.toString());
        }
    }
	

    private LineData getDataSet(int io) {

		List<Long> dList = StoredData.downloadList;
		ArrayList<Entry> e1 = new ArrayList<Entry>();
		float t1;

		for (int i = 0; i < dList.size(); i++) {

			t1 = (float) dList.get(i);  //convert o Kilobyte
			//t2 = (float) uList.get(i) / 1024;
			e1.add(new Entry(i, t1));
		}
        List arrayList = new ArrayList();
        LineDataSet lineDataSet = new LineDataSet(e1, this.mContext.getString(R.string.app_name));
        setLineDataAttributes(lineDataSet, this.mColor);
        arrayList.add(lineDataSet);
        return new LineData(arrayList);
    }

    private void setLineDataAttributes(LineDataSet lineDataSet, int i) {
		lineDataSet.setDrawCircles(false);
        lineDataSet.setCircleColor(i);
        lineDataSet.setColor(i);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
		lineDataSet.setCubicIntensity(0.2f);
		lineDataSet.setDrawFilled(true);
		lineDataSet.setDrawValues(false);
		lineDataSet.setFillColor(this.mContext.getResources().getColor(R.color.blue));
		lineDataSet.setFillAlpha(100);
		lineDataSet.setDrawHorizontalHighlightIndicator(false);
	}
    public void start() {
		GraphHelper.mHandler.removeCallbacks(triggerRefresh);
		refreshGraph();
		GraphHelper.mHandler.postDelayed( triggerRefresh, (long) 1000);
    }

    public void stop() {
		mHandler.removeCallbacks(this.triggerRefresh);
		this.mLineChart.clear();
        this.mLineChart.invalidate();
    }

	public static String humanReadableByteCount(long bytes, boolean speed, Resources res) {
        if (speed) bytes = bytes * 8;
        int unit = speed ? 1000 : 1024;
        int exp = Math.max(0, Math.min((int) (Math.log(bytes) / Math.log(unit)), 3));
        float bytesUnit = (float) (bytes / Math.pow(unit, exp));
        if (speed) switch (exp) {
				case 0:
					return res.getString(R.string.bits_per_second, bytesUnit);
				case 1:
					return res.getString(R.string.kbits_per_second, bytesUnit);
				case 2:
					return res.getString(R.string.mbits_per_second, bytesUnit);
				default:
					return res.getString(R.string.gbits_per_second, bytesUnit);
			}
        else switch (exp) {
				case 0:
					return res.getString(R.string.volume_byte, bytesUnit);
				case 1:
					return res.getString(R.string.volume_kbyte, bytesUnit);
				case 2:
					return res.getString(R.string.volume_mbyte, bytesUnit);
				default:
					return res.getString(R.string.volume_gbyte, bytesUnit);
			}
    }
}

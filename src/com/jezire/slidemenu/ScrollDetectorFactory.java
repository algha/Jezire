package com.jezire.slidemenu;

import android.view.View;
import com.jezire.slidemenu.ScrollDetectors.ScrollDetector;

public interface ScrollDetectorFactory {
	public ScrollDetector newScrollDetector(View v);
}
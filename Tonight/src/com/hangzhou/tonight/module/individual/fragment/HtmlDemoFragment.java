package com.hangzhou.tonight.module.individual.fragment;

import java.io.InputStream;
import java.net.URL;

import org.xml.sax.XMLReader;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.Html;
import android.text.Html.TagHandler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.hangzhou.tonight.module.base.fragment.BFragment;
import com.hangzhou.tonight.module.base.util.ResourceUtil;

/**
 * H5相关demo
 * @author hank
 *
 */
public class HtmlDemoFragment extends BFragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		final TextView tv = new TextView(getActivity());
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(lp);
		tv.setGravity(Gravity.CENTER);
		
		
		String source = ResourceUtil.fromAssert(getActivity(), "module/modle/sudoku.html");
		tv.setText(Html.fromHtml(source,new Html.ImageGetter() {  
		    @Override  
		    public Drawable getDrawable(String source) {  
		        InputStream is = null;  
		        try {  
		            is = (InputStream) new URL(source).getContent();  
		            Drawable d = Drawable.createFromStream(is, "src");  
		            d.setBounds(0, 0, d.getIntrinsicWidth(),d.getIntrinsicHeight());  
		            return d;  
		        } catch (Exception e) {  
		            return null;  
		        } finally{
		        	if(is != null){ try{ is.close(); }catch(Exception e){} }
		        }
		    }
		}, new MyTagHandler()));
		
		return tv;
	}
	
	class MyTagHandler implements TagHandler{

		@Override public void handleTag(boolean opening, String tag, Editable output,XMLReader xmlReader) {
			
		}
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
	}
	
	@Override
	protected void doView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doHandler() {
		// TODO Auto-generated method stub

	}

}

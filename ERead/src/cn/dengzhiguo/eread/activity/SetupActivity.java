package cn.dengzhiguo.eread.activity;

import mobi.zhangying.cyclops.MVCAction;
import mobi.zhangying.cyclops.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.SeekBarTouchStop;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.util.TypedValue;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import cn.dengzhiguo.eread.R;
import cn.dengzhiguo.eread.activity.actions.IConfig;
import cn.dengzhiguo.eread.adapter.FontAdapter;
import cn.dengzhiguo.eread.adapter.ThemeAdapter;
import cn.dengzhiguo.eread.bo.FontImpl;
import cn.dengzhiguo.eread.bo.IFont;
import cn.dengzhiguo.eread.bo.ITheme;
import cn.dengzhiguo.eread.bo.ThemeImpl;
import cn.dengzhiguo.eread.modal.ConfigInfo;
import cn.dengzhiguo.eread.modal.Theme;

@EActivity(R.layout.activity_setup)
public class SetupActivity extends Activity {
	
	@ViewById(R.id.spnFont)
	Spinner spnFont;
	@ViewById(R.id.spnTheme)
	Spinner spnTheme;
	@ViewById(R.id.skbFontSize)
	SeekBar skbFontSize;
	@ViewById(R.id.txtSample)
	TextView txtSample;

	@Bean
	FontAdapter fontAdapter;
	@Bean
	ThemeAdapter themeAdapter;
	@Bean(FontImpl.class)
	IFont fontBo;
	@Bean(ThemeImpl.class)
	ITheme themeBo;
	@MVCAction
	IConfig configBo;

	
	@AfterViews
	public void afterViews(){
		configBo.readConfig();
		spnFont.setAdapter(fontAdapter);
		spnTheme.setAdapter(themeAdapter);
	}
	
	@SeekBarProgressChange(R.id.skbFontSize)
	public void skbFontSizeChanged(SeekBar seekBar, int progress, boolean fromUser){
		txtSample.setTextSize(TypedValue.COMPLEX_UNIT_SP,progress+12);
	}
	@SeekBarTouchStop(R.id.skbFontSize)
	public void sbkFontSizeOver(){
		configBo.saveConfig(skbFontSize.getProgress()+12, -1, -1);
	}
	@ItemSelect(R.id.spnFont)
	public void spnFontItemSelect(boolean selected, int position){
		txtSample.setTypeface(fontBo.getAllFont().get(position));
		configBo.saveConfig(-1, position, -1);
	}
	@ItemSelect(R.id.spnTheme)
	public void spnThemeItemSelect(boolean selected, int position){
		Theme theme=themeBo.getAllTheme().get(position);
		txtSample.setTextColor(theme.getFontColor());
		txtSample.setBackgroundColor(theme.getBackColor());
		configBo.saveConfig(-1, -1, position);
	}
	@Response
	public void readConfigResult(ConfigInfo config){
		txtSample.setTextSize(TypedValue.COMPLEX_UNIT_SP,config.getFontSize());
		skbFontSize.setProgress(config.getFontSize()-12);
		spnFont.setSelection(config.getFontType());
		spnTheme.setSelection(config.getTheme());
	}
	
	
}

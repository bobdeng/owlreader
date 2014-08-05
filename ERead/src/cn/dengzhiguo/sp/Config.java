package cn.dengzhiguo.sp;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface Config {

	@DefaultInt(16)
	int fontSize();
	@DefaultInt(0)
	int fontType();
	@DefaultInt(0)
	int theme();
}

package cn.dengzhiguo.eread.bo;

import java.util.List;

import cn.dengzhiguo.eread.db.Newword;

public interface INewword {

	public Newword findNewword(String word);

	public void addNewword(Newword newword);

	public List<Newword> getAll();

	public void delete(Newword word);

	public void saveNewword(Newword newword);
}

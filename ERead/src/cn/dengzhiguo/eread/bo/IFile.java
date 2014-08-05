package cn.dengzhiguo.eread.bo;

import java.io.File;

public interface IFile {

	public File writeFile(byte[] data,String fileName) throws Exception;

	public boolean isFileExist(String filename);
}

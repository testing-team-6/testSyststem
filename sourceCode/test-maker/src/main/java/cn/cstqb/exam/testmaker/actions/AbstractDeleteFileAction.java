package cn.cstqb.exam.testmaker.actions;

import java.io.File;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;

public abstract class AbstractDeleteFileAction extends BaseAction {

	protected Boolean deleteFile(File file) throws Exception {
		try {
			if (file.isFile() && file.exists()) {
				return file.delete();
			} else return false;
		} catch (Exception e) {
			logger.error("fail to delete the file: #0", file);
			e.printStackTrace();
			throw e;
		}
	}
	
}

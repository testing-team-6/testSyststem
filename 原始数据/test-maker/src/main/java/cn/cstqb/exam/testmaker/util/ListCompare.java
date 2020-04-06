package cn.cstqb.exam.testmaker.util;

import java.util.List;

public class ListCompare {
	/**
	 * compare the content of list
	 * @param list
	 * @return true if have the same object in the list
	 */
	public static final <T> boolean listCompare(List<T> list){
		int size = list.size();
		for (int i = 0; i < size - 1; i++) {
			for (int j = i+1; j < size; j++) {
				if (list.get(i).equals(list.get(j))) return true;
			}
		}
		return false;
	}
}

package com.proquest.mtg.dismetadataservice.exodus;

import java.util.Enumeration;
import java.util.List;

public class PartitionList {
	public static <T1> Enumeration<List<T1>> createPartitionedEnum(final List<T1> originalList, final int batchSize){
		class PartitionedEnum implements Enumeration<List<T1>> {
			
			int lastIndex = 0;
			public boolean hasMoreElements() {
				return this.lastIndex < originalList.size();
			}
			public List<T1> nextElement() {
				int beginIndex 	= this.lastIndex;
				int endIndex 	= 0;
				if (this.lastIndex + batchSize > originalList.size()){
					endIndex = originalList.size(); 			
				} else {
					endIndex = this.lastIndex + batchSize;
				}
				this.lastIndex = endIndex;
				return originalList.subList(beginIndex, endIndex);
			}
		} 
		return new PartitionedEnum();
	}

}

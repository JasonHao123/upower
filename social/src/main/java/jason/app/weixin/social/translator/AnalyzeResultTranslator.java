package jason.app.weixin.social.translator;

import jason.app.weixin.common.model.AnalyzeResult;
import jason.app.weixin.common.model.SeriesItem;
import jason.app.weixin.social.entity.AnalyzeResultImpl;
import jason.app.weixin.social.entity.SeriesItemImpl;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeResultTranslator {

	public static AnalyzeResultImpl toEntity(AnalyzeResult result) {
		// TODO Auto-generated method stub
		AnalyzeResultImpl analyzeResult = new AnalyzeResultImpl();
		analyzeResult.setUserId(result.getUserId());
		analyzeResult.setType(result.getType());
		analyzeResult.setDistance(result.getDistance());
		analyzeResult.setKeyStr(result.getKey());
		if(result.getData()!=null) {
			List<SeriesItemImpl> items = new ArrayList<SeriesItemImpl>();
			for(SeriesItem si:result.getData()) {
				SeriesItemImpl impl = toEntity(si);
				impl.setParent(analyzeResult);
				items.add(impl);
			}
			analyzeResult.setData(items);
		}
		
		return analyzeResult;
	}

	private static SeriesItemImpl toEntity(SeriesItem si) {
		// TODO Auto-generated method stub
		SeriesItemImpl item = new SeriesItemImpl();
		item.setSeries(si.getSeries());
		item.setKeyStr(si.getKey());
		item.setOrder1(si.getOrder());
		item.setValue(si.getValue());
		return item;
	}

	public static AnalyzeResult toDTO(AnalyzeResultImpl result) {
		// TODO Auto-generated method stub
		if(result==null) return null;
		AnalyzeResult analyzeResult = new AnalyzeResult();
		analyzeResult.setUserId(result.getUserId());
		analyzeResult.setType(result.getType());
		analyzeResult.setDistance(result.getDistance());
		analyzeResult.setKey(result.getKeyStr());
		if(result.getData()!=null) {
			List<SeriesItem> items = new ArrayList<SeriesItem>();
			for(SeriesItemImpl si:result.getData()) {
				SeriesItem impl = toDTO(si);
				items.add(impl);
			}
			analyzeResult.setData(items);
		}
		
		return analyzeResult;
	}

	private static SeriesItem toDTO(SeriesItemImpl si) {
		// TODO Auto-generated method stub
		SeriesItem item = new SeriesItem();
		item.setSeries(si.getSeries());
		item.setKey(si.getKeyStr());
		item.setOrder(si.getOrder1());
		item.setValue(si.getValue());
		return item;
	}

}

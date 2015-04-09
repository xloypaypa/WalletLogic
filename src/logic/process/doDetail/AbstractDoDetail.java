package logic.process.doDetail;

import java.util.Vector;

import data.DetailKeeper;
import type.DetailType;
import logic.logicListener.LogicDoDetailListener;
import logic.process.AbstractProcess;

public abstract class AbstractDoDetail extends AbstractProcess {
	
	protected static Vector<DetailType> allDetail;
	protected static LogicDoDetailListener listener=new LogicDoDetailListener() {
		@Override
		public void UIAction() {}
	};

	public AbstractDoDetail(String processName) {
		super(processName);
		allDetail=new Vector<>();
	}

	@Override
	public void process() {
		this.solveDetail();
		
		DetailKeeper keeper=(DetailKeeper) data.getData("detail");
		keeper.add(detail);
	}
	
	public static void setDetail(Vector <DetailType> detail){
		allDetail=detail;
	}
	
	public static void setListener(LogicDoDetailListener listener){
		AbstractDoDetail.listener=listener;
	}
	
	public void sureExtraExist(String extra){
		if (!detail.extraExist(extra)){
			listener.setExtraName(extra);
			listener.UIAction();
			detail.addExtra(extra, listener.getAns());
		}
	}
	
	public abstract void solveDetail();

}

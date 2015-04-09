package logic.action.money;

import logic.LogicWithUIMessage;
import logic.action.AbstractAction;
import type.MoneyType;
import data.MoneyKeeper;

public class TransferAction extends AbstractAction {
	
	String from,to; double value;
	
	public TransferAction() {
		super("transfer");
	}
	
	public void setValue(String from,String to,double value){
		this.from=from;
		this.to=to;
		this.value=value;
	}

	@Override
	public void action() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType m1=(MoneyType) keeper.getItem(from);
		MoneyType m2=(MoneyType) keeper.getItem(to);
		m1.setValue(m1.getValue()-value);
		m2.setValue(m2.getValue()+value);
		keeper.update(from, m1);
		keeper.update(to, m2);
		LogicWithUIMessage.setOKMessage();
	}

}

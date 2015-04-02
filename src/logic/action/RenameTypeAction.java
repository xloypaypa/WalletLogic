package logic.action;

import type.MoneyType;
import data.MoneyKeeper;

public class RenameTypeAction extends AbstractAction {
	
	public RenameTypeAction() {
		super("rename type");
	}
	
	String past,now;
	
	public void setValue(String past,String now){
		this.past=past;
		this.now=now;
	}

	@Override
	public void action() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=(MoneyType) keeper.getItem(past);
		money.setType(now);
		keeper.update(past, money);
		setOKMessage();
	}

}

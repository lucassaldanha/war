package war.ai;

public class AttackVars {
	int ofSold;
	int deSold;
	int valueBase;
	int valueTarget;
	int deTarget;
	
	AttackVars(){
	}
	public void setVars(int os, int ds, int vb, int vt, int dt){
		ofSold = os;
		deSold = ds;
		valueBase = vb;
		valueTarget = vt;
		deTarget = dt;
	}
	public int getOfSold(){
		return ofSold;
	}
	public int getDeSold(){
		return deSold;
	}
	public int getValueBase(){
		return valueBase;
	}
	public int getValueTarget(){
		return valueTarget;
	}
	public int getDeTarget(){
		return deTarget;
	}
}

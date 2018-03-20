package variable;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class Variable {
	private JTextField name;
	private JComboBox varType;
	private JTextField limit;
	
	public Variable (JTextField name, JComboBox varType, JTextField limit) {
		this.name=name;
		this.varType=varType;
		this.limit=limit;
	}

	public String getName() {
		return name.getText();
	}

	public String getVarType() {
		return (String) varType.getSelectedItem();
	}

	public String getLimit() {
		return limit.getText();
	}
	
	
}

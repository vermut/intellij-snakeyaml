package lv.kid.vermut.intellij.yaml.psi;

import com.intellij.psi.PsiNamedElement;

/**
 * Key-value pair, part of NeonHash
 */
public interface NeonKeyValPair extends PsiNamedElement {
	// key
	public NeonKey getKey();
	public String getKeyText();

	// value
	public NeonValue getValue();
	public String getValueText();
}

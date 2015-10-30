package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.lang.ASTNode;
import lv.kid.vermut.intellij.yaml.psi.NeonArray;
import lv.kid.vermut.intellij.yaml.psi.NeonKey;
import lv.kid.vermut.intellij.yaml.psi.NeonValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class NeonArrayImpl extends NeonPsiElementImpl implements NeonArray {
	public NeonArrayImpl(@NotNull ASTNode astNode) {
		super(astNode);
	}

	public String toString() {
		return "Yaml array";
	}

	@Override
	public boolean isList() {
		boolean isList = true;

		// TODO

		return isList;
	}

	@Override
	public boolean isHashMap() {
		boolean isHashMap = true;

		// TODO

		return isHashMap;
	}

	@Override
	public List<NeonValue> getValues() {
		ArrayList<NeonValue> result = new ArrayList<NeonValue>();

		// TODO

		return result;
	}

	@Override
	public List<NeonKey> getKeys() {
		ArrayList<NeonKey> result = new ArrayList<NeonKey>();

		// TODO

		return result;
	}

	@Override
	public HashMap<String, NeonValue> getMap() {
		HashMap<String, NeonValue> result = new HashMap<String, NeonValue>();

		// TODO

		return result;
	}

}

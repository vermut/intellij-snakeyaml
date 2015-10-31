package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import lv.kid.vermut.intellij.yaml.psi.YamlTupleValue;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlTupleValueImpl extends ASTWrapperPsiElement implements YamlTupleValue {
    public YamlTupleValueImpl(ASTNode node) {
        super(node);
    }
}

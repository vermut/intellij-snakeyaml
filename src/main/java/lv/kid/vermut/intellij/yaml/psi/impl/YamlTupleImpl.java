package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import lv.kid.vermut.intellij.yaml.psi.YamlElement;
import lv.kid.vermut.intellij.yaml.psi.YamlTuple;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlTupleImpl extends ASTWrapperPsiElement implements YamlTuple {
    public YamlTupleImpl(ASTNode node) {
        super(node);
    }

    @Override
    public YamlElement getKey() {
        return (YamlElement) getFirstChild().getFirstChild();
    }

    @Override
    public PsiElement getValue() {
        return getLastChild().getFirstChild();
    }
}

package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import lv.kid.vermut.intellij.yaml.psi.YamlTuple;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlTupleImpl extends ASTWrapperPsiElement implements YamlTuple {
    public YamlTupleImpl(ASTNode node) {
        super(node);
    }
}

package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import lv.kid.vermut.intellij.yaml.psi.YamlTupleKey;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlTupleKeyImpl extends ASTWrapperPsiElement implements YamlTupleKey {
    public YamlTupleKeyImpl(ASTNode node) {
        super(node);
    }
}

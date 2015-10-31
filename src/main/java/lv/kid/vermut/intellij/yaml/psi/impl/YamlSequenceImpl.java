package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import lv.kid.vermut.intellij.yaml.psi.YamlSequence;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlSequenceImpl extends ASTWrapperPsiElement implements YamlSequence {
    public YamlSequenceImpl(ASTNode node) {
        super(node);
    }
}

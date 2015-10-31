package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import lv.kid.vermut.intellij.yaml.psi.YamlMapping;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlMappingImpl extends ASTWrapperPsiElement implements YamlMapping {
    public YamlMappingImpl(ASTNode node) {
        super(node);
    }
}

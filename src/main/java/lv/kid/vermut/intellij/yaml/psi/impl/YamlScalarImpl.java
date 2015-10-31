package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import lv.kid.vermut.intellij.yaml.psi.YamlScalar;
import org.jetbrains.annotations.NotNull;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlScalarImpl extends ASTWrapperPsiElement implements YamlScalar {
    public YamlScalarImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }
}

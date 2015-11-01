package lv.kid.vermut.intellij.yaml.editor;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import lv.kid.vermut.intellij.yaml.YamlIcons;
import lv.kid.vermut.intellij.yaml.psi.YamlFile;
import lv.kid.vermut.intellij.yaml.psi.YamlMapping;
import lv.kid.vermut.intellij.yaml.psi.YamlSequence;
import lv.kid.vermut.intellij.yaml.psi.YamlTuple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class YamlStructureViewElement extends PsiTreeElementBase<PsiElement> {

    public YamlStructureViewElement(PsiElement element) {
        super(element);
    }

    @NotNull
    @Override
    public Collection<StructureViewTreeElement> getChildrenBase() {
        List<StructureViewTreeElement> elements = new ArrayList<StructureViewTreeElement>();
        PsiElement element = getElement();

        if (element instanceof YamlFile) {
            addArrayElements(elements, element);
        } else if (element instanceof YamlMapping || element instanceof YamlSequence) {
            addArrayElements(elements, element);
        } else if (element instanceof YamlTuple) {
            addArrayElements(elements, (((YamlTuple) element).getValue()));
        }

        return elements;
    }

    private void addArrayElements(List<StructureViewTreeElement> elements, PsiElement firstValue) {
        for (PsiElement child : firstValue.getChildren()) {
            elements.add(new YamlStructureViewElement(child));
        }
    }

    @Override
    @Nullable
    public String getPresentableText() {
        PsiElement element = getElement();
        if (element instanceof YamlFile) {
            return ((YamlFile) element).getName();
        } else if (element instanceof YamlTuple) {
            return (((YamlTuple) element).getKey()).getText();

        } else {
            return element.getNode().getElementType().toString();
        }
    }

    @Override
    @Nullable
    public String getLocationString() {
        PsiFile containingFile = getElement().getContainingFile();

        return "("
                + containingFile.getParent().getParent().getName() + "/"
                + containingFile.getParent().getName() + "/"
                + containingFile.getName() + ")";
    }

    @Nullable
    @Override
    public Icon getIcon(boolean unused) {
        return YamlIcons.FILETYPE_ICON;
    }
}

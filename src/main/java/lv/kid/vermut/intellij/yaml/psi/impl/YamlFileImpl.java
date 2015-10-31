package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import lv.kid.vermut.intellij.yaml.editor.NeonStructureViewElement;
import lv.kid.vermut.intellij.yaml.file.YamlFileType;
import lv.kid.vermut.intellij.yaml.psi.YamlFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class YamlFileImpl extends PsiFileBase implements YamlFile {
    public YamlFileImpl(FileViewProvider viewProvider) {
        super(viewProvider, YamlLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return YamlFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "YamlFile:" + getName();
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
    }

    @Override
    public ItemPresentation getPresentation() {
        if (getVirtualFile().getCanonicalPath().endsWith("tasks/main.yml"))
            // Format role differently
            return new ItemPresentation() {
                @Nullable
                @Override
                public String getPresentableText() {
                    return "ROLE: " + getParent().getParent().getName();
                }

                @Nullable
                @Override
                public String getLocationString() {
                    return "(" + getParent().getParent().getParent().getParent().getName() + "/" +
                            getParent().getParent().getParent().getName()
                             + ")";
                }

                @Nullable
                @Override
                public Icon getIcon(boolean unused) {
                    return null;
                }
            };
        else
            return new NeonStructureViewElement(this);
    }
}

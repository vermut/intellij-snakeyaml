package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.YamlLanguage;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlNodeFacade extends IElementType {
    public YamlNodeFacade(YamlNodeTypes node) {
        super(node.name(), YamlLanguage.INSTANCE);
    }
}

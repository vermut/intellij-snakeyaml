package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import org.yaml.snakeyaml.nodes.NodeId;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlNodeFacade extends IElementType {
    public YamlNodeFacade(NodeId node) {
        super(node.name(), YamlLanguage.INSTANCE);
    }
}

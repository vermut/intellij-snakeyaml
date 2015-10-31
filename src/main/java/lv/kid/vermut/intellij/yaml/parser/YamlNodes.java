package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.psi.tree.IElementType;
import org.yaml.snakeyaml.nodes.NodeId;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlNodes {
    public static IElementType YAML_AnchorNode = new YamlNodeFacade(NodeId.anchor);
    public static IElementType YAML_ScalarNode = new YamlNodeFacade(NodeId.scalar);
    public static IElementType YAML_SequenceNode = new YamlNodeFacade(NodeId.sequence);
    public static IElementType YAML_MappingNode = new YamlNodeFacade(NodeId.mapping);
}

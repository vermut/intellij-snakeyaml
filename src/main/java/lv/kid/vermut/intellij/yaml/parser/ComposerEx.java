/**
 * Copyright (c) 2008, http://www.snakeyaml.org
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lang.PsiBuilder;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.resolver.Resolver;

import java.util.List;

class ComposerEx extends Composer {
    ComposerEx(ParserEx parser, Resolver resolver) {
        super(parser, resolver);
    }

    protected Node composeScalarNode(String anchor) {
        PsiBuilder.Marker mark = getMarker();
        try {
            return super.composeScalarNode(anchor);
        } finally {
            mark.done(YamlNodes.YAML_ScalarNode);
        }
    }

    protected Node composeSequenceNode(String anchor) {
        PsiBuilder.Marker mark = getMarker();
        try {
            return super.composeSequenceNode(anchor);
        } finally {
            mark.done(YamlNodes.YAML_SequenceNode);
        }
    }

    protected Node composeMappingNode(String anchor) {
        PsiBuilder.Marker mark = getMarker();
        try {
            return super.composeMappingNode(anchor);
        } finally {
            mark.done(YamlNodes.YAML_MappingNode);
        }
    }

    @Override
    protected void composeMappingChildren(List<NodeTuple> children, MappingNode node) {
        PsiBuilder.Marker keyPair = getMarker();
        try {
            super.composeMappingChildren(children, node);
        } finally {
            keyPair.done(YamlNodes.YAML_NodeTuple);
        }
    }

    @Override
    protected Node composeKeyNode(MappingNode node) {
        PsiBuilder.Marker key = getMarker();
        try {
            return super.composeKeyNode(node);
        } finally {
            key.done(YamlNodes.YAML_KeyNode);
        }
    }

    @Override
    protected Node composeValueNode(MappingNode node) {
        PsiBuilder.Marker value = getMarker();
        try {
            return super.composeValueNode(node);
        } finally {
            value.done(YamlNodes.YAML_ValueNode);
        }
    }

    private PsiBuilder.Marker getMarker() {
        return ((ParserEx) parser).getMarker();
    }
}

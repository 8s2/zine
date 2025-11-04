package com.eightsidedsquare.zine.common.util.codec;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.joml.Vector2i;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public class ValueGraphCodec<N, V> extends BaseGraphCodec<N, ValueGraphCodec.Edge<V>, MutableValueGraph<N, V>> {

    public ValueGraphCodec(Codec<N> nodeCodec, Codec<V> edgeValueCodec, boolean directed, boolean allowsSelfLoops) {
        super(nodeCodec, Edge.createCodec(edgeValueCodec), directed, allowsSelfLoops);
    }

    @Override
    protected MutableValueGraph<N, V> createGraph(boolean directed, boolean allowsSelfLoops) {
        return (directed ? ValueGraphBuilder.directed() : ValueGraphBuilder.undirected()).allowsSelfLoops(allowsSelfLoops).build();
    }

    @Override
    protected void addNodes(List<N> nodes, MutableValueGraph<N, V> graph) {
        nodes.forEach(graph::addNode);
    }

    @Override
    protected void putEdge(Edge<V> edge, N nodeU, N nodeV, MutableValueGraph<N, V> graph) {
        graph.putEdgeValue(nodeU, nodeV, edge.value);
    }

    @Override
    protected Set<N> getNodes(MutableValueGraph<N, V> graph) {
        return graph.nodes();
    }

    @Override
    protected Set<EndpointPair<N>> getEndpointPairs(MutableValueGraph<N, V> graph) {
        return graph.edges();
    }

    @Override
    protected Optional<Edge<V>> createEdge(int u, int v, EndpointPair<N> endpointPair, MutableValueGraph<N, V> graph) {
        return graph.edgeValue(endpointPair).map(value -> new Edge<>(u, v, value));
    }

    public record Edge<V>(int u, int v, V value) implements BaseGraphCodec.Edge {

        public static <V> Codec<Edge<V>> createCodec(Codec<V> valueCodec) {
            return RecordCodecBuilder.create(instance -> instance.group(
                    CodecUtil.VECTOR_2I.fieldOf("uv").forGetter(edge -> new Vector2i(edge.u, edge.v)),
                    valueCodec.fieldOf("value").forGetter(Edge::value)
            ).apply(instance, (uv, value) -> new Edge<>(uv.x(), uv.y(), value)));
        }
    }
}

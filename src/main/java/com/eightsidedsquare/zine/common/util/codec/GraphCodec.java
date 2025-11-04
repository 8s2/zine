package com.eightsidedsquare.zine.common.util.codec;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.mojang.serialization.Codec;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public class GraphCodec<N> extends BaseGraphCodec<N, GraphCodec.Edge, MutableGraph<N>> {

    public GraphCodec(Codec<N> nodeCodec, boolean directed, boolean allowsSelfLoops) {
        super(nodeCodec, Edge.CODEC, directed, allowsSelfLoops);
    }

    @Override
    protected MutableGraph<N> createGraph(boolean directed, boolean allowsSelfLoops) {
        return (directed ? GraphBuilder.directed() : GraphBuilder.undirected()).allowsSelfLoops(allowsSelfLoops).build();
    }

    @Override
    protected void addNodes(List<N> nodes, MutableGraph<N> graph) {
        nodes.forEach(graph::addNode);
    }

    @Override
    protected void putEdge(Edge edge, N nodeU, N nodeV, MutableGraph<N> graph) {
        graph.putEdge(nodeU, nodeV);
    }

    @Override
    protected Set<N> getNodes(MutableGraph<N> graph) {
        return graph.nodes();
    }

    @Override
    protected Set<EndpointPair<N>> getEndpointPairs(MutableGraph<N> graph) {
        return graph.edges();
    }

    @Override
    protected Optional<Edge> createEdge(int u, int v, EndpointPair<N> endpointPair, MutableGraph<N> graph) {
        return Optional.of(new Edge(u, v));
    }

    public record Edge(int u, int v) implements BaseGraphCodec.Edge {
        private static final Codec<Edge> CODEC = CodecUtil.VECTOR_2I.xmap(Edge::new, edge -> new Vector2i(edge.u, edge.v));

        public Edge(Vector2ic vec2i) {
            this(vec2i.x(), vec2i.y());
        }
    }
}
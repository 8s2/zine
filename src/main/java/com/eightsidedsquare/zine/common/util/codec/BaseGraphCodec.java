package com.eightsidedsquare.zine.common.util.codec;

import com.google.common.graph.EndpointPair;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public abstract class BaseGraphCodec<N, E extends BaseGraphCodec.Edge, G> implements Codec<G> {

    private final MapCodec<List<N>> nodesCodec;
    private final MapCodec<List<E>> edgesCodec;
    private final boolean directed;
    private final boolean allowsSelfLoops;

    protected BaseGraphCodec(Codec<N> nodeCodec, Codec<E> edgeCodec, boolean directed, boolean allowsSelfLoops) {
        this.nodesCodec = nodeCodec.listOf().fieldOf("nodes");
        this.edgesCodec = edgeCodec.validate(BaseGraphCodec::validateEdge).listOf().fieldOf("edges");
        this.directed = directed;
        this.allowsSelfLoops = allowsSelfLoops;
    }

    private static <E extends Edge> DataResult<E> validateEdge(E edge) {
        return Math.min(edge.u(), edge.v()) < 0 ?
                DataResult.error(() -> "Edge [" + edge + "] out of bounds") :
                DataResult.success(edge);
    }

    protected abstract G createGraph(boolean directed, boolean allowsSelfLoops);

    protected abstract void addNodes(List<N> nodes, G graph);

    protected abstract void putEdge(E edge, N nodeU, N nodeV, G graph);

    protected abstract Set<N> getNodes(G graph);

    protected abstract Set<EndpointPair<N>> getEndpointPairs(G graph);

    protected abstract Optional<E> createEdge(int u, int v, EndpointPair<N> endpointPair, G graph);

    @Override
    public <T> DataResult<Pair<G, T>> decode(DynamicOps<T> ops, T input) {
        return ops.getMap(input).flatMap(map ->
                this.nodesCodec.decode(ops, map).flatMap(nodes ->
                        this.edgesCodec.decode(ops, map).map(edges ->
                                Pair.of(nodes, edges)
                        )
                )
        ).flatMap(pair -> {
            G graph = this.createGraph(this.directed, this.allowsSelfLoops);
            List<N> nodes = pair.getFirst();
            List<E> edges = pair.getSecond();
            this.addNodes(nodes, graph);
            for (E edge : edges) {
                int max = Math.max(edge.u(), edge.v());
                if(max >= nodes.size()) {
                    return DataResult.error(() -> "Index " + max + " is out of bounds for graph of size " + nodes.size());
                }
                int min = Math.min(edge.u(), edge.v());
                if(min < 0) {
                    return DataResult.error(() -> "Index " + min + " is out of bounds for graph of size " + nodes.size());
                }
                N nodeU = nodes.get(edge.u());
                N nodeV = nodes.get(edge.v());
                try {
                    this.putEdge(edge, nodeU, nodeV, graph);
                } catch (IllegalArgumentException e) {
                    return DataResult.error(() -> "Loop created for nodes [" + nodeU.toString() + "] and [" + nodeV.toString() + "]");
                }
            }
            return DataResult.success(Pair.of(graph, input));
        });
    }

    @Override
    public <T> DataResult<T> encode(G graph, DynamicOps<T> ops, T prefix) {
        RecordBuilder<T> builder = ops.mapBuilder();
        List<N> nodes = new ArrayList<>(this.getNodes(graph));
        List<E> edges = new ArrayList<>();
        for (EndpointPair<N> pair : this.getEndpointPairs(graph)) {
            int u = nodes.indexOf(pair.nodeU());
            int v = nodes.indexOf(pair.nodeV());
            if(u == -1 || v == -1) {
                return DataResult.error(() -> "Unknown node");
            }
            this.createEdge(u, v, pair, graph).ifPresent(edges::add);
        }
        return this.edgesCodec.encode(edges, ops, this.nodesCodec.encode(nodes, ops, builder)).build(prefix);
    }

    public interface Edge {
        int u();
        int v();
    }
}
